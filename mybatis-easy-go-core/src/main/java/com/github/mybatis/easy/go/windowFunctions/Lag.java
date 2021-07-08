package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 用于统计窗口内往上第n行值
 */
@_Over
public class Lag extends Function {
    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
