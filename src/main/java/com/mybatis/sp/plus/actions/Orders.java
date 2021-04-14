package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:48
 */
public class Orders extends Action {

    List<Order> orders=new ArrayList<>();

    public Orders() {
    }

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public Orders(Order... orders) {
        this.orders = Arrays.asList(orders);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(Order... orders) {
        if (this.orders==null){
            this.orders = Arrays.asList(orders);
        }else {
            this.orders.addAll(Arrays.asList(orders));
        }

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
        if (orders.size()==0){
            throw new SelfCheckException("orders can not be empty in action Orders");
        }
    }
}
