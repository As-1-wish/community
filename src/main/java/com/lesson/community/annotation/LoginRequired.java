package com.lesson.community.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //运行在方法上
@Retention(RetentionPolicy.RUNTIME) // 运行时起作用
public @interface LoginRequired {
}
