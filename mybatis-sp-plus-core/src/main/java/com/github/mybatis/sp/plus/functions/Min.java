package com.github.mybatis.sp.plus.functions;

import com.github.mybatis.sp.plus.Function;
import com.github.mybatis.sp.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
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
