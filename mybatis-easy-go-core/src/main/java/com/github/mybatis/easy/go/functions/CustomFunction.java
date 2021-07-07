package com.github.mybatis.easy.go.functions;

import com.github.mybatis.easy.go.Function;
import com.github.mybatis.easy.go.exception.SelfCheckException;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * 自定义函数
 *
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/29 18:12
 */
public class CustomFunction extends Function {

    String functionName;

    List<Object> parameters = new ArrayList<>();

    public CustomFunction() {
    }

    public CustomFunction(String functionName) {
        this.functionName = functionName;
    }

    public CustomFunction(String functionName, Object... parameters) {
        this.functionName = functionName;
        if (parameters.length == 1 && parameters[0] instanceof Collection) {
            this.parameters.addAll((Collection<?>) parameters[0]);
        } else {
            this.parameters = new ArrayList<>(Arrays.asList(parameters));
        }
    }

    public String getFunctionName() {
        return functionName;
    }

    public CustomFunction setFunctionName(String functionName) {
        this.functionName = functionName;
        return this;
    }

    public List<Object> getParameters() {
        return parameters;
    }

    public CustomFunction setParameters(List<Object> parameters) {
        this.parameters = parameters;
        return this;
    }

    @Override
    public void selfCheck() throws SelfCheckException {
        if (StringUtils.isBlank(functionName)) {
            throw new SelfCheckException("functionName can not be null in function CustomFunction");
        }
    }
}
