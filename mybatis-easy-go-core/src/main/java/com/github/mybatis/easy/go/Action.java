package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.actions.*;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Alias;
import com.github.mybatis.easy.go.meta.Result;
import com.github.mybatis.easy.go.meta.Table;
import com.github.mybatis.easy.go.spring.BaseMapper;
import com.github.mybatis.easy.go.spring.BeanHelper;
import com.github.mybatis.easy.go.step.StepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.DatabaseVersion;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.github.mybatis.easy.go.ActionMethods.select;
import static com.github.mybatis.easy.go.FunctionMethods.count;
import static com.github.mybatis.easy.go.MetaMethods.allField;
import static com.github.mybatis.easy.go.MetaMethods.constantField;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:41
 */
public abstract class Action {
    /**
     * 数据库类型对应的步骤生成器
     */
    public static HashMap<String, List<Class>> dbTypeToStepGenerator = new HashMap<>();
    /**
     * 查询构造器
     */
    QueryBuilders builders;
    /**
     * 步骤生成器
     */
    StepGenerator stepGenerator;
    /**
     * 是否将sql打印出来
     */
    private boolean printSql;
    /**
     * 是否将参数设置到sql中
     */
    @Deprecated
    private boolean setParameter;

    /**
     * 通过构造函数的方法创建时，设置默认的builder
     */
    public Action() {
        if (builders == null) {
            builders = new QueryBuilders();
            builders.addActionToTree(this);
        }
    }

    public QueryBuilders getBuilders() {
        return builders;
    }


    public Action setBuilders(QueryBuilders builders) {
        this.builders = builders;
        return this;
    }

    /**
     * 将之前的Action类型转为Table，以作为子查询
     *
     * @return 子查询对象
     */
    public Table asTable() {
        return new Table().setActions(builders.getActionTree());
    }

    /**
     * 将之前的Action类型转为Table，以作为子查询
     *
     * @param alias 子查询的结果表的别名
     * @return 子查询对象
     */
    public Table asTable(Alias alias) {
        return new Table().setAlias(alias).setActions(builders.getActionTree());
    }

    /**
     * 将之前的Action类型转为Table，以作为子查询
     *
     * @param alias 子查询的结果表的别名
     * @return 子查询对象
     */
    public Table asTable(String alias) {
        return new Table().setAlias(new Alias(alias)).setActions(builders.getActionTree());
    }

    /**
     * 执行语句
     *
     * @throws Exception
     */
    public void execute() throws Exception {
        List<Action> actions = builders.getActionTree();
        if (actions.size() > 0) {
            Action start = actions.get(0);
            if (start instanceof Select) {
                executeSelect();
            } else if (start instanceof Delete) {
                executeDelete();
            } else if (start instanceof Update) {
                executeUpdate();
            } else if (start instanceof InsertInto) {
                executeInsert();
            } else {
                Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
                getMapper().execute((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
            }
        }
    }

    /**
     * 执行更新语句
     *
     * @return 更新的记录条数
     * @throws Exception
     */
    public int executeUpdate() throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        return getMapper().executeUpdate((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
    }

    /**
     * 执行插入语句
     *
     * @return 插入的记录条数
     * @throws Exception
     */
    public int executeInsert() throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        return getMapper().executeInsert((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
    }

    /**
     * 执行删除语句
     *
     * @return 删除的条数
     * @throws Exception
     */
    public int executeDelete() throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        return getMapper().executeDelete((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
    }

    /**
     * 执行查询语句
     *
     * @return 返回结果对象，结果对象内的数据结构为 List<Map<String, Object>>
     * @throws Exception
     */
    public Result executeSelect() throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return new Result(map);
    }

    /**
     * 游标的方式执行查询语句
     *
     * @param handler 数据处理器
     * @throws Exception
     */
    public void executeFetchSelect(ResultHandler<Map<String, Object>> handler) throws Exception {
        getSessionTemplate().select("BaseMapper.executeFetchQuery", getStepGenerator().toStep(), handler);
    }

    /**
     * 执行查询，返回结果为一个对象
     *
     * @param tClass 返回对象的类型
     * @param <T>
     * @return 查询出的结果对象
     * @throws Exception
     */
    public <T> T executeOneSelect(Class<T> tClass) throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return new Result(map).convertToOne(tClass);
    }

    /**
     * 执行查询，返回结果为一个对象
     *
     * @param tClass   返回对象的类型
     * @param function 进行类型转换的方法
     * @param <T>
     * @return 查询出的结果对象
     * @throws Exception
     */
    public <T> T executeOneSelect(Class<T> tClass, BiFunction<Class<T>, Map<String, Object>, T> function) throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return new Result(map).convertToOne(tClass, function);
    }

    /**
     * 执行查询，返回结果为一个对象
     *
     * @param typeName 返回对象的类型名称
     * @param function 进行类型转换的方法
     * @param <T>
     * @return 查询出的结果对象
     * @throws Exception
     */
    public <T> T executeOneSelect(String typeName, BiFunction<String, Map<String, Object>, T> function) throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return new Result(map).convertToOne(typeName, function);
    }

    /**
     * 执行查询，返回结果为一个对象
     *
     * @param function 进行类型转换的方法
     * @param <T>
     * @return 查询出的结果对象
     * @throws Exception
     */
    public <T> T executeOneSelect(Function<Map<String, Object>, T> function) throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return new Result(map).convertToOne(function);
    }

    /**
     * 执行查询，返回结果为一个List
     *
     * @param tClass 返回对象的类型
     * @param <T>
     * @return 查询出的结果对象List
     * @throws Exception
     */
    public <T> List<T> executeListSelect(Class<T> tClass) throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return new Result(map).convertToList(tClass);
    }

    /**
     * 执行查询，返回结果为一个List
     *
     * @param tClass   返回对象的类型
     * @param function 进行类型转换的方法
     * @param <T>
     * @return 查询出的结果对象List
     * @throws Exception
     */
    public <T> List<T> executeListSelect(Class<T> tClass, BiFunction<Class<T>, List<Map<String, Object>>, List<T>> function) throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return new Result(map).convertToList(tClass, function);
    }

    /**
     * 执行查询，返回结果为一个List
     *
     * @param typeName 返回对象的类型名称
     * @param function 进行类型转换的方法
     * @param <T>
     * @return 查询出的结果对象List
     * @throws Exception
     */
    public <T> List<T> executeListSelect(String typeName, BiFunction<String, List<Map<String, Object>>, List<T>> function) throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return new Result(map).convertToList(typeName, function);
    }

    /**
     * 执行查询，返回结果为一个List
     *
     * @param function 进行类型转换的方法
     * @param <T>
     * @return 查询出的结果对象List
     * @throws Exception
     */
    public <T> List<T> executeListSelect(Function<List<Map<String, Object>>, List<T>> function) throws Exception {
        Object[] sqlParams = getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return new Result(map).convertToList(function);
    }

    /**
     * 执行分页查询，返回结果为PageRecord对象
     *
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页条数
     * @param tClass    返回对象类型
     * @param <T>
     * @return 查询出的结果PageRecord对象
     * @throws Exception
     */
    public <T> PageRecord<T> executePageSelect(int pageIndex, int pageSize, Class<T> tClass) throws Exception {
        long total = getCount();
        PageRecord pageRecord = new PageRecord().setTotal(total);
        if (total > 0) {
            List<Map<String, Object>> map = getPageData(pageIndex, pageSize);
            return new Result(map).convertToPage(pageIndex, pageSize, total, tClass);
        } else {
            return new PageRecord().setTotal(total);
        }
    }

    /**
     * 执行分页查询，返回结果为PageRecord对象
     *
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页条数
     * @param tClass    返回对象类型
     * @param function  进行类型转换的方法
     * @param <T>
     * @return 查询出的结果PageRecord对象
     * @throws Exception
     */
    public <T> PageRecord<T> executePageSelect(int pageIndex, int pageSize, Class<T> tClass, BiFunction<Class<T>, List<Map<String, Object>>, List<T>> function) throws Exception {
        long total = getCount();
        if (total > 0) {
            List<Map<String, Object>> map = getPageData(pageIndex, pageSize);
            return new Result(map).convertToPage(pageIndex, pageSize, total, tClass, function);
        } else {
            return new PageRecord().setTotal(total);
        }
    }

    /**
     * 执行分页查询，返回结果为PageRecord对象
     *
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页条数
     * @param typeName  返回对象类型名称
     * @param function  进行类型转换的方法
     * @param <T>
     * @return 查询出的结果PageRecord对象
     * @throws Exception
     */
    public <T> PageRecord<T> executePageSelect(int pageIndex, int pageSize, String typeName, BiFunction<String, List<Map<String, Object>>, List<T>> function) throws Exception {
        long total = getCount();
        if (total > 0) {
            List<Map<String, Object>> map = getPageData(pageIndex, pageSize);
            return new Result(map).convertToPage(pageIndex, pageSize, total, typeName, function);
        } else {
            return new PageRecord().setTotal(total);
        }
    }

    /**
     * 执行分页查询，返回结果为PageRecord对象
     *
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页条数
     * @param function  进行类型转换的方法
     * @param <T>
     * @return 查询出的结果PageRecord对象
     * @throws Exception
     */
    public <T> PageRecord<T> executePageSelect(int pageIndex, int pageSize, Function<List<Map<String, Object>>, List<T>> function) throws Exception {
        long total = getCount();
        if (total > 0) {
            List<Map<String, Object>> map = getPageData(pageIndex, pageSize);
            return new Result(map).convertToPage(pageIndex, pageSize, total, function);
        } else {
            return new PageRecord().setTotal(total);
        }
    }

    /**
     * 获取查询的记录条数
     *
     * @return 查询包含的记录条数
     * @throws Exception
     */
    public long getCount() throws Exception {
        From count = ActionMethodSource.from(select(count(constantField(1)).as("countNum")), this.asTable("PAGE_TEMP"));
        Object[] sqlParams = count.getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> countMap = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        long total = Long.parseLong(countMap.get(0).get("countNum").toString());
        return total;
    }

    /**
     * 分页获取一页数据
     *
     * @param pageIndex 页码，从1开始
     * @param pageSize  每页条数
     * @return 查询出的结果List
     * @throws Exception
     */
    public List<Map<String, Object>> getPageData(int pageIndex, int pageSize) throws Exception {
        Limit data = ActionMethodSource.limit(ActionMethodSource.from(select(allField()), this.asTable("PAGE_TEMP")), pageSize, (pageIndex - 1) * pageSize);
        Object[] sqlParams = data.getStepGenerator().toMybatisSql(printSql);
        List<Map<String, Object>> map = getMapper().executeQuery((String) sqlParams[0], (HashMap<String, Object>) sqlParams[1]);
        cleanNull(map);
        return map;
    }

    private boolean hasUnion() {
        for (Action action : builders.getActionTree()) {
            if (action instanceof Union || action instanceof UnionAll) {
                return true;
            }
        }
        return false;
    }

    /**
     * 聚合函数即使在没有数据的情况下也会返回NULL值，需要清理掉
     *
     * @param map
     */
    private void cleanNull(List<Map<String, Object>> map) {
        Iterator iterator = map.iterator();
        while (iterator.hasNext()) {
            Object obj = iterator.next();
            if (obj == null) {
                iterator.remove();
            }
        }
    }

    public BaseMapper getMapper() {
        return BeanHelper.getBean(BaseMapper.class);
    }


    public SqlSessionTemplate getSessionTemplate() {
        return BeanHelper.getBean(SqlSessionTemplate.class);
    }

    public StepGenerator getStepGenerator() throws Exception {
        if (stepGenerator == null) {
            SqlSessionTemplate sessionTemplate = BeanHelper.getBean(SqlSessionTemplate.class);
            SqlSession sqlSession = SqlSessionUtils.getSqlSession(sessionTemplate.getSqlSessionFactory(),
                    sessionTemplate.getExecutorType(), sessionTemplate.getPersistenceExceptionTranslator());
            String dbVersion = "";
            try (Connection connection = sqlSession.getConnection()) {
                dbVersion = connection.getMetaData().getDatabaseProductVersion();
            }
            if (dbTypeToStepGenerator.containsKey(sessionTemplate.getConfiguration().getDatabaseId())) {
                List<Class> generatorList = dbTypeToStepGenerator.get(sessionTemplate.getConfiguration().getDatabaseId());
                if (generatorList.size() > 1) {
                    Class fclass = null;
                    for (Class aClass : generatorList) {
                        DatabaseVersion dv = (DatabaseVersion) aClass.getAnnotation(DatabaseVersion.class);
                        if (dv != null) {
                            String max = dv.maxVersion();
                            String min = dv.minVersion();
                            if (StringUtils.isNotBlank(min) && StringUtils.isNotBlank(max)) {
                                if (max.compareToIgnoreCase(dbVersion) > 0 && min.compareToIgnoreCase(dbVersion) < 0) {
                                    fclass = aClass;
                                    break;
                                }
                            } else if (StringUtils.isNotBlank(min)) {
                                if (min.compareToIgnoreCase(dbVersion) < 0) {
                                    fclass = aClass;
                                    break;
                                }
                            } else if (StringUtils.isNotBlank(max)) {
                                if (max.compareToIgnoreCase(dbVersion) > 0) {
                                    fclass = aClass;
                                    break;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    //没有合适版本的生成器，则选择第一个版本
                    if (fclass == null) {
                        fclass = generatorList.get(0);
                    }
                    stepGenerator = (StepGenerator) fclass.getConstructor(List.class).newInstance(builders.getActionTree());
                } else {
                    stepGenerator = (StepGenerator) generatorList.get(0).getConstructor(List.class).newInstance(builders.getActionTree());
                }
            } else {
                stepGenerator = new StepGenerator(builders.getActionTree(), "");
            }
        }
        return stepGenerator;
    }

    public boolean isPrintSql() {
        return printSql;
    }

    public Action setPrintSql(boolean printSql) {
        this.printSql = printSql;
        return this;
    }

    @Deprecated
    public boolean isSetParameter() {
        return setParameter;
    }

    @Deprecated
    public Action setSetParameter(boolean setParameter) {
        this.setParameter = setParameter;
        return this;
    }

    public abstract void selfCheck() throws SelfCheckException;

}

