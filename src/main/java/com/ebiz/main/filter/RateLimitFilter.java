package com.ebiz.main.filter;

import com.ebiz.java.util.common.StringUtil;
import com.ebiz.java.util.http.RequestUtils;
import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.entity.RateLimitVO;
import com.ebiz.main.biz.service.StatisticService;
import com.ebiz.main.constant.RateLimitConstant;
import com.ebiz.main.constant.StatusConstant;
import com.ebiz.main.enums.RATE_LIMIT_TYPE;
import com.ebiz.main.enums.ZUUL_FUNCTION_ENUMS;
import com.ebiz.main.rateLimit.Rate;
import com.ebiz.main.rateLimit.RedisRateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/9.
 */

public class RateLimitFilter extends ZuulFilter {

    @Autowired
    private JedisUtil jedisUtil;

    @Autowired
    private StatisticService statisticService;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return StatusConstant.RATE_LIMIT;
    }

    @Override
    public Object run() {
        RequestContext ctx= RequestContext.getCurrentContext();
        HttpServletRequest req=ctx.getRequest();
        HttpServletResponse res = ctx.getResponse();

        String uri = req.getRequestURI();
        String ip = RequestUtils.getClientIp(req);
        String token = req.getHeader("token");

        PathMatcher pathMatcher = new AntPathMatcher();

        for(Map.Entry<String, RateLimitVO> entry: RateLimitConstant.pathMap.entrySet()) {
            if (pathMatcher.match(entry.getKey(), uri)) {
                if (rateLimit(entry.getValue(), ip, token)) {

                    statisticService.record(ZUUL_FUNCTION_ENUMS.STAT_FAILURE.getLabel(),
                            req, entry, HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());

                    ctx.setSendZuulResponse(false);
                    ctx.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());// 返回错误码
                    ctx.setResponseBody(HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());// 返回错误内容
                    ctx.set("isSuccess", false);
                    return null;
                }
            }
        }
        ctx.set("isSuccess", true);
        return null;
    }

    private boolean rateLimit(RateLimitVO rateLimitVO, String ip, String token) {
        String type = rateLimitVO.getType();
        StringBuffer key = new StringBuffer(rateLimitVO.getPath());
        Integer limit = rateLimitVO.getLimit();
        Integer refresh = rateLimitVO.getRefresh();
        if (type.equalsIgnoreCase(RATE_LIMIT_TYPE.ORIGIN.getLabel())) {
            key.append(":").append(ip);
        } else if (type.equalsIgnoreCase(RATE_LIMIT_TYPE.USER.getLabel())) {
            key.append(":").append(token);
        }
        return StringUtil.isEmpty(RedisRateLimiter.acquireTokenFromBucket(new Rate(key.toString()), jedisUtil.getJedis(),
                limit, refresh));
    }

}