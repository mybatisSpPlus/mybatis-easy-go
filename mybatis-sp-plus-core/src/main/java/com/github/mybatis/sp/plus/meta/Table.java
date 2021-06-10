package com.github.mybatis.sp.plus.meta;

import com.github.mybatis.sp.plus.Action;
import com.github.mybatis.sp.plus.Meta;
import com.github.mybatis.sp.plus.exception.SelfCheckException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:44
 */
public class Table extends Meta {

    List<Action> actions = new ArrayList<>();
    /**
     * 比如mysql的BINARY，前缀，后缀会原样体现在sql中
     */
    private String specialPrefix;
    /**
     * 特殊后缀，与前缀一起可以组成部分暂时不持支的函数等
     */
    private String specialPostfix;

    private String schema;

    private String name;

    private Alias alias;

    public Table() {

    }

    public Table(String name) {
        this.name = name.trim();
    }

    public Table(String schema, String name) {
        this.schema = schema.trim();
        this.name = name.trim();
    }

    public Table(String name, Alias alias) {
        this.name = name.trim();
        this.alias = alias;
    }

    public Table(String schema, String name, Alias alias) {
        this.schema = schema.trim();
        this.name = name.trim();
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public Table setName(String name) {
        this.name = name.trim();
        return this;
    }

    public List<Action> getActions() {
        return actions;
    }

    public Table setActions(List<Action> actions) {
        this.actions = actions;
        return this;
    }

    public Alias getAlias() {
        return alias;
    }

    public Table setAlias(Alias alias) {
        this.alias = alias;
        return this;
    }

    public Table as(Alias alias) {
        return setAlias(alias);
    }

    public Table as(String alias) {
        return setAlias(new Alias(alias));
    }

    public String getSchema() {
        return schema;
    }

    public Table setSchema(String schema) {
        this.schema = schema.trim();
        return this;
    }

    public String getSpecialPrefix() {
        return specialPrefix;
    }

    public Table setSpecialPrefix(String specialPrefix) {
        this.specialPrefix = specialPrefix;
        return this;
    }

    public Table prefix(String specialPrefix) {
        this.specialPrefix = specialPrefix;
        return this;
    }

    public String getSpecialPostfix() {
        return specialPostfix;
    }

    public Table setSpecialPostfix(String specialPostfix) {
        this.specialPostfix = specialPostfix;
        return this;
    }

    public Table postfix(String specialPostfix) {
        this.specialPostfix = specialPostfix;
        return this;
    }

    public void selfCheck() throws SelfCheckException {
        if (StringUtils.isBlank(name) && actions.size() == 0) {
            throw new SelfCheckException("table name can not be null where it‘s not a sub query");
        }
    }


}
