package com.github.mybatis.easy.go.sourceAnnotation;

public @interface FunctionSource {
    /**
     * 引入此方法需要添加的依赖对象的全路径，多个逗号分割
     */
    Class[] requiredClass();

    Class targetAnnotation();
}
