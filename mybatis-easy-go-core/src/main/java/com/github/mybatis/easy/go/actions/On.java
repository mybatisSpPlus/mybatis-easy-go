package com.github.mybatis.easy.go.actions;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.Condition;
import com.github.mybatis.easy.go.conditions.EmptyCondition;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.github.mybatis.easy.go.QueryBuilderHelper.arrays;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:58
 */
@_Set
@_Where
@_Union
@_UnionAll
@_CrossJoin
@_LeftJoin
@_RightJoin
@_InnerJoin
@_FullJoin
@_GroupBy
@_OrderBy
@_Limit
public class On extends Action {

    List<Condition> conditions = new ArrayList<>();

    public On() {
    }

    public On(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public On(Condition... conditions) {
        this.conditions = arrays(conditions);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public On setConditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public On addConditions(Condition... conditions) {
        if (this.conditions == null) {
            this.conditions = arrays(conditions);
        } else {
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
            throw new SelfCheckException("conditions can not be empty in action On");
        }
    }
}
