package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.QueryBuilderHelper;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.meta.Order;

import java.util.List;

public class Over extends Function {

    Function windowFunction;

    List<Field> partitions;

    List<Order> orders;

    boolean useRows;

    boolean useRanger;

    String start;

    String end;

    public Over partitions(String... fieldNames) {
        partitions = QueryBuilderHelper.fieldNameToField(fieldNames);
        return this;
    }

    public Over partitions(Field... fields) {
        partitions = QueryBuilderHelper.arrays(fields);
        return this;
    }

    public Over orders(Order... orders) {
        this.orders = QueryBuilderHelper.arrays(orders);
        return this;
    }

    public Over rows() {
        useRows = true;
        return this;
    }

    public Over ranger() {
        useRanger = true;
        return this;
    }

    public Over between(String start, String end) {
        this.start = start;
        this.end = end;
        return this;
    }

    public Function getWindowFunction() {
        return windowFunction;
    }

    public void setWindowFunction(Function windowFunction) {
        this.windowFunction = windowFunction;
    }

    public List<Field> getPartitions() {
        return partitions;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public boolean isUseRows() {
        return useRows;
    }

    public boolean isUseRanger() {
        return useRanger;
    }

    public String getStart() {
        return start;
    }


    public String getEnd() {
        return end;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
