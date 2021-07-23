package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 用于统计窗口内往下第n行值
 */
@_Over
public class Lead extends Function {

    Field field;

    int rowCount;

    public Lead(Field field, int rowCount) {
        this.field = field;
        this.rowCount = rowCount;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
