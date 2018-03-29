package com.ebiz.main.filter;

import com.ebiz.main.biz.entity.RateLimitVO;
import com.ebiz.main.biz.service.StatisticService;
import com.ebiz.main.constant.RateLimitConstant;
import com.ebiz.main.enums.ZUUL_FUNCTION_ENUMS;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.core.Ordered;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/12.
 */
public class StatisticFilter extends ZuulFilter {

    @Autowired
    private StatisticService statisticService;

    @Override
    public String filterType() {
        return FilterConstants.ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx= RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI();


        PathMatcher pathMatcher = new AntPathMatcher();
        for(Map.Entry<String, RateLimitVO> entry: RateLimitConstant.pathMap.entrySet()) {
            if (pathMatcher.match(entry.getKey(), uri) && (boolean)ctx.get("isSuccess")) {
                statisticService.record(ZUUL_FUNCTION_ENUMS.STAT_SUCCESS.getLabel(),
                        request, entry, null);
            }
        }

        return null;
    }

}