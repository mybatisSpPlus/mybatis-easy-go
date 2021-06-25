package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.exception.SelfCheckException;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/15 10:41
 */
public abstract class Meta {
    public abstract void selfCheck() throws SelfCheckException;
}
