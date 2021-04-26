package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:28
 */
public class Round  extends Function {

    Field field;

    int decimals;

    public Round() {
    }

    public Round(Field field, int decimals) {
        this.field = field;
        this.decimals = decimals;
    }

    public Field getField() {
        return field;
    }

    public Round setField(Field field) {
        this.field = field;
        return this;
    }

    public int getDecimals() {
        return decimals;
    }

    public Round setDecimals(int decimals) {
        this.decimals = decimals;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null){
            throw new SelfCheckException("field can not be null in function Round");
        }
        if (decimals<0){
            throw new SelfCheckException("decimals can not be litter than 0 in function Round");
        }

    }
}
