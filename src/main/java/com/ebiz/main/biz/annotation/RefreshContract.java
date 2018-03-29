package com.ebiz.main.biz.annotation;

import com.ebiz.main.biz.service.IPService;
import com.ebiz.main.biz.service.RateLimitService;
import com.ebiz.main.constant.StatusConstant;
import com.ebiz.main.filter.IPFilter;
import com.ebiz.main.router.RefreshRouteService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/3/10.
 */
@Aspect
@Component
public class RefreshContract {

    @Autowired
    private RefreshRouteService refreshRouteService;

    @Autowired
    private RateLimitService rateLimitService;

    @Autowired
    private StatusConstant statusConstant;

    @Autowired
    private IPService ipService;

    @Pointcut("@annotation(RouteRefresh)")
    public void RouteRefreshPointCut() {
    }

    @Pointcut("@annotation(RateLimitRefresh)")
    public void RateLimitRefreshPointCut() {
    }

    @Pointcut("@annotation(ConfigRefresh)")
    public void ConfigRefreshPointCut() {
    }

    @Pointcut("@annotation(WhilteListRefresh)")
    public void WhilteListRefreshPointCut() {
    }

    @Around(value = "RouteRefreshPointCut()")
    public Object  RouteRefresh(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        refreshRouteService.refreshRoute();
        return result;
    }

    @Around(value = "RateLimitRefreshPointCut()")
    public Object RateLimitRefresh(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        rateLimitService.refreshMap();
        return result;
    }

    @Around(value = "ConfigRefreshPointCut()")
    public Object ConfigRefresh(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        statusConstant.refresh();
        return result;
    }

    @Around(value = "WhilteListRefreshPointCut()")
    public Object WhilteListRefresh(final ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        ipService.refreshWhiteList();
        return result;
    }
}