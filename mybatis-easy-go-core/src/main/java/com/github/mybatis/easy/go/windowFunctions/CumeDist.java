package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation._Over;
import com.github.mybatis.easy.go.step.Mysql5StepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.UnSupport;

/**
 * 小于等于当前值的行数/分组内总行数
 */
@_Over
@UnSupport(unSupportGenerator = {Mysql5StepGenerator.class})
public class CumeDist extends Function {
    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
