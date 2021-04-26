package com.github.mybatis.sp.plus.plus.actions;

import com.github.mybatis.sp.plus.plus.Action;
import com.github.mybatis.sp.plus.plus.Condition;
import com.github.mybatis.sp.plus.plus.conditions.EmptyCondition;
import com.github.mybatis.sp.plus.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.annotation._Limit;
import com.mybatis.sp.plus.annotation._OrderBy;
import com.mybatis.sp.plus.annotation._Union;
import com.mybatis.sp.plus.annotation._UnionAll;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static com.github.mybatis.sp.plus.plus.QueryBuilderHelper.arrays;

/**
 * @author zhouyu74748585@hotmail.com
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
        this.conditions = arrays(conditions);
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
            this.conditions = arrays(conditions);
        }else {
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
