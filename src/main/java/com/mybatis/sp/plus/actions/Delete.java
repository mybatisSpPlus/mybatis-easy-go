package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.tableNameToTable;


/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:29
 */
public class Delete extends Action {

    List<Table> tables=new ArrayList<>();

    public Delete() {
    }

    public Delete(Table... tables) {
        this.tables.addAll(Arrays.asList(tables));
    }

    public Delete(List<Table> tables) {
        this.tables.addAll(tables);
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

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
    }


}
