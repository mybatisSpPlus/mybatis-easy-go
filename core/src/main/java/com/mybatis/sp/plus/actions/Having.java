package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.annotation._Limit;
import com.mybatis.sp.plus.annotation._OrderBy;
import com.mybatis.sp.plus.annotation._Union;
import com.mybatis.sp.plus.annotation._UnionAll;
import com.mybatis.sp.plus.conditions.EmptyCondition;
import com.mybatis.sp.plus.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 17:00
 */
@_OrderBy
@_Limit
@_Union
@_UnionAll
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


    @Override
    public void selfCheck() throws SelfCheckException {
        //先将EmptyCondition去掉;
        Iterator<Condition> iterator = conditions.iterator();
        while (iterator.hasNext()) {
            Condition condition = iterator.next();
            if (condition instanceof EmptyCondition) {
                iterator.remove();
            }
        }
        if (conditions.size() == 0) {
            throw new SelfCheckException("conditions can not be empty in action Having");
        }
    }

}
