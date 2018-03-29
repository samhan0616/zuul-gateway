package com.ebiz.main.filter;

import com.ebiz.main.biz.entity.RateLimitVO;
import com.ebiz.main.biz.service.StatisticService;
import com.ebiz.main.constant.RateLimitConstant;
import com.ebiz.main.constant.StatusConstant;
import com.ebiz.main.enums.ZUUL_FUNCTION_ENUMS;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/5.
 */
public class AccessFilter extends ZuulFilter {

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    @Autowired
    private StatisticService statisticService;


    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @Override
    public boolean shouldFilter() {
        return StatusConstant.AUTH_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI();

        PathMatcher pathMatcher = new AntPathMatcher();

        logger.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        // TODO: 2018/3/5  jwt验证
        String token = request.getHeader("token");

        if (null == token /*&& JwtTokenUtil.isTokenExpired(token)*/) {//暂时简单化测试
            for(Map.Entry<String, RateLimitVO> entry: RateLimitConstant.pathMap.entrySet()) {
                if (pathMatcher.match(entry.getKey(), uri)) {
                    statisticService.record(ZUUL_FUNCTION_ENUMS.STAT_FAILURE.getLabel(),
                            request, entry, HttpStatus.FORBIDDEN.getReasonPhrase());
                }
            }
            ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
            ctx.setResponseStatusCode(HttpStatus.FORBIDDEN.value());// 返回错误码
            ctx.setResponseBody(HttpStatus.FORBIDDEN.getReasonPhrase());// 返回错误内容
            ctx.set("isSuccess", false);
            return null;
        }
        ctx.set("isSuccess", true);
        return null;
    }
}