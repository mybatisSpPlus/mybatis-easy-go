package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.actions.*;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.meta.Order;
import com.github.mybatis.easy.go.meta.Table;
import com.github.mybatis.easy.go.methodAnnotation.*;
import com.github.mybatis.easy.go.sourceAnnotation.FunctionBag;
import com.github.mybatis.easy.go.sourceAnnotation.FunctionSource;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/18 8:36
 */
@FunctionBag
public class ActionFunctionSource {

    @FunctionSource(targetAnnotation = _Select.class, requiredClass = {Select.class})
    public static Select select(Action action) {
        Select select = new Select();
        action.getBuilders().getActionTree().add(select);
        select.setBuilders(action.getBuilders());
        return select;
    }

    @FunctionSource(targetAnnotation = _Select.class, requiredClass = {Select.class, Field.class})
    public static Select select(Action action, Field... fields) {
        Select select = new Select(fields);
        action.getBuilders().getActionTree().add(select);
        select.setBuilders(action.getBuilders());
        return select;
    }

    @FunctionSource(targetAnnotation = _Select.class, requiredClass = {Select.class})
    public static Select select(Action action, String... fieldNames) {
        Select select = new Select(QueryBuilderHelper.fieldNameToField(fieldNames));
        action.getBuilders().getActionTree().add(select);
        select.setBuilders(action.getBuilders());
        return select;
    }

    @FunctionSource(targetAnnotation = _From.class, requiredClass = {From.class, Table.class})
    public static From from(Action action, Table table) {
        From from = new From(table);
        action.getBuilders().getActionTree().add(from);
        from.setBuilders(action.getBuilders());
        return from;
    }

    @FunctionSource(targetAnnotation = _From.class, requiredClass = {From.class})
    public static From from(Action action, String tableName) {
        Table table = QueryBuilderHelper.tableNameToTable(tableName);
        From from = new From(table);
        action.getBuilders().getActionTree().add(from);
        from.setBuilders(action.getBuilders());
        return from;
    }

    @FunctionSource(targetAnnotation = _Set.class, requiredClass = {Set.class})
    public static Set set(Action action) {
        Set set = new Set();
        action.getBuilders().getActionTree().add(set);
        set.setBuilders(action.getBuilders());
        return set;
    }

    @FunctionSource(targetAnnotation = _Where.class, requiredClass = {Where.class, Condition.class})
    public static Where where(Action action, Condition... condition) {
        Where where = new Where(condition);
        action.getBuilders().getActionTree().add(where);
        where.setBuilders(action.getBuilders());
        return where;
    }

    @FunctionSource(targetAnnotation = _Union.class, requiredClass = {Union.class})
    public static Union union(Action action) {
        Union union = new Union();
        action.getBuilders().getActionTree().add(union);
        union.setBuilders(action.getBuilders());
        return union;
    }

    @FunctionSource(targetAnnotation = _UnionAll.class, requiredClass = {UnionAll.class})
    public static UnionAll unionAll(Action action) {
        UnionAll unionAll = new UnionAll();
        action.getBuilders().getActionTree().add(unionAll);
        unionAll.setBuilders(action.getBuilders());
        return unionAll;
    }

    @FunctionSource(targetAnnotation = _CrossJoin.class, requiredClass = {CrossJoin.class, Table.class})
    public static CrossJoin crossJoin(Action action, Table table) {
        CrossJoin crossJoin = new CrossJoin(table);
        action.getBuilders().getActionTree().add(crossJoin);
        crossJoin.setBuilders(action.getBuilders());
        return crossJoin;
    }

    @FunctionSource(targetAnnotation = _CrossJoin.class, requiredClass = {CrossJoin.class})
    public static CrossJoin crossJoin(Action action, String tableName) {
        CrossJoin crossJoin = new CrossJoin(QueryBuilderHelper.tableNameToTable(tableName));
        action.getBuilders().getActionTree().add(crossJoin);
        crossJoin.setBuilders(action.getBuilders());
        return crossJoin;
    }

    @FunctionSource(targetAnnotation = _LeftJoin.class, requiredClass = {LeftJoin.class, Table.class})
    public static LeftJoin leftJoin(Action action, Table table) {
        LeftJoin leftJoin = new LeftJoin(table);
        action.getBuilders().getActionTree().add(leftJoin);
        leftJoin.setBuilders(action.getBuilders());
        return leftJoin;
    }

    @FunctionSource(targetAnnotation = _LeftJoin.class, requiredClass = {LeftJoin.class})
    public static LeftJoin leftJoin(Action action, String tableName) {
        LeftJoin leftJoin = new LeftJoin(QueryBuilderHelper.tableNameToTable(tableName));
        action.getBuilders().getActionTree().add(leftJoin);
        leftJoin.setBuilders(action.getBuilders());
        return leftJoin;
    }

    @FunctionSource(targetAnnotation = _InnerJoin.class, requiredClass = {InnerJoin.class, Table.class})
    public static InnerJoin innerJoin(Action action, Table table) {
        InnerJoin innerJoin = new InnerJoin(table);
        action.getBuilders().getActionTree().add(innerJoin);
        innerJoin.setBuilders(action.getBuilders());
        return innerJoin;
    }

    @FunctionSource(targetAnnotation = _InnerJoin.class, requiredClass = {InnerJoin.class})
    public static InnerJoin innerJoin(Action action, String tableName) {
        InnerJoin innerJoin = new InnerJoin(QueryBuilderHelper.tableNameToTable(tableName));
        action.getBuilders().getActionTree().add(innerJoin);
        innerJoin.setBuilders(action.getBuilders());
        return innerJoin;
    }

    @FunctionSource(targetAnnotation = _RightJoin.class, requiredClass = {RightJoin.class, Table.class})
    public static RightJoin rightJoin(Action action, Table table) {
        RightJoin rightJoin = new RightJoin(table);
        action.getBuilders().getActionTree().add(rightJoin);
        rightJoin.setBuilders(action.getBuilders());
        return rightJoin;
    }

    @FunctionSource(targetAnnotation = _RightJoin.class, requiredClass = {RightJoin.class})
    public static RightJoin rightJoin(Action action, String tableName) {
        RightJoin rightJoin = new RightJoin(QueryBuilderHelper.tableNameToTable(tableName));
        action.getBuilders().getActionTree().add(rightJoin);
        rightJoin.setBuilders(action.getBuilders());
        return rightJoin;
    }

    @FunctionSource(targetAnnotation = _FullJoin.class, requiredClass = {FullJoin.class, Table.class})
    public static FullJoin fullJoin(Action action, Table table) {
        FullJoin fullJoin = new FullJoin(table);
        action.getBuilders().getActionTree().add(fullJoin);
        fullJoin.setBuilders(action.getBuilders());
        return fullJoin;
    }

    @FunctionSource(targetAnnotation = _FullJoin.class, requiredClass = {FullJoin.class})
    public static FullJoin fullJoin(Action action, String tableName) {
        FullJoin fullJoin = new FullJoin(QueryBuilderHelper.tableNameToTable(tableName));
        action.getBuilders().getActionTree().add(fullJoin);
        fullJoin.setBuilders(action.getBuilders());
        return fullJoin;
    }

    @FunctionSource(targetAnnotation = _On.class, requiredClass = {On.class, Condition.class})
    public static On on(Action action, Condition... conditions) {
        On on = new On(conditions);
        action.getBuilders().getActionTree().add(on);
        on.setBuilders(action.getBuilders());
        return on;
    }

    @FunctionSource(targetAnnotation = _GroupBy.class, requiredClass = {GroupBy.class, Field.class})
    public static GroupBy groupBy(Action action, Field... fields) {
        GroupBy groupBy = new GroupBy(fields);
        action.getBuilders().getActionTree().add(groupBy);
        groupBy.setBuilders(action.getBuilders());
        return groupBy;
    }

    @FunctionSource(targetAnnotation = _GroupBy.class, requiredClass = {GroupBy.class})
    public static GroupBy groupBy(Action action, String... fieldNames) {
        GroupBy groupBy = new GroupBy(QueryBuilderHelper.fieldNameToField(fieldNames));
        action.getBuilders().getActionTree().add(groupBy);
        groupBy.setBuilders(action.getBuilders());
        return groupBy;
    }

    @FunctionSource(targetAnnotation = _Having.class, requiredClass = {Having.class, Condition.class})
    public static Having having(Action action, Condition... condition) {
        Having having = new Having(condition);
        action.getBuilders().getActionTree().add(having);
        having.setBuilders(action.getBuilders());
        return having;
    }

    @FunctionSource(targetAnnotation = _OrderBy.class, requiredClass = {Orders.class, Order.class})
    public static Orders orderBy(Action action, Order... order) {
        Orders orders = new Orders(order);
        action.getBuilders().getActionTree().add(orders);
        orders.setBuilders(action.getBuilders());
        return orders;
    }

    @FunctionSource(targetAnnotation = _Limit.class, requiredClass = {Limit.class})
    public static Limit limit(Action action, int limit, int offset) {
        Limit limit1 = new Limit(limit, offset);
        action.getBuilders().getActionTree().add(limit1);
        limit1.setBuilders(action.getBuilders());
        return limit1;
    }

    @FunctionSource(targetAnnotation = _Limit.class, requiredClass = {Limit.class})
    public static Limit limit(Action action, int limit) {
        Limit limit1 = new Limit(limit);
        action.getBuilders().getActionTree().add(limit1);
        limit1.setBuilders(action.getBuilders());
        return limit1;
    }

    @FunctionSource(targetAnnotation = _Limit.class, requiredClass = {Limit.class})
    public static Limit limit(Action action, Limit limit) {
        action.getBuilders().getActionTree().add(limit);
        limit.setBuilders(action.getBuilders());
        return limit;
    }
}
