package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * 计算字符串长度
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:28
 */
public class Len extends Function {

    Field field;

    public Len() {
    }

    public Len(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public Len setField(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null) {
            throw new SelfCheckException("field can not be null in function Len");
        }

    }
}
