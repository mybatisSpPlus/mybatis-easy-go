package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.annotation._Where;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

import java.util.HashMap;
import java.util.Map;


/**
 * @author zhouyu4034@sefonsoft.com
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

    public Set addFieldValue(Field field, Object value) {
        this.fieldValue.put(field,value);
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (fieldValue.size()==0){
            throw new SelfCheckException("field and value can not be empty in action Set");
        }
        for (Map.Entry<Field, Object> entry : fieldValue.entrySet()) {
            entry.getKey().selfCheck();
            if (entry.getValue() instanceof Field){
                ((Field) entry.getValue()).selfCheck();
            }
        }
    }
}
