package com.mybatis.sp.plus;

import com.mybatis.sp.plus.exception.SelfCheckException;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 10:41
 */
public abstract class Meta {
    public abstract void selfCheck() throws SelfCheckException;
}
