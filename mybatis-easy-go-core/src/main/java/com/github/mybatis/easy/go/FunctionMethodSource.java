package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.methodAnnotation._Over;
import com.github.mybatis.easy.go.sourceAnnotation.FunctionBag;
import com.github.mybatis.easy.go.sourceAnnotation.FunctionSource;
import com.github.mybatis.easy.go.windowFunctions.Over;

@FunctionBag
public class FunctionMethodSource {


    @FunctionSource(targetAnnotation = _Over.class, requiredClass = {Over.class})
    public static Over over(Function function) {
        Over over = new Over();
        over.setWindowFunction(function);
        return over;
    }
}
