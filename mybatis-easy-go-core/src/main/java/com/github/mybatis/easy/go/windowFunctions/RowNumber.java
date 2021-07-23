package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation._Over;
import com.github.mybatis.easy.go.step.Mysql5StepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.UnSupport;

/**
 * 数据项在分组中的行号
 */
@_Over
@UnSupport(unSupportGenerator = {Mysql5StepGenerator.class}
)public class RowNumber extends Function {
    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
