package com.github.mybatis.sp.plus.plus.conditions;

import com.github.mybatis.sp.plus.plus.Condition;
import com.github.mybatis.sp.plus.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:34
 */
public class Lt extends Condition {

    Field field;

    Object value;

    public Lt() {
    }

    public Lt(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public Lt setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Lt setValue(Object value) {
        this.value = value;
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null||value==null){
            throw new SelfCheckException("field、value can not be null in condition Lt");
        }
        field.selfCheck();
        if (value instanceof Field){
            ((Field) value).selfCheck();
        }
    }

}
