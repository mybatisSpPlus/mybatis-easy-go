package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.exception.SelfCheckException;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 11:00
 */
public class Limit  extends Action {
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
        if (limit<0||offset<0){
            throw new SelfCheckException("limit and offset can not litter than 0 in action Limit");
        }
    }
}
