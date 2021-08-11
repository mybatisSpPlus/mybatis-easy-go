package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

public class Year extends Function {

    private Field data;

    public Year(Field data) {
        this.data = data;
    }

    public Field getData() {
        return data;
    }

    public void setData(Field data) {
        this.data = data;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
