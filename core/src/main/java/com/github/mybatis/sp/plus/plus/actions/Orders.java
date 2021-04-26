package com.github.mybatis.sp.plus.plus.actions;

import com.github.mybatis.sp.plus.plus.Action;
import com.github.mybatis.sp.plus.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.plus.meta.Order;
import com.mybatis.sp.plus.annotation._Limit;
import com.mybatis.sp.plus.annotation._Union;
import com.mybatis.sp.plus.annotation._UnionAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.github.mybatis.sp.plus.plus.QueryBuilderHelper.arrays;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:48
 */
@_Limit
@_Union
@_UnionAll
public class Orders extends Action {

    List<Order> orders=new ArrayList<>();

    public Orders() {
    }

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public Orders(Order... orders) {
        this.orders = arrays(orders);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(Order... orders) {
        if (this.orders==null){
            this.orders = arrays(orders);
        }else {
            this.orders.addAll(Arrays.asList(orders));
        }

    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (orders.size()==0){
            throw new SelfCheckException("orders can not be empty in action Orders");
        }
    }
}
