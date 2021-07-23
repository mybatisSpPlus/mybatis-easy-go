package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.methodAnnotation._Over;
import com.github.mybatis.easy.go.step.Mysql5StepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.UnSupport;

/**
 * 取分组内排序后，截止到当前行，最后一个值
 */
@_Over
@UnSupport(unSupportGenerator = {Mysql5StepGenerator.class})
public class LastValue extends Function {
    Field field;

    public LastValue(Field field) {
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
