package com.mybatis.sp.plus.conditions;

import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
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
        if (field==null){
            throw new SelfCheckException("field be null in condition IsNotNull");
        }
        field.selfCheck();
    }
}
