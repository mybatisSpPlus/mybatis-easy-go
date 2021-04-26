package com.github.mybatis.sp.plus.conditions;

import com.github.mybatis.sp.plus.Condition;
import com.github.mybatis.sp.plus.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:39
 */
public class And extends Condition {

    List<Condition> andCondition=new ArrayList<>();

    public And() {
    }

    public And(List<Condition> andCondition) {
        this.andCondition = andCondition;
    }

    public And(Condition... andCondition) {
        this.andCondition.addAll(Arrays.asList(andCondition));
    }

    public List<Condition> getAndCondition() {
        return andCondition;
    }

    public And setAndCondition(List<Condition> andCondition) {
        this.andCondition = andCondition;
        return this;
    }

    public And addAnd(Condition... conditions){
        andCondition.addAll(Arrays.asList(conditions));
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (andCondition.size()==0){
            throw new SelfCheckException("conditions can not be null in condition And");
        }
    }
}
