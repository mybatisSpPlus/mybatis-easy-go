package com.github.mybatis.easy.go.functionAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/16 20:14
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.SOURCE)
public @interface _Union_Source {
    /**
     * 引入此方法需要添加的依赖对象的全路径，多个逗号分割
     */
    Class[] requiredClass();
}
