package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

public class AddTime extends Function {

    private Field date;

    private Object expr;

    public AddTime(Field date, Object expr) {
        this.date = date;
        this.expr = expr;
    }

    public AddTime() {
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }

    public Field getDate() {
        return date;
    }

    public void setDate(Field date) {
        this.date = date;
    }

    public Object getExpr() {
        return expr;
    }

    public void setExpr(Object expr) {
        this.expr = expr;
    }
}
