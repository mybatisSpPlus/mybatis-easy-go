package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * 转为小写
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:29
 */
public class Lcase extends Function {

    Field field;

    public Lcase() {
    }

    public Lcase(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public Lcase setField(Field field) {
        this.field = field;
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null) {
            throw new SelfCheckException("field can not be null in function Lcase");
        }

    }
}
