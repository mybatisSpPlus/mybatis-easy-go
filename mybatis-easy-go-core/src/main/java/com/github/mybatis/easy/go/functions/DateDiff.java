package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

public class DateDiff extends Function {

    private Field date1;

    private Field date2;

    public DateDiff(Field date1, Field date2) {
        this.date1 = date1;
        this.date2 = date2;
    }

    public DateDiff() {
    }

    public Field getDate1() {
        return date1;
    }

    public void setDate1(Field date1) {
        this.date1 = date1;
    }

    public Field getDate2() {
        return date2;
    }

    public void setDate2(Field date2) {
        this.date2 = date2;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
