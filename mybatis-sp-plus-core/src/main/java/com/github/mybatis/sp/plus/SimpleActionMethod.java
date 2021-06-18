package com.github.mybatis.sp.plus;

import com.github.mybatis.sp.plus.actions.From;
import com.github.mybatis.sp.plus.actions.InsertInto;
import com.github.mybatis.sp.plus.actions.Set;
import com.github.mybatis.sp.plus.actions.Where;
import com.github.mybatis.sp.plus.exception.NoAnnotationException;
import com.github.mybatis.sp.plus.mappingAnnotation.FIELD;
import com.github.mybatis.sp.plus.mappingAnnotation.ID;
import com.github.mybatis.sp.plus.mappingAnnotation.TABLE;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.*;

import static com.github.mybatis.sp.plus.ActionFunctionSource.from;
import static com.github.mybatis.sp.plus.ActionFunctionSource.where;
import static com.github.mybatis.sp.plus.ActionMethods.delete;
import static com.github.mybatis.sp.plus.ActionMethods.select;
import static com.github.mybatis.sp.plus.MetaMethods.allField;
import static com.github.mybatis.sp.plus.MetaMethods.field;

public class SimpleActionMethod {

    private static String getTable(Class<?> tClass) throws NoAnnotationException {
        TABLE ta = tClass.getAnnotation(TABLE.class);
        if (ta != null) {
            if (StringUtils.isNotBlank(ta.schema())) {
                return ta.schema() + "." + ta.tableName();
            } else {
                return ta.tableName();
            }
        } else {
            throw new NoAnnotationException("there is no TABLE annotation in Class " + tClass.getSimpleName());
        }
    }

    private static String getIdField(Class<?> tClass) throws NoAnnotationException {
        for (Field declaredField : tClass.getDeclaredFields()) {
            ID ia = declaredField.getAnnotation(ID.class);
            if (ia != null) {
                FIELD fa = declaredField.getAnnotation(FIELD.class);
                if (fa != null) {
                    return fa.FieldName();
                } else {
                    return declaredField.getName();
                }
            }
        }
        throw new NoAnnotationException("there is no ID annotation in Class " + tClass.getSimpleName());
    }

    private static List<String> getFields(Class<?> tClass) throws Exception {
        List<String> result = new ArrayList<>();
        for (Field declaredField : tClass.getDeclaredFields()) {
            FIELD fa = declaredField.getAnnotation(FIELD.class);
            if (fa != null) {
                declaredField.setAccessible(true);
                result.add(fa.FieldName());
            }
        }
        if (result.size() == 0) {
            throw new NoAnnotationException("there is no FIELD annotation in Class " + tClass.getSimpleName());
        } else {
            return result;
        }
    }

    private static List<Object> getValues(List<String> fields, Object object) throws Exception {
        List<Object> result = new ArrayList<>();
        for (String fieldName : fields) {
            Field field = object.getClass().getField(fieldName);
            field.setAccessible(true);
            result.add(field.get(object));
        }
        return result;
    }

    private static Map<String, Object> getFieldAndValue(Object object, boolean includeNullValue) throws Exception {
        HashMap<String, Object> hm = new HashMap<>();
        for (Field declaredField : object.getClass().getDeclaredFields()) {
            FIELD fa = declaredField.getAnnotation(FIELD.class);
            if (fa != null) {
                declaredField.setAccessible(true);
                Object value = declaredField.get(object);
                if (value != null || includeNullValue) {
                    hm.put(fa.FieldName(), value);
                }
            }
        }
        return hm;
    }

    /**
     * 传入带有注解的类型和对象进行插入
     *
     * @param items 需要插入的对象，对象需要是同一个类型
     * @param <T>
     * @return 插入条数
     * @throws Exception
     */
    public static <T> int insertInto(T... items) throws Exception {
        if (items.length == 0) {
            return 0;
        }
        String tableName = getTable(items[0].getClass());
        List<String> fields = getFields(items[0].getClass());
        List<List<Object>> values = new ArrayList<>();
        for (T item : items) {
            values.add(getValues(fields, item));
        }
        InsertInto insertInto = ActionMethods.insertInto(tableName)
                .fields(fields.toArray(new String[fields.size()]))
                .values(values);
        return insertInto.executeInsert();
    }

    /**
     * 传入带有注解的类型和对象进行插入
     *
     * @param items 需要插入的对象，对象需要是同一个类型
     * @param <T>
     * @return 插入条数
     * @throws Exception
     */
    public static <T> int insertInto(Collection<T> items) throws Exception {
        if (items == null || items.size() == 0) {
            return 0;
        }
        String tableName = getTable(items.iterator().next().getClass());
        List<String> fields = getFields(items.iterator().next().getClass());
        List<List<Object>> values = new ArrayList<>();
        for (T item : items) {
            values.add(getValues(fields, item));
        }
        InsertInto insertInto = ActionMethods.insertInto(tableName)
                .fields(fields.toArray(new String[fields.size()]))
                .values(values);
        return insertInto.executeInsert();
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
        Set set = ActionFunctionSource.set(ActionMethods.update(tableName)).setFieldNameValue(setValues);
        return set.executeUpdate();
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
        String tableName = getTable(tClass);
        From from = from(select(allField()), tableName);
        return from.executeListSelect(tClass);
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
    public static <T> List<T> selectList(Class<T> tClass, Condition condition) throws Exception {
        String tableName = getTable(tClass);
        Where where = where(from(select(allField()), tableName), condition);
        return where.executeListSelect(tClass);
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
    public static <T> PageRecord<T> selectPage(Class<T> tClass, Condition condition, int index, int size) throws Exception {
        String tableName = getTable(tClass);
        Where where = where(from(select(allField()), tableName), condition);
        return where.executePageSelect(index, size, tClass);
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
