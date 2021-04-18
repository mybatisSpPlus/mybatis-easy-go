package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.annotation.*;
import com.mybatis.sp.plus.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:58
 */
@_Union
@_UnionAll
@_GroupBy
@_OrderBy
@_Limit
public class Where extends Action {
    List<Condition> conditions=new ArrayList<>();

    public Where() {
    }

    public Where(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Where(Condition... conditions) {
        this.conditions = Arrays.asList(conditions);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public Where setConditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public Where addConditions(Condition... conditions) {
        if (this.conditions==null) {
            this.conditions = Arrays.asList(conditions);
        }else {
            this.conditions.addAll(Arrays.asList(conditions));
        }
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (conditions.size()==0){
            throw new SelfCheckException("conditions can not be empty in action Where");
        }else {
            for (Condition condition : conditions) {
                condition.selfCheck();
            }
        }
    }
}
