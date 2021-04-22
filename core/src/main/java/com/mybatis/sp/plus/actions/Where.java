package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.annotation.*;
import com.mybatis.sp.plus.conditions.EmptyCondition;
import com.mybatis.sp.plus.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.mybatis.sp.plus.QueryBuilderHelper.arrays;

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
        this.conditions = arrays(conditions);
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
            this.conditions = arrays(conditions);
        }else {
            this.conditions.addAll(Arrays.asList(conditions));
        }
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        //此处不再进行check，而是将EmptyCondition去掉，如果最终where中没有条件，在构建step时会跳过;
        Iterator<Condition> iterator = conditions.iterator();
        while (iterator.hasNext()) {
            Condition condition = iterator.next();
            if (condition instanceof EmptyCondition) {
                iterator.remove();
            }
        }
    }
}
