package com.mybatis.sp.plus;

import com.mybatis.sp.plus.actions.*;
import com.mybatis.sp.plus.meta.Field;
import com.mybatis.sp.plus.meta.Table;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:26
 */
public class QueryBuilders {

    private List<Action> actionTree =new ArrayList<>();

    public Select select() {
        Select select=new Select();
        select.setBuilders(this);
        actionTree.add(select);
        return select;
    }

    public Select select(Field... fields) {
        Select select=new Select(fields);
        select.setBuilders(this);
        actionTree.add(select);
        return select;
    }
    public Select select(List<Field> fields) {
        Select select=new Select(fields);
        select.setBuilders(this);
        actionTree.add(select);
        return select;
    }

    public Update update(Table table) {
        Update update=new Update(table);
        update.setBuilders(this);
        actionTree.add(update);
        return update;
    }

    public InsertInto insertInto(Table table) {
        InsertInto insertInto=new InsertInto(table);
        insertInto.setBuilders(this);
        actionTree.add(insertInto);
        return insertInto;
    }

    public Delete delete() {
        Delete delete=new Delete();
        delete.setBuilders(this);
        actionTree.add(delete);
        return delete;
    }

    public Delete delete(Table... table) {
        Delete delete=new Delete(table);
        delete.setBuilders(this);
        actionTree.add(delete);
        return delete;
    }

    public Truncate truncate(Table table) {
        Truncate truncate=new Truncate(table);
        truncate.setBuilders(this);
        actionTree.add(truncate);
        return truncate;
    }

    public List<Action> getActionTree() {
        return actionTree;
    }

    public QueryBuilders addActionToTree(Action... actions){
        actionTree.addAll(Arrays.asList(actions));
        return this;
    }

    public void setActionTree(List<Action> actionTree) {
        this.actionTree = actionTree;
    }
}
