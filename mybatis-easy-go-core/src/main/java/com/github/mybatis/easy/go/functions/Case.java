package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Condition;
import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/19 17:37
 */
public class Case extends Function {
    List<Condition> when = new ArrayList<>();
    List<Object> thenValue = new ArrayList<>();
    Object elseValue;

    public Case() {
    }

    public Case when(Condition when) {
        this.when.add(when);
        return this;
    }

    public Case then(Object thenValue) {
        this.thenValue.add(thenValue);
        return this;
    }

    public Case els(Object elseValue) {
        this.elseValue = elseValue;
        return this;
    }

    public Object getElseValue() {
        return elseValue;
    }

    public Case setElseValue(Object elseValue) {
        this.elseValue = elseValue;
        return this;
    }

    public List<Condition> getWhen() {
        return when;
    }

    public void setWhen(List<Condition> when) {
        this.when = when;
    }

    public List<Object> getThenValue() {
        return thenValue;
    }

    public void setThenValue(List<Object> thenValue) {
        this.thenValue = thenValue;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (when.size() == 0 || thenValue.size() == 0 || elseValue == null) {
            throw new SelfCheckException("when,thenValue,elseValue can not be null in CaseWhen");
        }
        if (when.size() != thenValue.size()) {
            throw new SelfCheckException("when,thenValue must be same size in CaseWhen");
        }
    }
}
