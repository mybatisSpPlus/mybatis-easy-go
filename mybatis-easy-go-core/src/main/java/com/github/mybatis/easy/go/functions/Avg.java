package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 求平均
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 21:40
 */
@_Over
public class Avg extends Function {

    Field field;

    public Avg() {
    }

    public Avg(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public Avg setField(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null) {
            throw new SelfCheckException("field can not be null in function Avg");
        }

    }
}
