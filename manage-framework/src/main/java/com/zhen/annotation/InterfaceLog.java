package com.zhen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *   接口信息日志
 * @author 甄子函
 * @date: 2022/9/22__21:28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface InterfaceLog {

    /**
     * 接口类型
     * @return
     */
    String InterfaceType() default "";
}
