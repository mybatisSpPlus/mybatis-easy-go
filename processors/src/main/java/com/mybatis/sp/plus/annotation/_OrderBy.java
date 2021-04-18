package com.mybatis.sp.plus.annotation;

import com.mybatis.sp.plus.functionAnnotation._From_Source;
import com.mybatis.sp.plus.functionAnnotation._OrderBy_Source;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author zhouyu4034@sefonsoft.com
 * @date 2021/4/16 20:14
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface _OrderBy {
    Class source() default _OrderBy_Source.class;
}