package com.mybatis.sp.plus.conditions;

import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
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
        if (orCondition.size()==0){
            throw new SelfCheckException("conditions can not be null in condition Or");
        }
        for (Condition condition : orCondition) {
            condition.selfCheck();
        }
    }
}
