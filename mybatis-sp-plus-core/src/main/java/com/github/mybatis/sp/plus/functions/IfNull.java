package com.github.mybatis.sp.plus.functions;

import com.github.mybatis.sp.plus.Function;
import com.github.mybatis.sp.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/5/28 10:33
 */
public class IfNull extends Function {

    Field field;

    Object defaultValue;

    public IfNull() {
    }

    public IfNull(Field field, Object defaultValue) {
        this.field = field;
        this.defaultValue = defaultValue;
    }

    public Field getField() {
        return field;
    }

    public IfNull setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public IfNull setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null || defaultValue == null) {
            throw new SelfCheckException("field、defaultValue can not be null in function IfNUll");
        }
    }
}
