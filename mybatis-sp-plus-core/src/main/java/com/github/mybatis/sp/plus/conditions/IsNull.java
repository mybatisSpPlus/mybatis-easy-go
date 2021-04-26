package com.github.mybatis.sp.plus.conditions;

import com.github.mybatis.sp.plus.Condition;
import com.github.mybatis.sp.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:36
 */
public class IsNull extends Condition {
    Field field;

    public IsNull() {
    }

    public IsNull(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public IsNull setField(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null){
            throw new SelfCheckException("field be null in condition IsNull");
        }
        field.selfCheck();
    }
}
