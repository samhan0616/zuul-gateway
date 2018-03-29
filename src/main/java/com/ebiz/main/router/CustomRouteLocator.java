package com.ebiz.main.router;

import com.ebiz.java.util.common.GsonUtil;
import com.ebiz.java.util.common.StringUtil;
import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.entity.ZuulRouteVO;
import com.ebiz.main.constant.PathConstant;
import com.ebiz.main.enums.REDIS_KEY_ENUMS;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.RefreshableRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties.ZuulRoute;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/9.
 */
public class CustomRouteLocator extends SimpleRouteLocator implements RefreshableRouteLocator {


    public final static Logger logger = LoggerFactory.getLogger(CustomRouteLocator.class);

    @Autowired
    private JedisUtil jedisUtil;


    private ZuulProperties properties;



    public CustomRouteLocator(String servletPath, ZuulProperties properties) {
        super(servletPath, properties);
        this.properties = properties;
        logger.info("servletPath:{}",servletPath);
    }

    @Override

    public void refresh() {
        doRefresh();
    }


    @Override
    protected Map<String, ZuulRoute> locateRoutes() {

        LinkedHashMap<String, ZuulRoute> routesMap = new LinkedHashMap<String, ZuulRoute>();

        //从application.properties中加载路由信息
        routesMap.putAll(super.locateRoutes());
        //从db中加载路由信息
        routesMap.putAll(locateRoutesFromRedis());
        //优化一下配置
        LinkedHashMap<String, ZuulRoute> values = new LinkedHashMap<>();

        for (Map.Entry<String, ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            // Prepend with slash if not already present.
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.hasText(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }

        return values;

    }


    private Map<String, ZuulRoute> locateRoutesFromRedis(){

        Map<String, ZuulRoute> routes = new LinkedHashMap<>();

        Map<String, String> path2Service = new HashMap<>();

        Map<String, String> path2Url = new HashMap<>();

        JedisUtil.Hash hash = jedisUtil.new Hash();

        List<ZuulRouteVO> results = GsonUtil.json2Collection(hash.hvals(REDIS_KEY_ENUMS.ROUTE.getLabel()).toString(),
                new TypeToken<List<ZuulRouteVO>>(){}.getType());

        for (ZuulRouteVO result : results) {

            if(StringUtil.isEmpty(result.getPath()) || StringUtil.isEmpty(result.getUrl()) ){
                continue;
            }
            ZuulRoute zuulRoute = new ZuulRoute();
            try {
                org.springframework.beans.BeanUtils.copyProperties(result,zuulRoute);
            } catch (Exception e) {
                logger.error("=============load zuul route info from redis with error==============",e);
            }
            routes.put(zuulRoute.getPath(),zuulRoute);
            path2Service.put(zuulRoute.getPath(), zuulRoute.getServiceId());
            path2Url.put(zuulRoute.getPath(),zuulRoute.getUrl());
        }
        PathConstant.path2Service = path2Service;
        PathConstant.path2Url = path2Url;

        return routes;
    }


}