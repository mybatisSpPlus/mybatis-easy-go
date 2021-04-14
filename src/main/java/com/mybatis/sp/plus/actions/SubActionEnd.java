package com.mybatis.sp.plus.actions;

import com.mybatis.sp.plus.Action;
import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Alias;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/13 14:06
 */
public class SubActionEnd extends Action {

    private Alias alias;

    public SubActionEnd(Alias alias) {
        this.alias = alias;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (alias==null){
            throw new SelfCheckException("sub query alias can not be null");
        }else {
            alias.selfCheck();
        }
    }
}
