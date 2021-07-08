package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 返回窗口中第n个expr的值。expr可以是表达式，也可以是列名
 */
@_Over
public class NthValue extends Function {
    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
