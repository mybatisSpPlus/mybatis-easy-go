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

    /**
     * 返回一个空的条件，用于占位，在生成语句时会被去掉
     *
     * @return
     */
    public static EmptyCondition emptyCondition() {
        return new EmptyCondition();
    }

    /**
     * 返回一个 与 条件
     *
     * @param conditions 传入的条件，所有条件需要同时满足
     * @return
     */
    public static And and(Condition... conditions) {
        return new And(conditions);
    }

    /**
     * 返回一个 或 条件
     *
     * @param conditions 传入的条件，所有条件至少需要满足一个
     * @return
     */
    public static Or or(Condition... conditions) {
        return new Or(conditions);
    }

    /**
     * 返回一个 否 条件
     *
     * @param condition 传入的条件
     * @return
     */
    public static Not not(Condition condition) {
        return new Not(condition);
    }

    /**
     * 返回一个 范围限定 条件，
     *
     * @param field      字段
     * @param startValue 最小值
     * @param endValue   最大值
     * @return
     */
    public static Between between(Field field, Object startValue, Object endValue) {
        return new Between(field, startValue, endValue);
    }

    /**
     * 返回一个 范围限定 条件，
     *
     * @param fieldName  字段名
     * @param startValue 最小值
     * @param endValue   最大值
     * @return
     */
    public static Between between(String fieldName, Object startValue, Object endValue) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Between(field, startValue, endValue);
    }

    /**
     * 返回一个 等于 条件
     *
     * @param field 字段
     * @param value 判断值
     * @return
     */
    public static Eq eq(Field field, Object value) {
        return new Eq(field, value);
    }

    /**
     * 返回一个 等于 条件
     *
     * @param fieldName 字段名
     * @param value     判断值
     * @return
     */
    public static Eq eq(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Eq(field, value);
    }

    /**
     * 返回一个 不等于 条件
     *
     * @param field 字段
     * @param value 判断值
     * @return
     */
    public static Neq neq(Field field, Object value) {
        return new Neq(field, value);
    }

    /**
     * 返回一个 不等于 条件
     *
     * @param fieldName 字段名
     * @param value     判断值
     * @return
     */
    public static Neq neq(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Neq(field, value);
    }

    /**
     * 返回一个 大于 条件
     *
     * @param field 字段
     * @param value 判断值
     * @return
     */
    public static Gt gt(Field field, Object value) {
        return new Gt(field, value);
    }

    /**
     * 返回一个 大于 条件
     *
     * @param fieldName 字段名
     * @param value     判断值
     * @return
     */
    public static Gt gt(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Gt(field, value);
    }

    /**
     * 返回一个 大于等于 条件
     *
     * @param field 字段
     * @param value 判断值
     * @return
     */
    public static Gte gte(Field field, Object value) {
        return new Gte(field, value);
    }

    /**
     * 返回一个 大于等于 条件
     *
     * @param fieldName 字段名
     * @param value     判断值
     * @return
     */
    public static Gte gte(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Gte(field, value);
    }

    /**
     * 返回一个 小于 条件
     *
     * @param field 字段
     * @param value 判断值
     * @return
     */
    public static Lt lt(Field field, Object value) {
        return new Lt(field, value);
    }

    /**
     * 返回一个 小于 条件
     *
     * @param fieldName 字段名
     * @param value     判断值
     * @return
     */
    public static Lt lt(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Lt(field, value);
    }

    /**
     * 返回一个 小于等于 条件
     *
     * @param field 字段
     * @param value 判断值
     * @return
     */
    public static Lte lte(Field field, Object value) {
        return new Lte(field, value);
    }

    /**
     * 返回一个 小于等于 条件
     *
     * @param fieldName 字段名
     * @param value     判断值
     * @return
     */
    public static Lte lte(String fieldName, Object value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Lte(field, value);
    }

    /**
     * 返回一个 模糊匹配 条件
     *
     * @param field 字段
     * @param value 判断值
     * @return
     */
    public static Like like(Field field, String value) {
        return new Like(field, value);
    }

    /**
     * 返回一个 模糊匹配 条件
     *
     * @param fieldName 字段名
     * @param value     判断值
     * @return
     */
    public static Like like(String fieldName, String value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Like(field, value);
    }

    /**
     * 返回一个 以...开始 条件
     *
     * @param field 字段
     * @param value 判断值
     * @return
     */
    public static StartWith startWith(Field field, String value) {
        return new StartWith(field, value);
    }

    /**
     * 返回一个 以...开始 条件
     *
     * @param fieldName 字段名
     * @param value     判断值
     * @return
     */
    public static StartWith startWith(String fieldName, String value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new StartWith(field, value);
    }

    /**
     * 返回一个 以...结尾 条件
     *
     * @param field 字段
     * @param value 判断值
     * @return
     */
    public static EndWith endWith(Field field, String value) {
        return new EndWith(field, value);
    }


    /**
     * 返回一个 以...结尾 条件
     *
     * @param fieldName 字段名
     * @param value     判断值
     * @return
     */
    public static EndWith endWith(String fieldName, String value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new EndWith(field, value);
    }

    /**
     * 返回一个 正则匹配 条件
     *
     * @param field 字段
     * @param value 正则表达式
     * @return
     */
    public static Regx regx(Field field, String value) {
        return new Regx(field, value);
    }

    /**
     * 返回一个 正则匹配 条件
     *
     * @param fieldName 字段名
     * @param value     正则表达式
     * @return
     */
    public static Regx regx(String fieldName, String value) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new Regx(field, value);
    }

    /**
     * 返回一个 在...其中 条件
     *
     * @param field  字段
     * @param values 值的范围
     * @return
     */
    public static In in(Field field, Object... values) {
        return new In(field, values);
    }

    /**
     * 返回一个 在...其中 条件
     *
     * @param fieldName 字段名
     * @param values    值的范围
     * @return
     */
    public static In in(String fieldName, Object... values) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new In(field, values);
    }

    /**
     * 返回一个 在...其中 条件
     *
     * @param field 字段
     * @param table 子查询对象
     * @return
     */
    public static In in(Field field, Table table) {
        return new In(field, table);
    }

    /**
     * 返回一个 在...其中 条件
     *
     * @param fieldName 字段名
     * @param table     子查询对象
     * @return
     */
    public static In in(String fieldName, Table table) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new In(field, table);
    }

    /**
     * 返回一个 为空 条件
     *
     * @param field 字段
     * @return
     */
    public static IsNull isNull(Field field) {
        return new IsNull(field);
    }

    /**
     * 返回一个 为空 条件
     *
     * @param fieldName 字段
     * @return
     */
    public static IsNull isNull(String fieldName) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new IsNull(field);
    }

    /**
     * 返回一个 不为空 条件
     *
     * @param field 字段
     * @return
     */
    public static IsNotNull isNotNull(Field field) {
        return new IsNotNull(field);
    }

    /**
     * 返回一个 不为空 条件
     *
     * @param fieldName 字段
     * @return
     */
    public static IsNotNull isNotNull(String fieldName) {
        Field field = QueryBuilderHelper.fieldNameToField(fieldName);
        return new IsNotNull(field);
    }

}
