package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Alias;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Order;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.fieldNameToField;
import static com.mybatis.sp.plus.QueryBuilderHelper.tableNameToTable;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 14:58
 */
public class From extends Action {

    Table table;

    public From(Table table) {
        this.table = table;
    }

    public Table getTable() {
        return table;
    }

    public Where where(Condition... condition) {
        Where where=new Where(condition);
        getBuilders().getActionTree().add(where);
        where.setBuilders(getBuilders());
        return where;
    }

    public Union union(){
        Union union=new Union();
        getBuilders().getActionTree().add(union);
        union.setBuilders(getBuilders());
        return union;
    }

    public UnionAll unionAll(){
        UnionAll unionAll=new UnionAll();
        getBuilders().getActionTree().add(unionAll);
        unionAll.setBuilders(getBuilders());
        return unionAll;
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

    public GroupBy groupBy(Field... fields) {
        GroupBy groupBy=new GroupBy(fields);
        getBuilders().getActionTree().add(groupBy);
        groupBy.setBuilders(getBuilders());
        return groupBy;
    }

    public GroupBy groupBy(String... fieldNames) {
        List<Field> fields=new ArrayList<>();
        for (String fieldName : fieldNames) {
            fields.add(fieldNameToField(fieldName));
        }
        GroupBy groupBy=new GroupBy(fields);
        getBuilders().getActionTree().add(groupBy);
        groupBy.setBuilders(getBuilders());
        return groupBy;
    }

    public Orders orderBy(Order... order) {
        Orders orders=new Orders(order);
        getBuilders().getActionTree().add(orders);
        orders.setBuilders(getBuilders());
        return orders;
    }

    public Limit limit(int limit, int offset) {
        Limit limit1=new Limit(limit, offset);
        getBuilders().getActionTree().add(limit1);
        limit1.setBuilders(getBuilders());
        return limit1;
    }

    public Limit limit(int limit) {
        Limit limit1=new Limit(limit);
        getBuilders().getActionTree().add(limit1);
        limit1.setBuilders(getBuilders());
        return limit1;
    }

    public Limit limit(Limit limit) {
        getBuilders().getActionTree().add(limit);
        limit.setBuilders(getBuilders());
        return limit;
    }

    public From as(Alias alias) {
        table.setAlias(alias);
        return this;
    }

    public From as(String alias) {
        table.setAlias(new Alias(alias));
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if(table==null){
            throw new SelfCheckException("table can not be null in action From");
        }
    }


}
