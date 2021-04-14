package com.mybatis.sp.plus;

import com.mybatis.sp.plus.exception.SelfCheckException;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/8 10:40
 */
public abstract class Condition {
    /**
     * 与下个条件是or的关系，默认false
     */
    boolean orWithNext;

    boolean lastOne;

    public boolean isOrWithNext() {
        return orWithNext;
    }

    public void setOrWithNext(boolean orWithNext) {
        this.orWithNext = orWithNext;
    }

    public boolean isLastOne() {
        return lastOne;
    }

    public void setLastOne(boolean lastOne) {
        this.lastOne = lastOne;
    }

    public abstract void selfCheck() throws SelfCheckException;
}
