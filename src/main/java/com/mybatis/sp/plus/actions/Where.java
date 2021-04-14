package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.fieldNameToField;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:58
 */
public class Where extends Action {
    List<Condition> conditions=new ArrayList<>();

    public Where() {
    }

    public Where(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Where(Condition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public Where setConditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public Where addConditions(Condition... conditions) {
        if (this.conditions==null) {
            this.conditions = Arrays.asList(conditions);
        }else {
            this.conditions.addAll(Arrays.asList(conditions));
        }
        return this;
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

    public Orders order(Order... order) {
        Orders orders=new Orders(order);
        getBuilders().getActionTree().add(orders);
        orders.setBuilders(getBuilders());
        return orders;
    }

    public Limit limit(Limit limit) {
        getBuilders().getActionTree().add(limit);
        limit.setBuilders(getBuilders());
        return limit;
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


    @Override
    public void selfCheck() throws SelfCheckException {
        if (conditions.size()==0){
            throw new SelfCheckException("conditions can not be empty in action Where");
        }else {
            for (Condition condition : conditions) {
                condition.selfCheck();
            }
        }
    }
}
