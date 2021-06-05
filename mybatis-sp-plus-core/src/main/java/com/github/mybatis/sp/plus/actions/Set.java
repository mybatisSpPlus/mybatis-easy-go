package com.github.mybatis.sp.plus.actions;

import com.github.mybatis.sp.plus.Action;
import com.github.mybatis.sp.plus.annotation._Where;
import com.github.mybatis.sp.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.meta.Field;

import java.util.HashMap;
import java.util.Map;

import static com.github.mybatis.sp.plus.MetaMethods.field;


/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/14 11:08
 */
@_Where
public class Set extends Action {

    Map<Field,Object> fieldValue=new HashMap<>();

    public Set() {
    }

    public Set(Map<Field, Object> fieldValue) {
        this.fieldValue = fieldValue;
    }

    public Map<Field, Object> getFieldValue() {
        return fieldValue;
    }

    public Set setFieldValue(Map<Field, Object> fieldValue) {
        this.fieldValue = fieldValue;
        return this;
    }

    public Set setFieldValue(Field field, Object value) {
        this.fieldValue.put(field, value);
        return this;
    }

    public Set setFieldNameValue(Map<String, Object> fieldNameValue) {
        for (Map.Entry<String, Object> entry : fieldNameValue.entrySet()) {
            this.fieldValue.put(field(entry.getKey()), entry.getValue());
        }
        return this;
    }

    public Set setFieldNameValue(String fieldName, Object value) {
        this.fieldValue.put(field(fieldName), value);
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (fieldValue.size() == 0) {
            throw new SelfCheckException("field and value can not be empty in action Set");
        }
        for (Map.Entry<Field, Object> entry : fieldValue.entrySet()) {
            entry.getKey().selfCheck();
            if (entry.getValue() instanceof Field) {
                ((Field) entry.getValue()).selfCheck();
            }
        }
    }
}
