package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * 四舍五入
 *
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/13 9:28
 */
public class Round extends Function {

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
        if (field == null) {
            throw new SelfCheckException("field can not be null in function Round");
        }
        if (decimals < 0) {
            throw new SelfCheckException("decimals can not be litter than 0 in function Round");
        }

    }
}
