package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 取分组内排序后，截止到当前行，第一个值
 */
@_Over
public class FirstValue extends Function {

    Field field;

    public FirstValue(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
