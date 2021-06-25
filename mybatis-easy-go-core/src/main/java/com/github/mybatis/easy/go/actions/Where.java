package com.github.mybatis.easy.go.actions;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.Condition;
import com.github.mybatis.easy.go.QueryBuilderHelper;
import com.github.mybatis.easy.go.annotation.*;
import com.github.mybatis.easy.go.conditions.EmptyCondition;
import com.github.mybatis.easy.go.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:58
 */
@_Union
@_UnionAll
@_GroupBy
@_OrderBy
@_Limit
public class Where extends Action {
    List<Condition> conditions = new ArrayList<>();

    public Where() {
    }

    public Where(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Where(Condition... conditions) {
        this.conditions = QueryBuilderHelper.arrays(conditions);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public Where setConditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public Where addConditions(Condition... conditions) {
        if (this.conditions == null) {
            this.conditions = QueryBuilderHelper.arrays(conditions);
        } else {
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
