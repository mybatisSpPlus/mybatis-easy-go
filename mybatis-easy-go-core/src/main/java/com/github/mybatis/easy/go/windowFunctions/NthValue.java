package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 返回窗口中第n个expr的值。expr可以是表达式，也可以是列名
 */
@_Over
public class NthValue extends Function {

    Field field;

    int index;

    public NthValue(Field field, int index) {
        this.field = field;
        this.index = index;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
