package com.mybatis.sp.plus.conditions;

import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:36
 */
public class Regx extends Condition {

    Field field;

    String value;

    public Regx() {
    }

    public Regx(Field field, String value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public Regx setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public Regx setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null|| StringUtils.isBlank(value)){
            throw new SelfCheckException("field、 value can not be null or empty in condition Regx");
        }
        field.selfCheck();
    }
}
