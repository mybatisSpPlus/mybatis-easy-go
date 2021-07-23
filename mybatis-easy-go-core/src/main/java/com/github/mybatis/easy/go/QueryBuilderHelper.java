package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.exception.NoAnnotationException;
import com.github.mybatis.easy.go.mappingAnnotation.FIELD;
import com.github.mybatis.easy.go.mappingAnnotation.ID;
import com.github.mybatis.easy.go.mappingAnnotation.TABLE;
import com.github.mybatis.easy.go.meta.Alias;
import com.github.mybatis.easy.go.meta.ConstantField;
import com.github.mybatis.easy.go.meta.Table;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 14:23
 */
public class QueryBuilderHelper {

    /**
     * 将返回的数据转化为目标类型
     *
     * @param clazz      目标类型，可以是父类
     * @param properties
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T convert(Class<T> clazz, Map<String, Object> properties) throws Exception {
        T result = null;
        if (clazz != null) {
            if (Map.class.isAssignableFrom(clazz)) {
                return (T) properties;
            } else if (Date.class.isAssignableFrom(clazz)) {
                Object value = getSingleValue(properties);
                if (value == null) {
                    return null;
                }
                if (value instanceof Long || value instanceof Integer || value.getClass() == long.class || value.getClass() == int.class) {
                    result = (T) new Date((Long) value);
                } else {
                    result = (T) value;
                }
            } else if (Boolean.class.isAssignableFrom(clazz) || clazz == boolean.class) {
                Object value = getSingleValue(properties);
                if (value == null) {
                    return null;
                }
                result = (T) formatBooleanString(value.toString());
            } else if (Integer.class.isAssignableFrom(clazz) || clazz == int.class) {
                Object value = getSingleValue(properties);
                if (value == null) {
                    return null;
                }
                result = (T) Integer.valueOf(value.toString());
            } else if (Long.class.isAssignableFrom(clazz) || clazz == long.class) {
                Object value = getSingleValue(properties);
                if (value == null) {
                    return null;
                }
                result = (T) Long.valueOf(value.toString());
            } else if (Float.class.isAssignableFrom(clazz) || clazz == float.class) {
                Object value = getSingleValue(properties);
                if (value == null) {
                    return null;
                }
                result = (T) Float.valueOf(value.toString());
            } else if (Double.class.isAssignableFrom(clazz) || clazz == double.class) {
                Object value = getSingleValue(properties);
                if (value == null) {
                    return null;
                }
                result = (T) Double.valueOf(value.toString());
            } else if (BigDecimal.class.isAssignableFrom(clazz)) {
                Object value = getSingleValue(properties);
                if (value == null) {
                    return null;
                }
                result = (T) new BigDecimal(value.toString());
            } else if (String.class.isAssignableFrom(clazz)) {
                Object value = getSingleValue(properties);
                if (value == null) {
                    return null;
                }
                result = (T) value.toString();
            } else {
                result = clazz.getConstructor().newInstance();
                setProperties(result, properties);
            }

        }
        return result;
    }

    public static Object getSingleValue(Map<String, Object> properties) throws Exception {
        //去掉分页插件返回的值
        properties.remove("RN_TMP");
        if (properties.size() > 1) {
            throw new Exception("except one column but two found");
        } else if (properties.size() == 0) {
            return null;
        } else {
            return properties.entrySet().iterator().next().getValue();
        }
    }

    public static <T> List<T> convert(Class<T> clazz, List<Map<String, Object>> propertiesList) throws Exception {
        List<T> result = new ArrayList<>();
        for (Map<String, Object> map : propertiesList) {
            result.add(convert(clazz, map));
        }
        return result;
    }


    public static void setProperties(Object entity, Map<String, Object> properties) throws Exception {
        setProperties(entity.getClass(), entity, properties);
    }

    /**
     * 如果类型的字段上有FIELD注解，则以注解为准
     *
     * @param clazz
     * @param entity
     * @param map
     * @throws Exception
     */
    private static void setProperties(Class<?> clazz, Object entity, Map<String, Object> map) throws Exception {
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            Class<?> fieldType = declaredField.getType();
            String fieldName;
            FIELD fa = declaredField.getAnnotation(FIELD.class);
            if (fa == null) {
                fieldName = declaredField.getName();
            } else {
                fieldName = fa.fieldName();
            }
            Object value = map.get(fieldName);
            if (value != null) {
                if (Date.class.isAssignableFrom(fieldType)) {
                    if (value instanceof Long || value instanceof Integer || value.getClass() == long.class || value.getClass() == int.class) {
                        declaredField.set(entity, new Date((Long) value));
                    } else {
                        declaredField.set(entity, value);
                    }
                } else if (Boolean.class.isAssignableFrom(fieldType) || fieldType == boolean.class) {
                    if (value != null && StringUtils.isNotBlank(value.toString())) {
                        declaredField.set(entity, formatBooleanString(value.toString()));
                    }
                } else if (Integer.class.isAssignableFrom(fieldType) || fieldType == int.class) {
                    declaredField.set(entity, new BigDecimal(value.toString()).intValue());
                } else if (Long.class.isAssignableFrom(fieldType) || fieldType == long.class) {
                    if (value instanceof Date) {
                        declaredField.set(entity, ((Date) value).getTime());
                    } else {
                        declaredField.set(entity, new BigDecimal(value.toString()).longValue());
                    }
                } else if (Float.class.isAssignableFrom(fieldType) || fieldType == float.class) {
                    declaredField.set(entity, Float.parseFloat(value.toString()));
                } else if (Double.class.isAssignableFrom(fieldType) || fieldType == double.class) {
                    declaredField.set(entity, Double.parseDouble(value.toString()));
                } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
                    declaredField.set(entity, new BigDecimal(value.toString()));
                } else if (String.class.isAssignableFrom(fieldType)) {
                    String valueTmp = value.toString();
                    declaredField.set(entity, valueTmp);
                } else if (fieldType.isEnum()) {
                    //如果是枚举，根据值的字符串值查找具体的枚举
                    String valueTmp = value.toString();
                    for (Object enumConstant : fieldType.getEnumConstants()) {
                        if (enumConstant.toString().equals(valueTmp)) {
                            declaredField.set(entity, enumConstant);
                        }
                    }
                } else {
                    throw new Exception("unsupported type: " + fieldType);
                }
            }
        }
        if (clazz != Object.class) {
            setProperties(clazz.getSuperclass(), entity, map);
        }
    }

    public static Boolean formatBooleanString(String str) {
        return formatBooleanString(str, false);
    }

    public static Boolean formatBooleanString(String str, boolean strict) {
        HashSet<String> trueStr = Sets.newHashSet("true", "1", "是", "yes", "确认");
        HashSet<String> falseStr = Sets.newHashSet("false", "0", "否", "no", "否认");
        if (strict) {
            if (StringUtils.isBlank(str)) {
                throw new RuntimeException("不是合法的Boolean类型字符串[true, 1, 是, yes, 确认],[false, 0, 否, no, 否认]");
            }
            if (trueStr.contains(str.toLowerCase())) {
                return true;
            } else if (falseStr.contains(str.toLowerCase())) {
                return false;
            } else {
                throw new RuntimeException("不是合法的Boolean类型字符串[true, 1, 是, yes, 确认],[false, 0, 否, no, 否认]");
            }
        } else {
            if (StringUtils.isBlank(str)) {
                return false;
            }
            if (trueStr.contains(str.toLowerCase())) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static String getTypeName(Object obj) {
        return obj.getClass().getSimpleName();
    }

    public static boolean isTable(Object obj) {
        return obj instanceof Table;
    }

    public static boolean isFunction(Object obj) {
        return obj instanceof Function;
    }

    /**
     * 是字段，但是不是函数
     *
     * @param obj
     * @return
     */
    public static boolean isField(Object obj) {
        return obj instanceof com.github.mybatis.easy.go.meta.Field && !(obj instanceof Function);
    }

    public static boolean isConstantField(Object obj) {
        return obj instanceof ConstantField;
    }

    public static String getTable(Class<?> tClass) throws NoAnnotationException {
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

    public static String getIdField(Class<?> tClass) throws NoAnnotationException {
        for (java.lang.reflect.Field declaredField : tClass.getDeclaredFields()) {
            ID ia = declaredField.getAnnotation(ID.class);
            if (ia != null) {
                FIELD fa = declaredField.getAnnotation(FIELD.class);
                if (fa != null) {
                    return fa.fieldName();
                } else {
                    return declaredField.getName();
                }
            }
        }
        throw new NoAnnotationException("there is no ID annotation in Class " + tClass.getSimpleName());
    }

    public static List<java.lang.reflect.Field> getFields(Class<?> tClass, AtomicBoolean autoGenerateId, List<java.lang.reflect.Field> idField) throws Exception {
        List<java.lang.reflect.Field> result = new ArrayList<>();
        for (java.lang.reflect.Field declaredField : tClass.getDeclaredFields()) {
            FIELD fa = declaredField.getAnnotation(FIELD.class);
            if (fa != null) {
                declaredField.setAccessible(true);
                ID id = declaredField.getAnnotation(ID.class);
                if (id != null && id.autoGenerate()) {
                    autoGenerateId.set(true);
                    idField.add(declaredField);
                    continue;
                }
                result.add(declaredField);
            }
        }
        if (result.size() == 0) {
            throw new NoAnnotationException("there is no FIELD annotation in Class " + tClass.getSimpleName());
        } else {
            return result;
        }
    }

    public static List<Object> getValues(List<java.lang.reflect.Field> fields, Object object) throws Exception {
        List<Object> result = new ArrayList<>();
        for (java.lang.reflect.Field field : fields) {
            field.setAccessible(true);
            result.add(field.get(object));
        }
        return result;
    }

    public static Map<String, Object> getFieldAndValue(Object object, boolean includeNullValue) throws Exception {
        HashMap<String, Object> hm = new HashMap<>();
        for (java.lang.reflect.Field declaredField : object.getClass().getDeclaredFields()) {
            FIELD fa = declaredField.getAnnotation(FIELD.class);
            if (fa != null) {
                declaredField.setAccessible(true);
                Object value = declaredField.get(object);
                if (value != null || includeNullValue) {
                    hm.put(fa.fieldName(), value);
                }
            }
        }
        return hm;
    }


    /**
     * 将字段名转为字段对象，支持的格式
     * [spPrefix@] [tableName.]fieldName [as] [alias] [@spPostfix]
     * 最后生成的sql中后缀会在别名之前，使用时请注意
     *
     * @param fieldName
     * @return
     */
    public static com.github.mybatis.easy.go.meta.Field fieldNameToField(String fieldName) {
        com.github.mybatis.easy.go.meta.Field field = new com.github.mybatis.easy.go.meta.Field();
        //判断是否有特殊前缀
        if (fieldName.contains("@")) {
            String[] strs = fieldName.split("@");
            field.setSpecialPrefix(strs[0].trim());
            fieldName = strs[1].trim();
            if (strs.length == 3) {
                field.setSpecialPostfix(strs[2].trim());
            }
        }
        //对点拆分,判断是否有表名
        if (fieldName.contains(".")) {
            String[] strs = fieldName.split("\\.");
            field.setTableName(strs[0].trim());
            fieldName = strs[1].trim();
        }
        //对表名判断是否有别名
        if (fieldName.toLowerCase().contains(" as ")) {
            String[] strs = getNameAndAlias(fieldName);
            fieldName = strs[0];
            field.setAlias(new Alias(strs[1]));
        } else {
            String[] strs = fieldName.split("\\s+");
            if (strs.length > 1) {
                fieldName = strs[0].trim();
                field.setAlias(new Alias(strs[1].trim()));
            }
        }
        field.setName(fieldName);
        return field;
    }

    public static List<com.github.mybatis.easy.go.meta.Field> fieldNameToField(String... fieldName) {
        List<com.github.mybatis.easy.go.meta.Field> result = new ArrayList<>();
        for (String s : fieldName) {
            result.add(fieldNameToField(s));
        }
        return result;
    }

    public static Table tableNameToTable(String tableName) {
        Table table = new Table();
        //判断是否有特殊前缀
        if (tableName.contains("@")) {
            String[] strs = tableName.split("@");
            table.setSpecialPrefix(strs[0].trim());
            tableName = strs[1].trim();
            if (strs.length == 3) {
                table.setSpecialPostfix(strs[2].trim());
            }
        }
        if (tableName.contains(".")) {
            String[] strs = tableName.split("\\.");
            table.setSchema(strs[0].trim()).setName(strs[1].trim());
        } else {
            table.setName(tableName.trim());
        }
        if (table.getName().toLowerCase().contains(" as ")) {
            String[] strs = getNameAndAlias(table.getName());
            table.setName(strs[0]);
            table.setAlias(new Alias(strs[1]));
        } else {
            String[] strs = table.getName().split("\\s+");
            if (strs.length > 1) {
                table.setName(strs[0].trim());
                table.setAlias(new Alias(strs[1].trim()));
            }
        }
        return table;
    }

    public static String[] getNameAndAlias(String s) {
        String[] strs = s.toLowerCase().split(" as ");
        String name = s.substring(0, strs[0].length()).trim();
        String alias = s.substring(strs[0].length() + 4).trim();
        return new String[]{name, alias};
    }

    public static <T> ArrayList<T> arrays(T... ts) {
        return Lists.newArrayList(ts);
    }
}
