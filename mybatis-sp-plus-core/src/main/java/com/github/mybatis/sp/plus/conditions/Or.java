package com.github.mybatis.sp.plus.conditions;

import com.github.mybatis.sp.plus.Condition;
import com.github.mybatis.sp.plus.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:40
 */
public class Or extends Condition {

    List<Condition> orCondition =new ArrayList<>();

    public Or() {
    }

    public Or(List<Condition> orCondition) {
        this.orCondition = orCondition;
    }

    public Or(Condition... orCondition) {
        this.orCondition.addAll(Arrays.asList(orCondition));
    }

    public List<Condition> getOrCondition() {
        return orCondition;
    }

    public Or setOrCondition(List<Condition> orCondition) {
        this.orCondition = orCondition;
        return this;
    }

    public Or addOr(Condition... conditions){
        orCondition.addAll(Arrays.asList(conditions));
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        //此处不再进行check，而是将EmptyCondition去掉，如果最终or中没有条件，在构建step时直接跳过
        Iterator<Condition> iterator = orCondition.iterator();
        while (iterator.hasNext()) {
            Condition condition = iterator.next();
            if (condition instanceof EmptyCondition) {
                iterator.remove();
            }
        }
    }
}
