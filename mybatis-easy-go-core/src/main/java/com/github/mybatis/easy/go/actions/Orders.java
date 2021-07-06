package com.github.mybatis.easy.go.actions;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.QueryBuilderHelper;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.EmptyOrder;
import com.github.mybatis.easy.go.meta.Order;
import com.github.mybatis.easy.go.methodAnnotation._Limit;
import com.github.mybatis.easy.go.methodAnnotation._Union;
import com.github.mybatis.easy.go.methodAnnotation._UnionAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:48
 */
@_Limit
@_Union
@_UnionAll
public class Orders extends Action {

    List<Order> orders = new ArrayList<>();

    public Orders() {
    }

    public Orders(List<Order> orders) {
        this.orders = orders;
    }

    public Orders(Order... orders) {
        this.orders = QueryBuilderHelper.arrays(orders);
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setOrders(Order... orders) {
        if (this.orders == null) {
            this.orders = QueryBuilderHelper.arrays(orders);
        } else {
            this.orders.addAll(Arrays.asList(orders));
        }

    }

    @Override
    public void selfCheck() throws SelfCheckException {
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order instanceof EmptyOrder) {
                iterator.remove();
            }
        }
    }
}
