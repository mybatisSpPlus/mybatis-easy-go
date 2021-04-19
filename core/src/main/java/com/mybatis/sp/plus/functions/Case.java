package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Condition;
import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/19 17:37
 */
public class Case extends Function {
    Condition when;
    Object thenValue;
    Object elseValue;

    public Case() {
    }

    public Case(Condition when, Object thenValue, Object elseValue) {
        this.when = when;
        this.thenValue = thenValue;
        this.elseValue = elseValue;
    }

    public Case when(Condition when) {
        this.when = when;
        return this;
    }

    public Case then(Object thenValue) {
        this.thenValue = thenValue;
        return this;
    }

    public Case els(Object elseValue) {
        this.elseValue = elseValue;
        return this;
    }

    public Condition getWhen() {
        return when;
    }

    public Case setWhen(Condition when) {
        this.when = when;
        return this;
    }

    public Object getThenValue() {
        return thenValue;
    }

    public Case setThenValue(Object thenValue) {
        this.thenValue = thenValue;
        return this;
    }

    public Object getElseValue() {
        return elseValue;
    }

    public Case setElseValue(Object elseValue) {
        this.elseValue = elseValue;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (when == null || thenValue == null || elseValue == null) {
            throw new SelfCheckException("when,thenValue,elseValue can not be null in CaseWhen");
        }
    }
}
