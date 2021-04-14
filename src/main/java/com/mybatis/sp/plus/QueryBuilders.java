package com.mybatis.sp.plus;

import com.mybatis.sp.plus.actions.Delete;
import com.mybatis.sp.plus.actions.Insert;
import com.mybatis.sp.plus.actions.Select;
import com.mybatis.sp.plus.actions.Update;
import com.mybatis.sp.plus.meta.Field;

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

    public Update update() {

        return null;
    }

    public Insert insert() {

        return null;
    }

    public Delete delete() {

        return null;
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
