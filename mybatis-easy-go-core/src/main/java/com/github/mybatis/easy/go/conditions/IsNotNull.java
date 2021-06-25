package com.github.mybatis.easy.go.conditions;

import com.github.mybatis.easy.go.Condition;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:37
 */
public class IsNotNull extends Condition {
    Field field;

    public IsNotNull() {
    }

    public IsNotNull(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public IsNotNull setField(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null) {
            throw new SelfCheckException("field be null in condition IsNotNull");
        }
        field.selfCheck();
    }
}
