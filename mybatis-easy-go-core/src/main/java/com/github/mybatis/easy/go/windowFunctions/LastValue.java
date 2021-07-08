package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 取分组内排序后，截止到当前行，最后一个值
 */
@_Over
public class LastValue extends Function {
    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
