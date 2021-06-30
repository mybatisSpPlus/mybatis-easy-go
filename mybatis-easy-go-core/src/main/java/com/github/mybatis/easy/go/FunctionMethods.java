package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.functions.*;
import com.github.mybatis.easy.go.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 22:44
 */
public class FunctionMethods {

    /**
     * case when的开始
     *
     * @return
     */
    public static Case caze() {
        return new Case();
    }

    /**
     * 计数方法
     *
     * @param field 源字段
     * @return
     */
    public static Count count(Field field) {
        return new Count(field);
    }

    /**
     * 字段拼接方法
     *
     * @param fields 需要拼接的字段
     * @return
     */
    public static Concat concat(Field... fields) {
        return new Concat(fields);
    }

    /**
     * 字段拼接方法
     *
     * @param separator 分隔符
     * @param fields    需要拼接的字段
     * @return
     */
    public static Concat concat(String separator, Field... fields) {
        return new Concat(separator, fields);
    }

    /**
     * 分组拼接字符串
     *
     * @param fields 需要拼接的字段
     * @return
     */
    public static GroupConcat groupConcat(Field... fields) {
        return new GroupConcat(fields);
    }

    /**
     * 分组拼接字符串
     *
     * @param separator 分隔符
     * @param fields    需要拼接的字段
     * @return
     */
    public static GroupConcat groupConcat(String separator, Field... fields) {
        return new GroupConcat(separator, fields);
    }

    /**
     * 格式化方法
     *
     * @param field  源字段
     * @param format 字段名
     * @return
     */
    public static Format format(Field field, String format) {
        return new Format(field, format);
    }

    /**
     * 字符串截取的方法
     *
     * @param field 源字段
     * @param start 开始位置
     * @return
     */
    public static Substr substr(Field field, Object start) {
        return new Substr(field, start);
    }

    /**
     * 字符串截取的方法
     *
     * @param field  源字段
     * @param start  开始位置
     * @param length 截取长度
     * @return
     */
    public static Substr substr(Field field, Object start, Object length) {
        return new Substr(field, start, length);
    }

    /**
     * 字符串编码转换的方法
     *
     * @param field         源字段
     * @param targetCharset 目标编码
     * @return
     */
    public static Convert convert(Field field, String targetCharset) {
        return new Convert(field, targetCharset);
    }

    /**
     * 字符串编码转换的方法
     *
     * @param field         源字段
     * @param targetCharset 目标编码
     * @param sourceCharset 来源编码
     * @return
     */
    public static Convert convert(Field field, String targetCharset, String sourceCharset) {
        return new Convert(field, targetCharset, sourceCharset);
    }

    /**
     * 在字符串中查找目标字的方法
     *
     * @param field  源字段
     * @param target 目标
     * @return
     */
    public static Instr instr(Field field, Object target) {
        return new Instr(field, target);
    }

    /**
     * 在字符串中查找目标字的方法
     *
     * @param field  源字段
     * @param target 目标字符串
     * @param start  起始位置
     * @param times  匹配序号
     * @return
     */
    public static Instr instr(Field field, Object target, Object start, Object times) {
        return new Instr(field, target, start, times);
    }

    /**
     * 从右侧开始截取字符串的方法
     *
     * @param field  源字段
     * @param length 截取长度
     * @return
     */
    public static Right right(Field field, int length) {
        return new Right(field, length);
    }

    /**
     * 从左侧开始截取字符串的方法
     *
     * @param field  源字段
     * @param length 截取长度
     * @return
     */
    public static Left left(Field field, int length) {
        return new Left(field, length);
    }

    /**
     * 替换字符串的方法
     *
     * @param field  源字段
     * @param oldStr 原字符串
     * @param newStr 新字符串
     * @return
     */
    public static Replace replace(Field field, Object oldStr, Object newStr) {
        return new Replace(field, oldStr, newStr);
    }

    /**
     * 字符串转为小写的方法
     *
     * @param field 源字段
     * @return
     */
    public static Lcase lcase(Field field) {
        return new Lcase(field);
    }

    /**
     * 字段转为大写的方法
     *
     * @param field 源字段
     * @return
     */
    public static Ucase ucase(Field field) {
        return new Ucase(field);
    }

    /**
     * 计算字符串长度的方法
     *
     * @param field 源字段
     * @return
     */
    public static Len len(Field field) {
        return new Len(field);
    }

    /**
     * 计算字节长度长度的方法
     *
     * @param field 源字段
     * @return
     */
    public static LenB lenB(Field field) {
        return new LenB(field);
    }

    /**
     * 计算字段平均值的方法
     *
     * @param field 源字段
     * @return
     */
    public static Avg avg(Field field) {
        return new Avg(field);
    }

    /**
     * 计算字段最大值的方法
     *
     * @param field 源字段
     * @return
     */
    public static Max max(Field field) {
        return new Max(field);
    }

    /**
     * 计算字段最小值的方法
     *
     * @param field 源字段
     * @return
     */
    public static Min min(Field field) {
        return new Min(field);
    }

    /**
     * 计算字段总值的方法
     *
     * @param field 源字段
     * @return
     */
    public static Sum sum(Field field) {
        return new Sum(field);
    }

    /**
     * 计算字段四舍五入值的方法
     *
     * @param field    源字段
     * @param decimals 保留的小数位数
     * @return
     */
    public static Round round(Field field, int decimals) {
        return new Round(field, decimals);
    }

    /**
     * 获得当前系统时间的方法
     *
     * @return
     */
    public static Now now() {
        return new Now();
    }

    /**
     * 加法
     *
     * @param a 加数a
     * @param b 加数b
     * @return
     */
    public static Add add(Object a, Object b) {
        return new Add(a, b);
    }

    /**
     * 除法
     *
     * @param a 被除数
     * @param b 除数
     * @return
     */
    public static Divide divide(Object a, Object b) {
        return new Divide(a, b);
    }

    /**
     * 乘法
     *
     * @param a 乘数a
     * @param b 乘数b
     * @return
     */
    public static Multiply multiply(Object a, Object b) {
        return new Multiply(a, b);
    }

    /**
     * 减法
     *
     * @param a 减数a
     * @param b 减数b
     * @return
     */
    public static Subtract subtract(Object a, Object b) {
        return new Subtract(a, b);
    }

    /**
     * 求余/取模
     *
     * @param a 被除数
     * @param b 除数
     * @return
     */
    public static Mod mod(Object a, Object b) {
        return new Mod(a, b);
    }

    /**
     * 求余/取模
     *
     * @param a 被除数
     * @param b 除数
     * @return
     */
    public static Mod surplus(Object a, Object b) {
        return new Mod(a, b);
    }

    /**
     * 调用自定义函数
     *
     * @param functionName 函数名称
     * @return
     */
    public static CustomFunction customFunction(String functionName) {
        return new CustomFunction(functionName);
    }

    /**
     * 调用自定义函数
     *
     * @param functionName 函数名称
     * @param parameters   参数列表
     * @return
     */
    public static CustomFunction customFunction(String functionName, Object... parameters) {
        return new CustomFunction(functionName, parameters);
    }
}
