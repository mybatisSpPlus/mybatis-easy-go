package com.github.mybatis.sp.plus;

import com.github.mybatis.sp.plus.exception.SelfCheckException;
import com.github.mybatis.sp.plus.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 21:39
 */
public abstract class Function extends Field {

    public abstract void selfCheck() throws SelfCheckException;

}