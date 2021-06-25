package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.conditions.*;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.meta.Table;


/**
 * condition的快捷方法
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 22:44
 */
public class ConditionMethods {

    public static EmptyCondition emptyCondition() {
        return new EmptyCondition();
    }

    public static And and(Condition... conditions) {
        return new And(conditions);
    }

    public static Or or(Condition... conditions) {
        return new Or(conditions);
    }

    public static Between between(Field field, Object startValue, Object endValue) {
        return new Between(field, startValue, endValue);
    }

    public static Eq eq(Field field, Object value) {
        return new Eq(field, value);
    }

    public static Neq neq(Field field, Object value) {
        return new Neq(field, value);
    }

    public static Gt gt(Field field, Object value) {
        return new Gt(field, value);
    }

    public static Gte gte(Field field, Object value) {
        return new Gte(field, value);
    }

    public static Lt lt(Field field, Object value) {
        return new Lt(field, value);
    }

    public static Lte lte(Field field, Object value) {
        return new Lte(field, value);
    }

    public static Like like(Field field, String value) {
        return new Like(field, value);
    }

    public static StartWith startWith(Field field, String value) {
        return new StartWith(field, value);
    }

    public static EndWith endWith(Field field, String value) {
        return new EndWith(field, value);
    }

    public static Regx regx(Field field, String value) {
        return new Regx(field, value);
    }

    public static In in(Field field, Object... values) {
        return new In(field, values);
    }

    public static In in(Field field, Table table) {
        return new In(field, table);
    }

    public static IsNull isNull(Field field) {
        return new IsNull(field);
    }

    public static IsNotNull isNotNull(Field field) {
        return new IsNotNull(field);
    }

    public static Not not(Condition condition) {
        return new Not(condition);
    }

    public static Between between(String fieldName, Object startValue, Object endValue) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Between(field, startValue, endValue);
    }

    public static Eq eq(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Eq(field, value);
    }

    public static Neq neq(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Neq(field, value);
    }

    public static Gt gt(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Gt(field, value);
    }

    public static Gte gte(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Gte(field, value);
    }

    public static Lt lt(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Lt(field, value);
    }

    public static Lte lte(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Lte(field, value);
    }

    public static Like like(String fieldName, String value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Like(field, value);
    }

    public static StartWith startWith(String fieldName, String value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new StartWith(field, value);
    }

    public static EndWith endWith(String fieldName, String value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new EndWith(field, value);
    }


    public static Regx regx(String fieldName, String value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Regx(field, value);
    }

    public static In in(String fieldName, Object... values) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new In(field, values);
    }

    public static IsNull isNull(String fieldName) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new IsNull(field);
    }

    public static IsNotNull isNotNull(String fieldName) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new IsNotNull(field);
    }

}
