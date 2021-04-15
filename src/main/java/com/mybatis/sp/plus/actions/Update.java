package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Table;

import static com.mybatis.sp.plus.QueryBuilderHelper.tableNameToTable;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:28
 */
public class Update extends Action {

    Table table;

    public Update(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public Set set(){
        Set set=new Set();
        getBuilders().getActionTree().add(set);
        set.setBuilders(getBuilders());
        return set;
    }

    public LeftJoin leftJoin(Table table){
        LeftJoin leftJoin=new LeftJoin(table);
        getBuilders().getActionTree().add(leftJoin);
        leftJoin.setBuilders(getBuilders());
        return leftJoin;
    }

    public InnerJoin innerJoin(Table table){
        InnerJoin innerJoin=new InnerJoin(table);
        getBuilders().getActionTree().add(innerJoin);
        innerJoin.setBuilders(getBuilders());
        return innerJoin;
    }

    public RightJoin rightJoin(Table table){
        RightJoin rightJoin=new RightJoin(table);
        getBuilders().getActionTree().add(rightJoin);
        rightJoin.setBuilders(getBuilders());
        return rightJoin;
    }

    public FullJoin fullJoin(Table table){
        FullJoin fullJoin=new FullJoin(table);
        getBuilders().getActionTree().add(fullJoin);
        fullJoin.setBuilders(getBuilders());
        return fullJoin;
    }

    public LeftJoin leftJoin(String tableName){
        LeftJoin leftJoin=new LeftJoin(tableNameToTable(tableName));
        getBuilders().getActionTree().add(leftJoin);
        leftJoin.setBuilders(getBuilders());
        return leftJoin;
    }

    public InnerJoin innerJoin(String tableName){
        InnerJoin innerJoin=new InnerJoin(tableNameToTable(tableName));
        getBuilders().getActionTree().add(innerJoin);
        innerJoin.setBuilders(getBuilders());
        return innerJoin;
    }

    public RightJoin rightJoin(String tableName){
        RightJoin rightJoin=new RightJoin(tableNameToTable(tableName));
        getBuilders().getActionTree().add(rightJoin);
        rightJoin.setBuilders(getBuilders());
        return rightJoin;
    }

    public FullJoin fullJoin(String tableName){
        FullJoin fullJoin=new FullJoin(tableNameToTable(tableName));
        getBuilders().getActionTree().add(fullJoin);
        fullJoin.setBuilders(getBuilders());
        return fullJoin;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if(table==null){
            throw new SelfCheckException("table can not be null in action Update");
        }
    }
}
