package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/13 9:28
 */
public class Substr extends Function {

    Field field;

    int start;

    int length;

    public Substr() {
    }

    public Substr(Field field, int start) {
        this.field = field;
        this.start = start;
    }

    public Substr(Field field, int start, int length) {
        this.field = field;
        this.start = start;
        this.length = length;
    }

    public Field getField() {
        return field;
    }

    public Substr setField(Field field) {
        this.field = field;
        return this;
    }

    public int getStart() {
        return start;
    }

    public Substr setStart(int start) {
        this.start = start;
        return this;
    }

    public int getLength() {
        return length;
    }

    public Substr setLength(int length) {
        this.length = length;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null){
            throw new SelfCheckException("field can not be null in function Substr");
        }
    }
}
