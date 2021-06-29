package com.github.mybatis.easy.go.meta;

import com.github.mybatis.easy.go.Meta;
import com.github.mybatis.easy.go.exception.SelfCheckException;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 10:48
 */
public class Order extends Meta {

    Field field;
    boolean desc;


    public Order() {
    }

    public Order(Field field, boolean desc) {
        this.field = field;
        this.desc = desc;
    }

    public Order(Field field, ORDER desc) {
        this.field = field;
        this.desc = desc == ORDER.DESC;
    }

    public Field getField() {
        return field;
    }

    public Order setField(Field field) {
        this.field = field;
        return this;
    }

    public boolean isDesc() {
        return desc;
    }

    public Order setDesc(boolean desc) {
        this.desc = desc;
        return this;
    }

    public Order desc() {
        this.desc = true;
        return this;
    }

    public void selfCheck() throws SelfCheckException {
        if (field == null) {
            throw new SelfCheckException("field can not be null in order");
        }
    }

    public enum ORDER {
        ASC, DESC
    }
}
