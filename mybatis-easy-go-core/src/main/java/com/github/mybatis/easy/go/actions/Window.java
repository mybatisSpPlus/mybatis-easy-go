package com.github.mybatis.easy.go.actions;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.QueryBuilderHelper;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.meta.Order;
import com.github.mybatis.easy.go.methodAnnotation._Limit;
import com.github.mybatis.easy.go.methodAnnotation._OrderBy;
import com.github.mybatis.easy.go.methodAnnotation._Union;
import com.github.mybatis.easy.go.methodAnnotation._UnionAll;
import com.github.mybatis.easy.go.step.Mysql5StepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.UnSupport;
import com.github.mybatis.easy.go.windowFunctions.frame.Frame;

import java.util.List;

/**
 * 窗口函数的窗口对象
 */
@_Union
@_UnionAll
@_OrderBy
@_Limit
@UnSupport(unSupportGenerator = {Mysql5StepGenerator.class})
public class Window extends Action {

    String windowAlias;

    List<Field> partitions;

    List<Order> orders;

    boolean useRows;

    boolean useRanger;

    Frame start;

    Frame end;

    public Window(String windowAlias) {
        this.windowAlias = windowAlias;
    }

    public Window as() {
        return this;
    }

    public Window partitions(String... fieldNames) {
        partitions = QueryBuilderHelper.fieldNameToField(fieldNames);
        return this;
    }

    public Window partitions(Field... fields) {
        partitions = QueryBuilderHelper.arrays(fields);
        return this;
    }

    public Window orders(Order... orders) {
        this.orders = QueryBuilderHelper.arrays(orders);
        return this;
    }

    public Window rows() {
        useRows = true;
        return this;
    }

    public Window ranger() {
        useRanger = true;
        return this;
    }

    public Window between(Frame start, Frame end) {
        this.start = start;
        this.end = end;
        return this;
    }

    public List<Field> getPartitions() {
        return partitions;
    }

    public void setPartitions(List<Field> partitions) {
        this.partitions = partitions;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public boolean isUseRows() {
        return useRows;
    }

    public void setUseRows(boolean useRows) {
        this.useRows = useRows;
    }

    public boolean isUseRanger() {
        return useRanger;
    }

    public void setUseRanger(boolean useRanger) {
        this.useRanger = useRanger;
    }

    public Frame getStart() {
        return start;
    }

    public void setStart(Frame start) {
        this.start = start;
    }

    public Frame getEnd() {
        return end;
    }

    public void setEnd(Frame end) {
        this.end = end;
    }

    public String getWindowAlias() {
        return windowAlias;
    }

    public void setWindowAlias(String windowAlias) {
        this.windowAlias = windowAlias;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
