package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.methodAnnotation._Over;
import com.github.mybatis.easy.go.step.Mysql5StepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.UnSupport;

/**
 * 用于统计窗口内往上第n行值
 */
@_Over
@UnSupport(unSupportGenerator = {Mysql5StepGenerator.class})
public class Lag extends Function {

    Field field;

    int rowCount;

    public Lag(Field field, int rowCount) {
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
