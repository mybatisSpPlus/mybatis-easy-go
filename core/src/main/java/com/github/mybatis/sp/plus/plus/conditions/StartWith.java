package com.github.mybatis.sp.plus.plus.conditions;

import com.github.mybatis.sp.plus.plus.Condition;
import com.github.mybatis.sp.plus.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.plus.meta.Field;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:35
 */
public class StartWith extends Condition {

    Field field;

    String value;

    public StartWith() {
    }

    public StartWith(Field field, String value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public StartWith setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public StartWith setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null || StringUtils.isBlank(value)) {
            throw new SelfCheckException("field、 value can not be null or empty in condition StartWith");
        }
        field.selfCheck();
    }
}
