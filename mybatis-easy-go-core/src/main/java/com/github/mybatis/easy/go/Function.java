package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.meta.Field;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/12 21:39
 */
public abstract class Function extends Field {

    public abstract void selfCheck() throws SelfCheckException;

}
