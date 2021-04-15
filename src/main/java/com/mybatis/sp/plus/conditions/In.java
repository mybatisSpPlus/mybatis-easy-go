package com.mybatis.sp.plus.conditions;

import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:35
 */
public class In extends Condition {

    Field field;

    List<Object> values=new ArrayList<>();

    Table table;

    public In() {
    }

    public In(Field field, Collection<Object> values) {
        this.field = field;
        this.values=new ArrayList<>(values);
    }

    public In(Field field, Table table) {
        this.field = field;
        this.table = table;
    }

    public In(Field field, Object... values) {
        this.field = field;
        this.values=new ArrayList<>(Arrays.asList(values));
    }

    public Field getField() {
        return field;
    }

    public In setField(Field field) {
        this.field = field;
        return this;
    }

    public Table getTable() {
        return table;
    }

    public In setTable(Table table) {
        this.table = table;
        return this;
    }

    public In inTable(Table table) {
        this.table = table;
        return this;
    }

    public List<Object> getValues() {
        return values;
    }

    public In setValues(Collection<Object> values) {
        this.values=new ArrayList<>(values);
        return this;
    }

    public In addValues(Object... values) {
        this.values.addAll(Arrays.asList(values));
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null){
            throw new SelfCheckException("field can not be null or empty in condition In");
        }
        if (values.size()==0&&table==null){
            throw new SelfCheckException("values and table can not be both null or empty in condition In");
        }
        field.selfCheck();
    }
}
