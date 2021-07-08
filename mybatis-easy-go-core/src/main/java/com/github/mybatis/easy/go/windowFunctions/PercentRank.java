package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 分组内当前行的RANK值-1/分组内总行数-1
 */
@_Over
public class PercentRank extends Function {
    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
