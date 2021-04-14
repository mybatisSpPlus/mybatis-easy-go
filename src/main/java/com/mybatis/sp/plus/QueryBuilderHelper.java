package com.mybatis.sp.plus;

import com.google.common.collect.Sets;
import com.mybatis.sp.plus.actions.From;
import com.mybatis.sp.plus.actions.Join;
import com.mybatis.sp.plus.actions.SubActionBegin;
import com.mybatis.sp.plus.actions.SubActionEnd;
import com.mybatis.sp.plus.conditions.*;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.ConstantField;
import com.mybatis.sp.plus.meta.Table;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/13 14:23
 */
public class QueryBuilderHelper {

    public static List<Action> formatActionChain(List<Action> actionTree) throws SelfCheckException {
        List<Action> result=new ArrayList<>();
        for (Action action : actionTree) {
            action.selfCheck();
            if (action instanceof From){
                Table table= ((From) action).getTable();
                if (table.getActions()!=null&&table.getActions().size()>0){
                    result.add(action);
                    result.add(new SubActionBegin());
                    result.addAll(formatActionChain(table.getActions()));
                    result.add(new SubActionEnd(table.getAlias()));
                }else {
                    result.add(action);
                }
            }else if (action instanceof Join){
                Table table= ((Join) action).getTable();
                if (table.getActions()!=null&&table.getActions().size()>0){
                    result.add(action);
                    result.add(new SubActionBegin());
                    result.addAll(formatActionChain(table.getActions()));
                    result.add(new SubActionEnd(table.getAlias()));
                }else {
                    result.add(action);
                }
            }else {
                result.add(action);
            }
        }
        return result;
    }

    public static List<Condition> formatConditionChain(List<Condition> conditions) throws SelfCheckException {
        List<Condition> result=new ArrayList<>();
        for (Condition condition : conditions) {
            //避免重复循环检查，在最外层直接进行一次检查
            condition.selfCheck();
            result.addAll(formatConditionChain(condition));
        }
        //整体结束了
        result.get(result.size()-1).setLastOne(true);
        return result;
    }

    private static List<Condition> formatConditionChain(Condition condition) throws SelfCheckException {
        List<Condition> result=new ArrayList<>();
        if (condition instanceof And){
            result.add(new ConditionBegin());
            for (Condition condition1 : ((And) condition).getAndCondition()) {
                result.addAll(formatConditionChain(condition1));
            }
            //在一个括号内结束了
            result.get(result.size()-1).setLastOne(true);
            result.add(new ConditionEnd());
        }else if (condition instanceof Or){
            result.add(new ConditionBegin());
            for (Condition condition1 : ((Or) condition).getOrCondition()) {
                condition1.setOrWithNext(true);
                result.addAll(formatConditionChain(condition1));
            }
            //在一个括号内结束了
            result.get(result.size()-1).setLastOne(true);
            result.add(new ConditionEnd());
        }else if (condition instanceof Not){
            result.add(condition);
            result.addAll(formatConditionChain(((Not) condition).getNotCondition()));
        }else {
            result.add(condition);
        }
        return result;
    }

    /**
     * @param clazz      目标类型，可以是父类
     * @param properties
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> T convert(Class<T> clazz, Map<String, Object> properties) throws Exception {
        T result=null;
        if (clazz != null) {
                if (Date.class.isAssignableFrom(clazz)) {
                    Object value=getSingleValue(properties);
                    if (value==null){
                        return null;
                    }
                    if (value instanceof Long || value instanceof Integer || value.getClass() == long.class || value.getClass() == int.class) {
                        result= (T) new Date((Long) value);
                    } else {
                        result= (T) value;
                    }
                } else if (Boolean.class.isAssignableFrom(clazz) || clazz == boolean.class) {
                    Object value=getSingleValue(properties);
                    if (value==null){
                        return null;
                    }
                    result=(T)formatBooleanString(value.toString());
                } else if (Integer.class.isAssignableFrom(clazz) || clazz == int.class) {
                    Object value=getSingleValue(properties);
                    if (value==null){
                        return null;
                    }
                    result= (T)Integer.valueOf(value.toString());
                } else if (Long.class.isAssignableFrom(clazz) || clazz == long.class) {
                    Object value=getSingleValue(properties);
                    if (value==null){
                        return null;
                    }
                    result= (T)Long.valueOf(value.toString());
                } else if (Float.class.isAssignableFrom(clazz) || clazz == float.class) {
                    Object value=getSingleValue(properties);
                    if (value==null){
                        return null;
                    }
                    result= (T)Float.valueOf(value.toString());
                } else if (Double.class.isAssignableFrom(clazz) || clazz == double.class) {
                    Object value=getSingleValue(properties);
                    if (value==null){
                        return null;
                    }
                    result= (T)Double.valueOf(value.toString());
                } else if (BigDecimal.class.isAssignableFrom(clazz)) {
                    Object value=getSingleValue(properties);
                    if (value==null){
                        return null;
                    }
                    result= (T)new BigDecimal(value.toString());
                } else if (String.class.isAssignableFrom(clazz)) {
                    Object value=getSingleValue(properties);
                    if (value==null){
                        return null;
                    }
                    result= (T)value.toString();
                } else {
                    result = clazz.newInstance();
                    setProperties(result, properties);
                }

        }
        return result;
    }

    public static Object getSingleValue(Map<String, Object> properties) throws Exception {
        if (properties.size()>1){
            throw new Exception("except one column but two found");
        }else if(properties.size()==0){
            return null;
        }else {
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

    private static void setProperties(Class<?> clazz, Object entity, Map<String, Object> map) throws Exception {
        for (Field declaredField : clazz.getDeclaredFields()) {
            declaredField.setAccessible(true);
            String fieldName = declaredField.getName();
            Class<?> fieldType = declaredField.getType();
            Object value = map.get(fieldName);
            if (value != null) {
                if (Date.class.isAssignableFrom(fieldType)) {
                    if (value instanceof Long || value instanceof Integer || value.getClass() == long.class || value.getClass() == int.class) {
                        declaredField.set(entity, new Date((Long) value));
                    } else {
                        declaredField.set(entity, value);
                    }
                } else if (Boolean.class.isAssignableFrom(fieldType) || fieldType == boolean.class) {
                    if (value!=null && StringUtils.isNotBlank(value.toString())) {
                        declaredField.set(entity, formatBooleanString(value.toString()));
                    }
                } else if (Integer.class.isAssignableFrom(fieldType) || fieldType == int.class) {
                    declaredField.set(entity, Integer.parseInt(value.toString()));
                } else if (Long.class.isAssignableFrom(fieldType) || fieldType == long.class) {
                    if (value instanceof Date) {
                        declaredField.set(entity, ((Date) value).getTime());
                    } else {
                        declaredField.set(entity, Long.parseLong(value.toString()));
                    }
                } else if (Float.class.isAssignableFrom(fieldType) || fieldType == float.class) {
                    declaredField.set(entity, new Float(value.toString()));
                } else if (Double.class.isAssignableFrom(fieldType) || fieldType == double.class) {
                    declaredField.set(entity, new Double(value.toString()));
                } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
                    declaredField.set(entity, new BigDecimal(value.toString()));
                } else if (String.class.isAssignableFrom(fieldType)) {
                    String valueTmp = value.toString();
                    declaredField.set(entity, valueTmp);
                } else {
                    throw new Exception("unsupported type:"+fieldType);
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

    public static String getTypeName(Object obj){
        return obj.getClass().getSimpleName();
    }

    public static boolean isFunction(Object obj){
        return obj instanceof Function;
    }

    public static boolean isConstantField(Object obj){
        return obj instanceof ConstantField;
    }

    public static com.mybatis.sp.plus.meta.Field fieldNameToField(String fieldName){
        com.mybatis.sp.plus.meta.Field field=new com.mybatis.sp.plus.meta.Field();
        if (fieldName.contains("\\.")){
            String[] strs=fieldName.split("\\.");
            field.setTableName(strs[0]).setName(strs[1]);
        }else {
            field.setName(fieldName);
        }
        return field;
    }

    public static Table tableNameToTable(String tableName){
        Table table=new Table();
        if (tableName.contains("\\.")){
            String[] strs=tableName.split("\\.");
            table.setSchema(strs[0]).setName(strs[1]);
        }else {
            table.setName(tableName);
        }
        return table;
    }
}
