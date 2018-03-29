package com.ebiz.main.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.RoutesRefreshedEvent;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/3/9.
 */
@Component
public class RefreshRouteService {

    private static final Logger logger = LoggerFactory.getLogger(RefreshRouteService.class);

    @Autowired
    ApplicationEventPublisher publisher;

    @Autowired
    RouteLocator routeLocator;


    public void refreshRoute() {
        RoutesRefreshedEvent routesRefreshedEvent = new RoutesRefreshedEvent(routeLocator);
        publisher.publishEvent(routesRefreshedEvent);
        logger.info("=======已成功更新路由信息=======");
    }

}