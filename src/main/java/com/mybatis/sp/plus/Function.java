package com.mybatis.sp.plus;

import com.mybatis.sp.plus.exception.SelfCheckException;
import com.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/12 21:39
 */
public abstract class Function extends Field {

    public abstract void selfCheck() throws SelfCheckException;

}