package com.mybatis.sp.plus.conditions;

import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:34
 */
public class Eq extends Condition {

    Field field;

    Object value;

    public Eq() {
    }

    public Eq(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public Eq setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Eq setValue(Object value) {
        this.value = value;
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null||value==null){
            throw new SelfCheckException("field、value can not be null in condition Eq");
        }
        field.selfCheck();
        if (value instanceof Field){
            ((Field) value).selfCheck();
        }
    }
}
