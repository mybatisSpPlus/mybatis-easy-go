package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.QueryBuilderHelper;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.tableNameToTable;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:28
 */
public class Select extends Action {

    List<Field> fields=new ArrayList<>();

    boolean distinct=false;

    public Select(List<Field> fields) {
        this.fields.addAll(fields);
    }

    public Select(Field... fields) {
        this.fields.addAll(Arrays.asList(fields));
    }

    public List<Field> getFields() {
        return fields;
    }

    public Select setFields(List<Field> fields) {
        this.fields = fields;
        return this;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public Select setDistinct(boolean distinct) {
        this.distinct = distinct;
        return this;
    }

    public Select distinct() {
        this.distinct = true;
        return this;
    }

    public Select distinct(Field... fields) {
        this.distinct = true;
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public Select distinct(String... fieldNames) {
        this.distinct = true;
        for (String fieldName : fieldNames) {
            this.fields.add(QueryBuilderHelper.fieldNameToField(fieldName));
        }
        return this;
    }

    public Select setFields(Field... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public Select fields(Field... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public Select fields(String... fieldNames) {
        for (String fieldName : fieldNames) {
            this.fields.add(QueryBuilderHelper.fieldNameToField(fieldName));
        }
        return this;
    }

    public From from(Table table) {
        From from=new From(table);
        getBuilders().getActionTree().add(from);
        from.setBuilders(getBuilders());
        return from;
    }

    public From from(String tableName) {
        Table table=tableNameToTable(tableName);
        From from=new From(table);
        getBuilders().getActionTree().add(from);
        from.setBuilders(getBuilders());
        return from;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (fields.size()==0){
            throw new SelfCheckException("fields can not be empty in action select");
        }
    }
}
