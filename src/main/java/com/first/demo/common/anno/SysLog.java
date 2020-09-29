package com.first.demo.common.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:自定义注解，用于记录用户操作
 * @Company：众阳健康
 * @Author: shh
 * @Date: 2020/3/12 11:14
 * @Version 1.0
 */
@Target(ElementType.METHOD)//该注解应用到方法方法上
@Retention(RetentionPolicy.RUNTIME)//so they may be read reflectively.
public @interface SysLog {
    //用户操作
    String value() default  "";
}
