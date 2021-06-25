package com.github.mybatis.easy.go.conditions;

import com.github.mybatis.easy.go.Condition;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:35
 */
public class EndWith extends Condition {

    Field field;

    String value;

    public EndWith() {
    }

    public EndWith(Field field, String value) {
        this.field = field;
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public EndWith setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getValue() {
        return value;
    }

    public EndWith setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null || StringUtils.isBlank(value)) {
            throw new SelfCheckException("field、 value can not be null or empty in condition EndWith");
        }
        field.selfCheck();
    }
}
