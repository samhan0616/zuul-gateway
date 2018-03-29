package com.ebiz.main.constant;

import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.service.ConfigService;
import com.ebiz.main.biz.service.RouteService;
import com.ebiz.main.enums.ZUUL_FUNCTION_ENUMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by Administrator on 2018/3/5.
 */
@Component
@DependsOn("jedisUtil")
public class StatusConstant {

    @Autowired
    private ConfigService configService;

    public static boolean RATE_LIMIT = false;
    public static boolean IP_FILTER = false;
    public static boolean AUTH_FILTER = false;

    @PostConstruct
    public void init() throws IOException {
        rateLimit();
        ipFilter();
        authFilter();
    }

    private void rateLimit(){
        StatusConstant.RATE_LIMIT = configService.queryStatus(ZUUL_FUNCTION_ENUMS.RATE_LIMIT.getLabel());
    }

    private void ipFilter(){
        StatusConstant.IP_FILTER = configService.queryStatus(ZUUL_FUNCTION_ENUMS.IP_FILTER.getLabel());
    }

    private void authFilter(){
        StatusConstant.AUTH_FILTER = configService.queryStatus(ZUUL_FUNCTION_ENUMS.AUTH.getLabel());
    }

    public void refresh() {
        rateLimit();
        ipFilter();
        authFilter();
    }

}