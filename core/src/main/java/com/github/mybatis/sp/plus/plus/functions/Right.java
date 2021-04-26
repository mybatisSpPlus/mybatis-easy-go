package com.github.mybatis.sp.plus.plus.functions;

import com.github.mybatis.sp.plus.plus.Function;
import com.github.mybatis.sp.plus.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:28
 */
public class Right extends Function {

    Field field;

    int length;

    public Right() {
    }

    public Right(Field field, int length) {
        this.field = field;
        this.length = length;
    }

    public Field getField() {
        return field;
    }

    public Right setField(Field field) {
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
            throw new SelfCheckException("field can not be null in function Right");
        }
    }
}
