package com.github.mybatis.easy.go.windowFunctions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import com.github.mybatis.easy.go.step.Mysql5StepGenerator;
import com.github.mybatis.easy.go.supportAnnotation.UnSupport;

/**
 * 与window对象组合使用，传入window的别名
 */
@UnSupport(unSupportGenerator = {Mysql5StepGenerator.class})
public class OverWindow extends Function {

    Function windowFunction;

    String windowAlias;

    public OverWindow(String windowAlias) {
        this.windowAlias = windowAlias;
    }

    public String getWindowAlias() {
        return windowAlias;
    }

    public void setWindowAlias(String windowAlias) {
        this.windowAlias = windowAlias;
    }

    public Function getWindowFunction() {
        return windowFunction;
    }

    public void setWindowFunction(Function windowFunction) {
        this.windowFunction = windowFunction;
    }

    @Override
    public void selfCheck() throws SelfCheckException {

    }
}
