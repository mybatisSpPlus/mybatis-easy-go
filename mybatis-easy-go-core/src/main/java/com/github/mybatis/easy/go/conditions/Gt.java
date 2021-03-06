package com.github.mybatis.easy.go.conditions;

import com.github.mybatis.easy.go.Condition;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:34
 */
public class Gt extends Condition {

    Field field;

    Object value;

    public Gt() {
    }

    public Gt(Field field, Object value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public Gt setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Gt setValue(Object value) {
        this.value = value;
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null || value == null) {
            throw new SelfCheckException("field、value can not be null in condition Gt");
        }
        field.selfCheck();
        if (value instanceof Field) {
            ((Field) value).selfCheck();
        }
    }
}
