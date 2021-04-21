package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
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
