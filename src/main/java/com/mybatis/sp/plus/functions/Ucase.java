package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/13 9:29
 */
public class Ucase  extends Function {

    Field field;

    public Ucase() {
    }

    public Ucase(Field field) {
        this.field = field;
    }

    public Field getField() {
        return field;
    }

    public Ucase setField(Field field) {
        this.field = field;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null){
            throw new SelfCheckException("field can not be null in function Ucase");
        }

    }
}
