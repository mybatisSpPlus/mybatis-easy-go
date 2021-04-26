package com.github.mybatis.sp.plus.plus.conditions;

import com.github.mybatis.sp.plus.plus.Condition;
import com.github.mybatis.sp.plus.plus.exception.SelfCheckException;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:36
 */
public class Not extends Condition {
    Condition notCondition;

    public Not(Condition notCondition) {
        this.notCondition = notCondition;
    }

    public Not() {
    }

    public Condition getNotCondition() {
        return notCondition;
    }

    public Not setNotCondition(Condition notCondition) {
        this.notCondition = notCondition;
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (notCondition==null){
            throw new SelfCheckException("condition can not be null in condition Not");
        }
    }
}
