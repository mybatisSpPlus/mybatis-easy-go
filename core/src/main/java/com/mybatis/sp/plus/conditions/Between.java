package com.mybatis.sp.plus.conditions;

import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:49
 */
public class Between extends Condition {
    Field field;

    Object startValue;

    Object endValue;

    public Between() {
    }

    public Between(Field field, Object startValue, Object endValue) {
        this.field = field;
        this.startValue = startValue;
        this.endValue = endValue;
    }

    public Field getField() {
        return field;
    }

    public Between setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getStartValue() {
        return startValue;
    }

    public Between setStartValue(Object startValue) {
        this.startValue = startValue;
        return this;
    }

    public Object getEndValue() {
        return endValue;
    }

    public Between setEndValue(Object endValue) {
        this.endValue = endValue;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null||startValue==null||endValue==null){
            throw new SelfCheckException("field、start value、end value can not be null in condition Between");
        }
    }
}
