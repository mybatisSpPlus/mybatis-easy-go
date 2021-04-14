package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 17:00
 */
public class Having extends Action {
    List<Condition> conditions=new ArrayList<>();

    public Having() {
    }

    public Having(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Having(Condition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public Having setConditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public Having addConditions(Condition... conditions) {
        if (this.conditions==null) {
            this.conditions = Arrays.asList(conditions);
        }else {
            this.conditions.addAll(Arrays.asList(conditions));
        }
        return this;
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

    @Override
    public void selfCheck() throws SelfCheckException {
        if (conditions.size()==0){
            throw new SelfCheckException("conditions can not be empty in action Having");
        }else {
            for (Condition condition : conditions) {
                condition.selfCheck();
            }
        }
    }
}
