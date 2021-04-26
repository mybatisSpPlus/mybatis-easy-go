package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 21:39
 */
public class Count  extends Function {

    Field field;

    boolean distinct=false;

    public Count() {
    }

    public Count(Field field) {
        this.field = field;
    }

    public Count(Field field, boolean distinct) {
        this.field = field;
        this.distinct = distinct;
    }

    public Field getField() {
        return field;
    }

    public Count setField(Field field) {
        this.field = field;
        return this;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public Count setDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public Count distinct() {
        this.distinct = true;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null) {
            throw new SelfCheckException("field can not be null in function Count");
        }

    }
}
