package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * 查找字符串
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:28
 */
public class Instr extends Function {

    Field field;

    Object target;

    Object start;

    Object times;


    public Instr(Field field, Object target) {
        this.field = field;
        this.target = target;
    }

    public Instr(Field field, Object target, Object start, Object times) {
        this.field = field;
        this.target = target;
        this.start = start;
        this.times = times;
    }

    public Field getField() {
        return field;
    }

    public Instr setField(Field field) {
        this.field = field;
        return this;
    }

    public Object getTarget() {
        return target;
    }

    public Instr setTarget(Object target) {
        this.target = target;
        return this;
    }

    public Object getStart() {
        return start;
    }

    public Instr setStart(Object start) {
        this.start = start;
        return this;
    }

    public Object getTimes() {
        return times;
    }

    public Instr setTimes(Object times) {
        this.times = times;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field == null || target == null) {
            throw new SelfCheckException("field and target can not be null in function Instr");
        }
    }
}
