package com.github.mybatis.sp.plus.annotation;

import com.github.mybatis.sp.plus.functionAnnotation._Where_Source;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouyu74748585@hotmail.com
 * @date 2021/4/16 20:14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface _Where {
    Class source() default _Where_Source.class;
}