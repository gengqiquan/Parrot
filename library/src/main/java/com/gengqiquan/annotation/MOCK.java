package com.gengqiquan.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by gengqiquan on 2017/9/27.
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface MOCK {
    String value() ;
}