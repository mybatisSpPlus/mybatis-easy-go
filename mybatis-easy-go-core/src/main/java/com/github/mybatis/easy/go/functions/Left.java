package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * 从左侧截取字符串
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:28
 */
public class Left extends Function {

    Field field;

    int length;

    public Left() {
    }

    public Left(Field field, int length) {
        this.field = field;
        this.length = length;
    }

    public Field getField() {
        return field;
    }

    public Left setField(Field field) {
        this.field = field;
        return this;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null) {
            throw new SelfCheckException("field can not be null in function Left");
        }
    }
}
