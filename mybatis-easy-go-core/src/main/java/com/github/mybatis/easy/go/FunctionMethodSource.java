package com.github.mybatis.easy.go;

import com.github.mybatis.easy.go.methodAnnotation._Over;
import com.github.mybatis.easy.go.sourceAnnotation.FunctionBag;
import com.github.mybatis.easy.go.sourceAnnotation.FunctionSource;
import com.github.mybatis.easy.go.windowFunctions.Over;
import com.github.mybatis.easy.go.windowFunctions.OverWindow;

/**
 * 此类中的方法会追缴到具有目标注解的类里面，
 * 但是类型类型必要和方法的第一个参数相匹配
 */
@FunctionBag
public class FunctionMethodSource {


    @FunctionSource(targetAnnotation = _Over.class, requiredClass = {Over.class})
    public static Over over(Function function) {
        Over over = new Over();
        over.setWindowFunction(function);
        return over;
    }

    @FunctionSource(targetAnnotation = _Over.class, requiredClass = {OverWindow.class})
    public static OverWindow over(Function function, String windowAlias) {
        OverWindow over = new OverWindow(windowAlias);
        over.setWindowFunction(function);
        return over;
    }
}
