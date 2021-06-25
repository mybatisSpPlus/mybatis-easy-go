package com.github.mybatis.easy.go.actions;

import com.github.mybatis.easy.go.Action;
import com.github.mybatis.easy.go.exception.SelfCheckException;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/8 11:00
 */
public class Limit extends Action {
    int limit;
    int offset;

    public Limit() {
    }

    public Limit(int limit) {
        this.limit = limit;
    }

    public Limit(int limit, int offset) {
        this.limit = limit;
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public Limit setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public Limit setOffset(int offset) {
        this.offset = offset;
        return this;
    }


    @Override
    public void selfCheck() throws SelfCheckException {
        if (limit < 0 || offset < 0) {
            throw new SelfCheckException("limit and offset can not litter than 0 in action Limit");
        }
    }
}
