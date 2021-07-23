package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;

import java.util.ArrayList;
import java.util.List;

/**
 * 条件分支
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/19 17:37
 */
public class Case extends Function {
    Object caseCondition;
    List<Object> when = new ArrayList<>();
    List<Object> thenValue = new ArrayList<>();
    Object elseValue;

    public Case() {
    }

    public Case(Object caseCondition) {
        this.caseCondition = caseCondition;
    }

    public Case when(Object when) {
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

    public List<Object> getWhen() {
        return when;
    }

    public void setWhen(List<Object> when) {
        this.when = when;
    }

    public List<Object> getThenValue() {
        return thenValue;
    }

    public void setThenValue(List<Object> thenValue) {
        this.thenValue = thenValue;
    }

    public Object getCaseCondition() {
        return caseCondition;
    }

    public void setCaseCondition(Object caseCondition) {
        this.caseCondition = caseCondition;
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
