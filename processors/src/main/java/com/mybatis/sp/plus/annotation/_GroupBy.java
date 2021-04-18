package com.mybatis.sp.plus.annotation;

import com.mybatis.sp.plus.functionAnnotation._GroupBy_Source;

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
public @interface _GroupBy {
    Class source() default _GroupBy_Source.class;
}
