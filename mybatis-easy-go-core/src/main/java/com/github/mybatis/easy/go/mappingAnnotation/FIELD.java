package com.github.mybatis.easy.go.mappingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 当使用默认的映射方法时，如果字段上加上了这个注解
 * 则映射以这个注解的内容为准
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FIELD {
    /**
     * 对应的字段名
     *
     * @return
     */
    String fieldName();
}
