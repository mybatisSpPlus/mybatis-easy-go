package com.mybatis.sp.plus.functions;

import com.mybatis.sp.plus.Function;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/13 9:28
 */
public class Instr extends Function {

    Field field;

    Object target;

    int start;

    int times;


    public Instr(Field field, Object target) {
        this.field = field;
        this.target = target;
    }

    public Instr(Field field, Object target, int start, int times) {
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

    public int getStart() {
        return start;
    }

    public Instr setStart(int start) {
        this.start = start;
        return this;
    }

    public int getTimes() {
        return times;
    }

    public Instr setTimes(int times) {
        this.times = times;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (field==null||target==null){
            throw new SelfCheckException("field and target can not be null in function Instr");
        }
    }
}
