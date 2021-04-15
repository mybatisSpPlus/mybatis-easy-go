package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.QueryBuilderHelper;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.fieldNameToField;
import static com.mybatis.sp.plus.QueryBuilderHelper.tableNameToTable;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:28
 */
public class InsertInto extends Action {

    Table table;

    List<Field> fields=new ArrayList<>();

    List<List<Object>> values=new ArrayList<>();

    public InsertInto(Table table) {
        this.table = table;
    }

    public InsertInto(String tableName) {
        this.table = tableNameToTable(tableName);
    }

    public InsertInto fields(Field... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public InsertInto fields(String... fieldNames) {
        for (String fieldName : fieldNames) {
            this.fields.add(QueryBuilderHelper.fieldNameToField(fieldName));
        }
        return this;
    }

    public List<List<Object>> getValues() {
        return values;
    }

    public InsertInto values(Collection<List<Object>> values){
        this.values.addAll(values);
        return this;
    }

    public InsertInto values(List<Object>... values){
        this.values.addAll(Arrays.asList(values));
        return this;
    }

    public InsertInto addValue(List<Object> value){
        this.values.add(value);
        return this;
    }

    public Select select() {
        Select select=new Select();
        getBuilders().getActionTree().add(select);
        select.setBuilders(getBuilders());
        return select;
    }

    public Select select(Field... fields) {
        Select select=new Select(fields);
        getBuilders().getActionTree().add(select);
        select.setBuilders(getBuilders());
        return select;
    }

    public Select select(String... fieldNames) {
        List<Field> fields=new ArrayList<>();
        for (String fieldName : fieldNames) {
            fields.add(fieldNameToField(fieldName));
        }
        Select select=new Select(fields);
        getBuilders().getActionTree().add(select);
        select.setBuilders(getBuilders());
        return select;
    }


    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if(table==null){
            throw new SelfCheckException("table can not be null in action InsertInto");
        }
    }
}
