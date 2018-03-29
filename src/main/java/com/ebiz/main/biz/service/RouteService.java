package com.ebiz.main.biz.service;

import com.ebiz.java.util.common.GsonUtil;
import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.entity.ZuulRouteVO;
import com.ebiz.main.enums.REDIS_KEY_ENUMS;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Created by Administrator on 2018/3/9.
 */
@Service
public class RouteService {

    private Logger logger = LoggerFactory.getLogger(RouteService.class);

    @Autowired
    private JedisUtil jedisUtil;


    //增
    public void create(ZuulRouteVO zuulRouteVO) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        zuulRouteVO.setId(UUID.randomUUID().toString());
        hash.hset(REDIS_KEY_ENUMS.ROUTE.getLabel(), zuulRouteVO.getId(), GsonUtil.toJson(zuulRouteVO));
    }

    //删
    public void delete(String id) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        hash.hdel(REDIS_KEY_ENUMS.ROUTE.getLabel(), id);
    }

    //改
    public void update(ZuulRouteVO zuulRouteVO) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        hash.hset(REDIS_KEY_ENUMS.ROUTE.getLabel(), zuulRouteVO.getId(), GsonUtil.toJson(zuulRouteVO));
    }

    //查
    public List<ZuulRouteVO> query() {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        return GsonUtil.json2Collection(hash.hvals(REDIS_KEY_ENUMS.ROUTE.getLabel()).toString(),
                new TypeToken<List<ZuulRouteVO>>(){}.getType());
    }
    //根据serviceId查
    public ZuulRouteVO query(String id) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        return GsonUtil.fromJson(hash.hget(REDIS_KEY_ENUMS.ROUTE.getLabel(),id), ZuulRouteVO.class);
    }
}