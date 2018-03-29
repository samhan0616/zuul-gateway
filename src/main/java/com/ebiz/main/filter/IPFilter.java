package com.ebiz.main.filter;

import com.ebiz.java.util.http.RequestUtils;
import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.entity.IPFilterVO;
import com.ebiz.main.biz.entity.RateLimitVO;
import com.ebiz.main.biz.service.StatisticService;
import com.ebiz.main.constant.RateLimitConstant;
import com.ebiz.main.constant.StatusConstant;
import com.ebiz.main.constant.WhiteListConstant;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/3/9.
 */
public class IPFilter extends ZuulFilter {

    Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    private StatisticService statisticService;

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 1;
    }

    @Override
    public boolean shouldFilter() {
        return StatusConstant.IP_FILTER;
    }

    @Override
    public Object run() {
        RequestContext ctx= RequestContext.getCurrentContext();
        HttpServletRequest req=ctx.getRequest();
        String ipAddr= RequestUtils.getClientIp(req);
        String uri = req.getRequestURI();


        //配置本地IP白名单，生产环境可放入数据库或者redis中
        Map<String, List<String>> map = WhiteListConstant.pathMap;

        PathMatcher pathMatcher = new AntPathMatcher();


        for(Map.Entry<String, List<String>> entry: map.entrySet()) {
            if (pathMatcher.match(entry.getKey(), uri) ) {
                List<String> ips = map.get(entry.getKey());
                if(!ips.contains(ipAddr)){
                    statisticService.record(ZUUL_FUNCTION_ENUMS.STAT_FAILURE.getLabel(),
                            req, entry, HttpStatus.UNAUTHORIZED.getReasonPhrase());
                    logger.info("IP地址校验不通过！！！");
                    ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                    ctx.setSendZuulResponse(false);
                    ctx.setResponseBody(HttpStatus.UNAUTHORIZED.getReasonPhrase());
                    ctx.set("isSuccess", false);
                }
            }
        }

        logger.info("IP校验通过。");
        ctx.set("isSuccess", true);
        return null;
    }


}