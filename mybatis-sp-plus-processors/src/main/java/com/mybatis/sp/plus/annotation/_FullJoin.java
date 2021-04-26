package com.mybatis.sp.plus.annotation;

import com.mybatis.sp.plus.functionAnnotation._FullJoin_Source;

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
public @interface _FullJoin {
    Class source() default _FullJoin_Source.class;
}
