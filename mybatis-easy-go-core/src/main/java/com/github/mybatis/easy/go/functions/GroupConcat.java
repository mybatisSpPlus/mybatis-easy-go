package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.QueryBuilderHelper;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.EmptyOrder;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.meta.Order;
import com.github.mybatis.easy.go.step.DmStepGenerator;
import com.github.mybatis.easy.go.step.Oracle10GStepGenerator;
import com.github.mybatis.easy.go.step.Oracle11GStepGenerator;
import com.github.mybatis.easy.go.step.PgStepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.UnSupportProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * 分组拼接字符串
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 21:40
 */
public class GroupConcat extends Function {

    List<Field> objs = new ArrayList<>();

    @UnSupportProperty(unSupportGenerator = {DmStepGenerator.class,
            Oracle10GStepGenerator.class,
            PgStepGenerator.class})
    List<Order> orders = new ArrayList<>();

    @UnSupportProperty(unSupportGenerator = {DmStepGenerator.class,
            Oracle10GStepGenerator.class,
            PgStepGenerator.class})
    String separator;

    @UnSupportProperty(unSupportGenerator = {DmStepGenerator.class,
            Oracle10GStepGenerator.class,
            Oracle11GStepGenerator.class,
            PgStepGenerator.class})
    boolean distinct;

    public GroupConcat() {
    }

    public GroupConcat(List<Field> objs) {
        this.objs = objs;
    }

    public GroupConcat(Field... objs) {
        this.objs = QueryBuilderHelper.arrays(objs);
    }

    public GroupConcat(boolean distinct, Field... objs) {
        this.distinct = distinct;
        this.objs = QueryBuilderHelper.arrays(objs);
    }

    public GroupConcat(boolean distinct, String separator, Field... objs) {
        this.distinct = distinct;
        this.separator = separator;
        this.objs = QueryBuilderHelper.arrays(objs);
    }

    public GroupConcat(String separator, Field... objs) {
        this.separator = separator;
        this.objs = QueryBuilderHelper.arrays(objs);
    }

    public List<Field> getObjs() {
        return objs;
    }

    public void setObjs(List<Field> objs) {
        this.objs = objs;
    }

    public void addObjs(Field... objs) {
        if (this.objs == null) {
            this.objs = QueryBuilderHelper.arrays(objs);
        } else {
            this.objs.addAll(Arrays.asList(objs));
        }
    }

    public List<Order> getOrders() {
        return orders;
    }

    public GroupConcat withOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }

    public String getSeparator() {
        return separator;
    }

    public GroupConcat withSeparator(String separator) {
        this.separator = separator;
        return this;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public GroupConcat distinct() {
        this.distinct = true;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (objs.size() == 0) {
            throw new SelfCheckException("objs can not be null in function GroupConcat");
        }
        Iterator<Order> iterator = orders.iterator();
        while (iterator.hasNext()) {
            Order order = iterator.next();
            if (order instanceof EmptyOrder) {
                iterator.remove();
            }
        }
    }
}
