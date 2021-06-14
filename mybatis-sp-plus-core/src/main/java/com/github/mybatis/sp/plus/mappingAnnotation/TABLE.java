package com.github.mybatis.sp.plus.mappingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TABLE {
    /**
     * 所属模式
     *
     * @return
     */
    String schema() default "";

    /**
     * 对应表名
     *
     * @return
     */
    String tableName();
}
