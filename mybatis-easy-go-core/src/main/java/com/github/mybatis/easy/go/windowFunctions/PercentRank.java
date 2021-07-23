package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation._Over;
import com.github.mybatis.easy.go.step.Mysql5StepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.UnSupport;

/**
 * 分组内当前行的RANK值-1/分组内总行数-1
 */
@_Over
@UnSupport(unSupportGenerator = {Mysql5StepGenerator.class})
public class PercentRank extends Function {
    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
