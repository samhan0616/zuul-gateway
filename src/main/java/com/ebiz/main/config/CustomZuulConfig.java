package com.ebiz.main.config;

import com.ebiz.main.router.CustomRouteLocator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/3/9.
 */
@Configuration
public class CustomZuulConfig {

    @Autowired
    ZuulProperties zuulProperties;

    @Autowired
    ServerProperties server;

    @Bean
    public CustomRouteLocator routeLocator() {
        CustomRouteLocator routeLocator = new CustomRouteLocator(this.server.getServletPrefix(),
                this.zuulProperties);
        return routeLocator;
    }
}


