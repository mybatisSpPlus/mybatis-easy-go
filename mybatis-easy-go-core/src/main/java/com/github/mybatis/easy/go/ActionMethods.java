package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.actions.*;
import com.github.mybatis.easy.go.mappingAnnotation.FIELD;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.meta.Order;
import com.github.mybatis.easy.go.meta.Table;
import com.github.mybatis.easy.go.step.Step;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.mybatis.easy.go.ActionMethodSource.*;
import static com.github.mybatis.easy.go.ConditionMethods.emptyCondition;
import static com.github.mybatis.easy.go.MetaMethods.allField;
import static com.github.mybatis.easy.go.MetaMethods.field;
import static com.github.mybatis.easy.go.QueryBuilderHelper.*;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 22:44
 */
public class ActionMethods {


    /*
    action 的快捷生成方法
     */
    public static Select select() {
        return new QueryBuilders().select();
    }

    public static Select select(Field... fields) {
        return new QueryBuilders().select(fields);
    }

    public static Select select(String... fieldNames) {
        List<Field> fields = fieldNameToField(fieldNames);
        return new QueryBuilders().select(fields);
    }

    public static Update update(Table table) {
        return new QueryBuilders().update(table);
    }

    public static Update update(String tableName) {
        return new QueryBuilders().update(tableNameToTable(tableName));
    }

    public static InsertInto insertInto(Table table) {
        return new QueryBuilders().insertInto(table);
    }

    public static InsertInto insertInto(String tableName) {
        return new QueryBuilders().insertInto(tableNameToTable(tableName));
    }

    public static Delete delete() {
        return new QueryBuilders().delete();
    }

    public static Delete delete(Table... tables) {
        return new QueryBuilders().delete(tables);
    }

    public static Delete delete(String... tableNames) {
        Table[] tables = new Table[tableNames.length];
        for (int i = 0; i < tableNames.length; i++) {
            tables[i] = tableNameToTable(tableNames[i]);
        }
        return new QueryBuilders().delete(tables);
    }

    public static Truncate truncate(Table table) {
        return new QueryBuilders().truncate(table);
    }

    public static Truncate truncate(String tableName) {
        return new QueryBuilders().truncate(tableNameToTable(tableName));
    }

    public static Order Order() {
        return new Order();
    }

    public static Order Order(String fieldName) {
        return new Order(QueryBuilderHelper.fieldNameToField(fieldName), false);
    }

    public static Order Order(String fieldName, boolean desc) {
        return new Order(QueryBuilderHelper.fieldNameToField(fieldName), desc);
    }

    public static Order Order(Field field) {
        return new Order(field, false);
    }

    public static Order Order(Field field, boolean desc) {
        return new Order(field, desc);
    }

    public static Order Order(Field field, Order.ORDER desc) {
        return new Order(field, desc);
    }

    public static Limit limit() {
        return new Limit();
    }

    public static NoLimit noLimit() {
        return new NoLimit();
    }

    public static Limit limit(int limit) {
        return new Limit(limit);
    }

    public static Limit limit(int limit, int offset) {
        return new Limit(limit, offset);
    }

    /**对添加了注解的类的操作 **/

    /**
     * 传入带有注解的类型和对象进行插入
     *
     * @param items 需要插入的对象，对象需要是同一个类型
     * @param <T>
     * @return 插入条数
     * @throws Exception
     */
    public static <T> int insertInto(T... items) throws Exception {
        return insertInto(Arrays.asList(items));
    }

    /**
     * 传入带有注解的类型和对象进行插入
     *
     * @param items 需要插入的对象，对象需要是同一个类型,为了能够按照正确的顺序设置自增的id，所以不支持传入Set
     * @param <T>
     * @return 插入条数
     * @throws Exception
     */
    public static <T> int insertInto(List<T> items) throws Exception {
        if (items == null || items.size() == 0) {
            return 0;
        }
        String tableName = getTable(items.iterator().next().getClass());
        AtomicBoolean autoGenerateId = new AtomicBoolean(false);
        List<java.lang.reflect.Field> idFields = new ArrayList<>();
        List<java.lang.reflect.Field> refFields = getFields(items.iterator().next().getClass(), autoGenerateId, idFields);
        String[] fieldNames = new String[refFields.size()];
        for (int i = 0; i < refFields.size(); i++) {
            java.lang.reflect.Field f = refFields.get(i);
            fieldNames[i] = f.getAnnotation(FIELD.class).fieldName();
        }
        List<List<Object>> values = new ArrayList<>();
        for (T item : items) {
            values.add(getValues(refFields, item));
        }
        InsertInto insertInto = ActionMethods.insertInto(tableName)
                .fields(fieldNames)
                .values(values);
        if (autoGenerateId.get()) {
            //为了设置数据库生成的id,不通过mybatis执行，
            String sql = insertInto.getStepGenerator().toSql();
            List<Object> params = new ArrayList<>();
            for (Step step : insertInto.getStepGenerator().toStep()) {
                if (step.getStepValue() != null) {
                    params.add(step.getStepValue());
                }
            }
            SqlSessionTemplate sessionTemplate = insertInto.getSessionTemplate();
            SqlSession sqlSession = SqlSessionUtils.getSqlSession(sessionTemplate.getSqlSessionFactory(),
                    sessionTemplate.getExecutorType(), sessionTemplate.getPersistenceExceptionTranslator());
            try (Connection connection = sqlSession.getConnection();
                 PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                for (int i = 0; i < params.size(); i++) {
                    ps.setObject(i + 1, params.get(i));
                }
                int count = ps.executeUpdate();
                ResultSet rs = ps.getGeneratedKeys();
                final ResultSetMetaData rsmd = rs.getMetaData();
                if (rsmd.getColumnCount() < 1) {
                    // Error?
                } else {
                    java.lang.reflect.Field idField = idFields.get(0);
                    for (T item : items) {
                        if (rs.next()) {
                            Object id = rs.getObject(1);
                            if (idField.getType() == int.class || idField.getType().isAssignableFrom(Integer.class)) {
                                idField.set(item, ((java.math.BigInteger) id).intValue());
                            } else if (idField.getType() == long.class || idField.getType().isAssignableFrom(Long.class)) {
                                idField.set(item, ((java.math.BigInteger) id).longValue());
                            } else {
                                idField.set(item, id);
                            }
                        }
                    }
                }
                return count;
            }
        } else {
            return insertInto.executeInsert();
        }
    }

    public static <T> int update(T item) throws Exception {
        return update(item, false);
    }

    /**
     * 更新对象，需要对象存在主键
     *
     * @param item
     * @param setNullValue
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> int update(T item, boolean setNullValue) throws Exception {
        String tableName = getTable(item.getClass());
        String idName = getIdField(item.getClass());
        Map<String, Object> setValues = getFieldAndValue(item, setNullValue);
        if (!setValues.containsKey(idName) || setValues.get(idName) == null) {
            throw new Exception("id can not be null");
        }
        Object idValue = setValues.get(idName);
        setValues.remove(idName);
        Where where = where(
                ActionMethodSource.set(
                        ActionMethods.update(tableName)
                ).setFieldNameValue(setValues), field(idName).eq(idValue));
        return where.executeUpdate();
    }

    /**
     * 传入带有注解的类型和id进行查询
     *
     * @param tClass 带有注解的类型
     * @param Id     id值
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T selectOne(Class<T> tClass, Object Id) throws Exception {
        String tableName = getTable(tClass);
        String idName = getIdField(tClass);
        Where where = where(from(select(allField()), tableName), field(idName).eq(Id));
        return where.executeOneSelect(tClass);
    }

    /**
     * 传入带有注解的类型和id进行查询
     *
     * @param tClass 带有注解的类型
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> selectList(Class<T> tClass) throws Exception {
        return selectList(tClass, emptyCondition(), null);
    }

    public static <T> List<T> selectList(Class<T> tClass, Order... orders) throws Exception {
        return selectList(tClass, emptyCondition(), null, orders);
    }

    public static <T> List<T> selectList(Class<T> tClass, int length) throws Exception {
        return selectList(tClass, emptyCondition(), length);
    }

    public static <T> List<T> selectList(Class<T> tClass, int length, Order... orders) throws Exception {
        return selectList(tClass, emptyCondition(), length, orders);
    }

    /**
     * 传入带有注解的类型和id进行查询
     *
     * @param tClass    带有注解的类型
     * @param condition 查询条件
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<T> selectList(Class<T> tClass, Condition condition, Integer length, Order... orders) throws Exception {
        String tableName = getTable(tClass);
        Where where = where(from(select(allField()), tableName), condition != null ? condition : emptyCondition());
        if (orders != null && orders.length > 0) {
            return ActionMethodSource.limit(orderBy(where, orders), length != null ? limit(length) : noLimit()).executeListSelect(tClass);
        } else {
            return ActionMethodSource.limit(where, length != null ? limit(length) : noLimit()).executeListSelect(tClass);
        }
    }

    public static <T> PageRecord<T> selectPage(Class<T> tClass, int index, int size) throws Exception {
        return selectPage(tClass, emptyCondition(), index, size);
    }

    public static <T> PageRecord<T> selectPage(Class<T> tClass, int index, int size, Order... orders) throws Exception {
        return selectPage(tClass, emptyCondition(), index, size, orders);
    }

    /**
     * 传入带有注解的类型和id进行查询
     *
     * @param tClass    带有注解的类型
     * @param condition 查询条件
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> PageRecord<T> selectPage(Class<T> tClass, Condition condition, int index, int size, Order... orders) throws Exception {
        String tableName = getTable(tClass);
        Where where = where(from(select(allField()), tableName), condition != null ? condition : emptyCondition());
        if (orders != null && orders.length > 0) {
            return orderBy(where, orders).executePageSelect(index, size, tClass);
        } else {
            return where.executePageSelect(index, size, tClass);
        }
    }

    /**
     * 传入带有注解的类型和id进行删除
     *
     * @param tClass 带有注解的类型
     * @param Id     id值
     * @param
     * @return 删除的条数
     * @throws Exception
     */

    public static int deleteOne(Class<?> tClass, Object Id) throws Exception {
        String tableName = getTable(tClass);
        String idName = getIdField(tClass);
        Where where = where(from(delete(), tableName), field(idName).eq(Id));
        return where.executeDelete();
    }

    /**
     * 传入带有注解的类型和id进行删除
     *
     * @param tClass    带有注解的类型
     * @param condition 过滤条件
     * @param
     * @return 删除的条数
     * @throws Exception
     */
    public static int deleteList(Class<?> tClass, Condition condition) throws Exception {
        String tableName = getTable(tClass);
        Where where = where(from(delete(), tableName), condition);
        return where.executeDelete();
    }
}
