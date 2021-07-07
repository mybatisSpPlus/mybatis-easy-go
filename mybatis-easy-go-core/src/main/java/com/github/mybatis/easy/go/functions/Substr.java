package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * 截取字符串
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:28
 */
public class Substr extends Function {

    Field field;

    Object start;

    Object length;

    public Substr() {
    }

    public Substr(Field field, Object start) {
        this.field = field;
        this.start = start;
    }

    public Substr(Field field, Object start, Object length) {
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

    public Object getStart() {
        return start;
    }

    public Substr setStart(int start) {
        this.start = start;
        return this;
    }

    public Object getLength() {
        return length;
    }

    public Substr setLength(int length) {
        this.length = length;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null) {
            throw new SelfCheckException("field can not be null in function Substr");
        }
    }
}
