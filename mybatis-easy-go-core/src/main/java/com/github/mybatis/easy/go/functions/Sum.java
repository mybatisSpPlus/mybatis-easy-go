package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 求和
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 21:39
 */
@_Over
public class Sum extends Function {

    Field field;

    public Sum() {
    }

    public Sum(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public Sum setField(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null) {
            throw new SelfCheckException("field can not be null in function Sum");
        }

    }
}
