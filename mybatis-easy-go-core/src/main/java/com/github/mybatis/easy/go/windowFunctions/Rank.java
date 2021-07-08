package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.methodAnnotation._Over;

/**
 * 数据项在分组中的排名，排名相等会在名次中留下空位
 */
@_Over
public class Rank extends Function {


    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
