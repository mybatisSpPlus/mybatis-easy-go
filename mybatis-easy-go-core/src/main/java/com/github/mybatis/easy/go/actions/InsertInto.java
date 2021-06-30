package com.github.mybatis.easy.go.actions;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.QueryBuilderHelper;
import com.github.mybatis.easy.go.actionAnnotation._Select;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.meta.Table;
import com.github.mybatis.easy.go.step.DmStepGenerator;
import com.github.mybatis.easy.go.step.Oracle10GStepGenerator;
import com.github.mybatis.easy.go.step.Oracle11GStepGenerator;
import com.github.mybatis.easy.go.step.PgStepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.UnSupportProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:28
 */
@_Select
public class InsertInto extends Action {

    Table table;
    @UnSupportProperty(unSupportGenerator = {DmStepGenerator.class,
            Oracle10GStepGenerator.class,
            Oracle11GStepGenerator.class,
            PgStepGenerator.class})
    boolean ignore;

    List<Field> fields = new ArrayList<>();

    List<List<Object>> values = new ArrayList<>();

    public InsertInto(Table table) {
        this.table = table;
    }

    public InsertInto(String tableName) {
        this.table = QueryBuilderHelper.tableNameToTable(tableName);
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

    public InsertInto values(Collection<List<Object>> values) {
        this.values.addAll(values);
        return this;
    }

    public InsertInto values(List<Object>... values) {
        this.values.addAll(Arrays.asList(values));
        return this;
    }

    public InsertInto addValue(List<Object> value) {
        this.values.add(value);
        return this;
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

    public boolean isIgnore() {
        return ignore;
    }

    public InsertInto ignore(boolean ignore) {
        this.ignore = ignore;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (table == null) {
            throw new SelfCheckException("table can not be null in action InsertInto");
        }
    }
}
