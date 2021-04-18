package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 21:40
 */
public class Min  extends Function {


    Field field;

    public Min() {
    }

    public Min(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public Min setField(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null){
            throw new SelfCheckException("field can not be null in function Min");
        }

    }
}