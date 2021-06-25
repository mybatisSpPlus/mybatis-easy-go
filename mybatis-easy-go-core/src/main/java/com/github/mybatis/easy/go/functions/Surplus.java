package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;

/**
 * 求余
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:29
 */
public class Surplus extends Function {
    Object valueA;

    Object valueB;

    public Surplus() {
    }

    public Surplus(Object valueA, Object valueB) {
        this.valueA = valueA;
        this.valueB = valueB;
    }

    public Object getValueA() {
        return valueA;
    }

    public Surplus setValueA(Object valueA) {
        this.valueA = valueA;
        return this;
    }

    public Object getValueB() {
        return valueB;
    }

    public Surplus setValueB(Object valueB) {
        this.valueB = valueB;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (valueA == null || valueB == null) {
            throw new SelfCheckException("valueA、valueA can not be null in function Surplus");
        }
    }
}
