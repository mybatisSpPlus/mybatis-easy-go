package com.github.mybatis.sp.plus;

import com.github.mybatis.sp.plus.functions.*;
import com.github.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 22:44
 */
public class FunctionMethods {

    public static Case caze() {
        return new Case();
    }

    public static Count count(Field field) {
        return new Count(field);
    }

    public static Concat concat(Field... objs) {
        return new Concat(objs);
    }

    public static Format format(Field field, String format) {
        return new Format(field, format);
    }

    public static Substr substr(Field field, int start) {
        return new Substr(field, start);
    }

    public static Substr substr(Field field, int start, int length) {
        return new Substr(field, start, length);
    }

    public static Convert convert(Field field, String targetCharset) {
        return new Convert(field, targetCharset);
    }

    public static Convert convert(Field field, String targetCharset, String sourceCharset) {
        return new Convert(field, targetCharset, sourceCharset);
    }

    public static Instr instr(Field field, Object target) {
        return new Instr(field, target);
    }

    public static Instr instr(Field field, Object target, int start, int times) {
        return new Instr(field, target, start, times);
    }

    public static Right right(Field field, int length) {
        return new Right(field, length);
    }

    public static Left left(Field field, int length) {
        return new Left(field, length);
    }

    public static Replace replace(Field field, Object oldStr, Object newStr) {
        return new Replace(field, oldStr, newStr);
    }

    public static Lcase lcase(Field field) {
        return new Lcase(field);
    }

    public static Ucase ucase(Field field) {
        return new Ucase(field);
    }

    public static Len len(Field field){
        return new Len(field);
    }

    public static Avg avg(Field field){
        return new Avg(field);
    }

    public static Max max(Field field){
        return new Max(field);
    }

    public static Min min(Field field){
        return new Min(field);
    }

    public static Sum sum(Field field){
        return new Sum(field);
    }

    public static Round round(Field field, int decimals){
        return new Round(field,decimals);
    }

    public static Now now(){
        return new Now();
    }
}
