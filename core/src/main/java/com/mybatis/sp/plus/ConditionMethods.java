package com.mybatis.sp.plus;

import com.mybatis.sp.plus.conditions.*;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 22:44
 */
public class ConditionMethods {
    /**
     * condition的快捷方法
     * @param conditions
     * @return
     */
    public static And and(Condition... conditions){
        return new And(conditions);
    }

    public static Or or(Condition... conditions){
        return new Or(conditions);
    }

    public static Between between(Field field, Object startValue, Object endValue) {
        return new Between(field,startValue,endValue);
    }
    public static Eq eq(Field field, Object value){
        return new Eq(field,value);
    }

    public static Neq neq(Field field, Object value){
        return new Neq(field,value);
    }

    public static Gt gt(Field field, Object value){
        return new Gt(field,value);
    }

    public static Gte gte(Field field, Object value){
        return new Gte(field,value);
    }

    public static Lt lt(Field field, Object value){
        return new Lt(field,value);
    }

    public static Lte lte(Field field, Object value){
        return new Lte(field,value);
    }

    public static Like like(Field field, String value){
        return new Like(field,value);
    }

    public static Regx regx(Field field, String value){
        return new Regx(field,value);
    }

    public static In in(Field field, Object... values){
        return new In(field,values);
    }

    public static IsNull isNull(Field field){
        return new IsNull(field);
    }

    public static IsNotNull isNotNull(Field field){
        return new IsNotNull(field);
    }

    public static Not not(Condition condition){
        return new Not(condition);
    }

    public static Between between(String fieldName, Object startValue, Object endValue) {
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new Between(field,startValue,endValue);
    }
    public static Eq eq(String fieldName, Object value){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new Eq(field,value);
    }

    public static Neq neq(String fieldName, Object value){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new Neq(field,value);
    }

    public static Gt gt(String fieldName, Object value){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new Gt(field,value);
    }

    public static Gte gte(String fieldName, Object value){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new Gte(field,value);
    }

    public static Lt lt(String fieldName, Object value){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new Lt(field,value);
    }

    public static Lte lte(String fieldName, Object value){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new Lte(field,value);
    }

    public static Like like(String fieldName, String value){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new Like(field,value);
    }

    public static Regx regx(String fieldName, String value){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new Regx(field,value);
    }

    public static In in(String fieldName,Object... values){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new In(field,values);
    }

    public static IsNull isNull(String fieldName){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new IsNull(field);
    }

    public static IsNotNull isNotNull(String fieldName){
        Field field= QueryBuilderHelper.fieldNameToField(fieldName);
        return new IsNotNull(field);
    }

}
