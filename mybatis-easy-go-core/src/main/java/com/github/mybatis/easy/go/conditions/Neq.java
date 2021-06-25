package com.github.mybatis.easy.go.conditions;

import com.github.mybatis.easy.go.Condition;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:34
 */
public class Neq extends Condition {

    Field field;

    Object value;

    public Neq() {
    }

    public Neq(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public Neq setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Neq setValue(Object value) {
        this.value = value;
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null || value == null) {
            throw new SelfCheckException("field、value can not be null in condition Neq");
        }
        field.selfCheck();
        if (value instanceof Field) {
            ((Field) value).selfCheck();
        }
    }
}
