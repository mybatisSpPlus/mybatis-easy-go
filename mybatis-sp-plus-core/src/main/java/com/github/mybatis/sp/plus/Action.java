package com.github.mybatis.sp.plus;

import com.github.mybatis.sp.plus.actions.*;
import com.github.mybatis.sp.plus.exception.PageException;
import com.github.mybatis.sp.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.meta.Alias;
import com.github.mybatis.sp.plus.meta.Result;
import com.github.mybatis.sp.plus.meta.Table;
import com.github.mybatis.sp.plus.spring.BaseMapper;
import com.github.mybatis.sp.plus.spring.BeanHelper;
import com.github.mybatis.sp.plus.step.Step;
import com.github.mybatis.sp.plus.step.StepGenerator;
import org.apache.ibatis.session.ResultHandler;
import org.mybatis.spring.SqlSessionTemplate;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.github.mybatis.sp.plus.ActionMethods.select;
import static com.github.mybatis.sp.plus.FunctionMethods.count;
import static com.github.mybatis.sp.plus.MetaMethods.allField;
import static com.github.mybatis.sp.plus.MetaMethods.constantField;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:41
 */
public abstract class Action {

    public static HashMap<String, Class> dbTypeToStepGenerator = new HashMap<>();

    QueryBuilders builders;

    StepGenerator stepGenerator;

    private boolean printSql;

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

    public Table asTable(){
        return new Table().setActions(builders.getActionTree());
    }

    public Table asTable(Alias alias){
        return new Table().setAlias(alias).setActions(builders.getActionTree());
    }

    public Table asTable(String alias){
        return new Table().setAlias(new Alias(alias)).setActions(builders.getActionTree());
    }

    public void execute() throws Exception {
        List<Action> actions=builders.getActionTree();
        List<Step> steps=getStepGenerator().toStep(printSql,setParameter);
        if(actions.size()>0){
            Action start=actions.get(0);
            if (start instanceof Select){
                executeSelect();
            }else if (start instanceof Delete){
                executeDelete();
            }else if (start instanceof Update){
                executeUpdate();
            }else if (start instanceof InsertInto){
                executeInsert();
            }else {
                getMapper().execute(steps);
            }
        }
    }

    public int executeUpdate() throws Exception {
        return getMapper().executeUpdate(getStepGenerator().toStep(printSql,setParameter));
    }

    public int executeInsert() throws Exception {
        return getMapper().executeInsert(getStepGenerator().toStep(printSql,setParameter));
    }

    public int executeDelete() throws Exception {
        return getMapper().executeDelete(getStepGenerator().toStep(printSql, setParameter));
    }

    public Result executeSelect() throws Exception {
        List<Map<String, Object>> map = getMapper().executeQuery(getStepGenerator().toStep(printSql, setParameter));
        cleanNull(map);
        return new Result(map);
    }

    public void executeFetchSelect(ResultHandler<Map<String, Object>> handler) throws Exception {
        getSessionTemplate().select("com.github.mybatis.sp.plus.spring.BaseMapper.executeFetchQuery", getStepGenerator().toStep(printSql, setParameter), handler);
    }

    public <T> T executeOneSelect(Class<T> tClass) throws Exception {
        List<Map<String, Object>> map = getMapper().executeQuery(getStepGenerator().toStep(printSql, setParameter));
        cleanNull(map);
        return new Result(map).convertToOne(tClass);
    }

    public <T> T executeOneSelect(Class<T> tClass, BiFunction<Class<T>, Map<String, Object>, T> function) throws Exception {
        List<Map<String, Object>> map = getMapper().executeQuery(getStepGenerator().toStep(printSql, setParameter));
        cleanNull(map);
        return new Result(map).convertToOne(tClass, function);
    }

    public <T> T executeOneSelect(String typeName, BiFunction<String, Map<String, Object>, T> function) throws Exception {
        List<Map<String, Object>> map = getMapper().executeQuery(getStepGenerator().toStep(printSql, setParameter));
        cleanNull(map);
        return new Result(map).convertToOne(typeName, function);
    }

    public <T> T executeOneSelect(Function<Map<String, Object>, T> function) throws Exception {
        List<Map<String, Object>> map = getMapper().executeQuery(getStepGenerator().toStep(printSql, setParameter));
        cleanNull(map);
        return new Result(map).convertToOne(function);
    }

    public <T> List<T> executeListSelect(Class<T> tClass) throws Exception {
        List<Map<String, Object>> map = getMapper().executeQuery(getStepGenerator().toStep(printSql, setParameter));
        cleanNull(map);
        return new Result(map).convertToList(tClass);
    }

    public <T> List<T> executeListSelect(Class<T> tClass, BiFunction<Class<T>, List<Map<String, Object>>, List<T>> function) throws Exception {
        List<Map<String, Object>> map = getMapper().executeQuery(getStepGenerator().toStep(printSql, setParameter));
        cleanNull(map);
        return new Result(map).convertToList(tClass, function);
    }

    public <T> List<T> executeListSelect(String typeName, BiFunction<String,List<Map<String,Object>>,List<T>> function) throws Exception {
        List<Map<String,Object>> map=getMapper().executeQuery(getStepGenerator().toStep(printSql,setParameter));
        cleanNull(map);
        return new Result(map).convertToList(typeName, function);
    }

    public <T> List<T> executeListSelect(Function<List<Map<String, Object>>, List<T>> function) throws Exception {
        List<Map<String, Object>> map = getMapper().executeQuery(getStepGenerator().toStep(printSql, setParameter));
        cleanNull(map);
        return new Result(map).convertToList(function);
    }

    public <T> Page<T> executePageSelect(int pageNum, int pageSize, Class<T> tClass) throws Exception {
        long total = getCount();
        Page page = new Page().setTotal(total);
        if (total > 0) {
            List<Map<String, Object>> map = getPageData(pageNum, pageSize);
            return new Result(map).convertToPage(pageNum, pageSize, total, tClass);
        } else {
            return new Page().setTotal(total);
        }
    }


    public <T> Page<T> executePageSelect(int pageNum, int pageSize, Class<T> tClass, BiFunction<Class<T>, List<Map<String, Object>>, List<T>> function) throws Exception {
        long total = getCount();
        if (total > 0) {
            List<Map<String, Object>> map = getPageData(pageNum, pageSize);
            return new Result(map).convertToPage(pageNum, pageSize, total, tClass, function);
        } else {
            return new Page().setTotal(total);
        }
    }

    public <T> Page<T> executePageSelect(int pageNum, int pageSize, String typeName, BiFunction<String, List<Map<String, Object>>, List<T>> function) throws Exception {
        long total = getCount();
        if (total > 0) {
            List<Map<String, Object>> map = getPageData(pageNum, pageSize);
            return new Result(map).convertToPage(pageNum, pageSize, total, typeName, function);
        } else {
            return new Page().setTotal(total);
        }
    }

    public <T> Page<T> executePageSelect(int pageNum, int pageSize, Function<List<Map<String, Object>>, List<T>> function) throws Exception {
        long total = getCount();
        if (total > 0) {
            List<Map<String, Object>> map = getPageData(pageNum, pageSize);
            return new Result(map).convertToPage(pageNum, pageSize, total, function);
        } else {
            return new Page().setTotal(total);
        }
    }

    public long getCount() throws Exception {
        //如果包含union 则不能直接替换前面的select,需要整体作为子查询
        if (hasUnion()) {
            From count = ActionFunctionSource.from(select(count(constantField(1)).as("countNum")), this.asTable("PAGE_TEMP"));
            List<Map<String, Object>> countMap = getMapper().executeQuery(count.getStepGenerator().toStep(printSql, setParameter));
            long total = Long.parseLong(countMap.get(0).get("countNum").toString());
            return total;
        } else {
            if (!(builders.getActionTree().get(0) instanceof Select)) {
                throw new PageException("page query must start with select");
            }
            Action select = builders.getActionTree().get(0);
            builders.getActionTree().set(0, select(count(constantField(1)).as("countNum")));
            List<Map<String, Object>> countMap = getMapper().executeQuery(getStepGenerator().toStep(printSql, setParameter));
            builders.getActionTree().set(0, select);
            long total = Long.parseLong(countMap.get(0).get("countNum").toString());
            return total;
        }
    }

    public List<Map<String, Object>> getPageData(int pageNum, int pageSize) throws Exception {
        //如果包含union 则不能直接在后面添加limit,需要整体作为子查询
        if (hasUnion()) {
            Limit data = ActionFunctionSource.limit(ActionFunctionSource.from(select(allField()), this.asTable("PAGE_TEMP")), pageSize, (pageNum - 1) * pageSize);
            List<Map<String, Object>> map = getMapper().executeQuery(data.getStepGenerator().toStep(printSql, setParameter));
            cleanNull(map);
            return map;
        } else {
            if (this instanceof Limit) {
                throw new PageException("limit is not allowed when page query");
            }
            Limit data = ActionFunctionSource.limit(this, pageSize, (pageNum - 1) * pageSize);
            List<Map<String, Object>> map = getMapper().executeQuery(data.getStepGenerator().toStep(printSql, setParameter));
            cleanNull(map);
            return map;
        }
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
            if (dbTypeToStepGenerator.containsKey(sessionTemplate.getConfiguration().getDatabaseId())) {
                stepGenerator = (StepGenerator) dbTypeToStepGenerator.get(sessionTemplate.getConfiguration().getDatabaseId()).getConstructor(List.class).newInstance(builders.getActionTree());
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

    public boolean isSetParameter() {
        return setParameter;
    }

    public Action setSetParameter(boolean setParameter) {
        this.setParameter = setParameter;
        return this;
    }

    public abstract void selfCheck() throws SelfCheckException;

}

