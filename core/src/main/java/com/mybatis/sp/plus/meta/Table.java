package com.mybatis.sp.plus.meta;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Meta;
import com.mybatis.sp.plus.exception.SelfCheckException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:44
 */
public class Table  extends Meta {

    List<Action> actions=new ArrayList<>();

    private String schema;

    private String name;

    private Alias alias;

    public Table(){

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

    public void selfCheck() throws SelfCheckException {
        if (StringUtils.isBlank(name)&&actions.size()==0){
            throw new SelfCheckException("table name can not be null where it‘s not a sub query");
        }
    }


}
