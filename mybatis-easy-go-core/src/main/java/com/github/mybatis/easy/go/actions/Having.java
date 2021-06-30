package com.github.mybatis.easy.go.actions;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.Condition;
import com.github.mybatis.easy.go.QueryBuilderHelper;
import com.github.mybatis.easy.go.actionAnnotation._Limit;
import com.github.mybatis.easy.go.actionAnnotation._OrderBy;
import com.github.mybatis.easy.go.actionAnnotation._Union;
import com.github.mybatis.easy.go.actionAnnotation._UnionAll;
import com.github.mybatis.easy.go.conditions.EmptyCondition;
import com.github.mybatis.easy.go.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 17:00
 */
@_OrderBy
@_Limit
@_Union
@_UnionAll
public class Having extends Action {
    List<Condition> conditions = new ArrayList<>();

    public Having() {
    }

    public Having(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public Having(Condition... conditions) {
        this.conditions = QueryBuilderHelper.arrays(conditions);
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public Having setConditions(List<Condition> conditions) {
        this.conditions = conditions;
        return this;
    }

    public Having addConditions(Condition... conditions) {
        if (this.conditions == null) {
            this.conditions = QueryBuilderHelper.arrays(conditions);
        } else {
            this.conditions.addAll(Arrays.asList(conditions));
        }
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        //此处不再进行check，而是将EmptyCondition去掉，如果最终Having中没有条件，在构建step时会跳过;
        Iterator<Condition> iterator = conditions.iterator();
        while (iterator.hasNext()) {
            Condition condition = iterator.next();
            if (condition instanceof EmptyCondition) {
                iterator.remove();
            }
        }
    }

}
