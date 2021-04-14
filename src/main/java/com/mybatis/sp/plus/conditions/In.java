package com.mybatis.sp.plus.conditions;

import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:35
 */
public class In extends Condition {

    Field field;

    Collection<Object> values;

    public In() {
    }

    public In(Field field, Collection<Object> values) {
        this.field = field;
        this.values = values;
    }

    public In(Field field, Object... values) {
        this.field = field;
        this.values = Arrays.asList(values);
    }

    public Field getField() {
        return field;
    }

    public In setField(Field field) {
        this.field = field;
        return this;
    }

    public Collection<Object> getValues() {
        return values;
    }

    public In setValues(Collection<Object> values) {
        this.values = values;
        return this;
    }

    public In addValues(Object... values) {
        if (values!=null){
            this.values = Arrays.asList(values);
        }else {
            this.values.addAll(Arrays.asList(values));
        }
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null||values.size()==0){
            throw new SelfCheckException("field、 values can not be null or empty in condition In");
        }
        field.selfCheck();
    }
}
