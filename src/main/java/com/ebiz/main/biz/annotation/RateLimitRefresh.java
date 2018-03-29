package com.ebiz.main.biz.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2018/3/10.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RateLimitRefresh {

}

