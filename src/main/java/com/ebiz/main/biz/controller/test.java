package com.ebiz.main.biz.controller;

import com.ebiz.java.util.common.GsonUtil;
import com.ebiz.java.util.common.MapUtil;
import com.ebiz.java.util.http.RequestUtils;
import com.ebiz.java.util.jedis.JRedisPoolConfig;
import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.entity.IPFilterVO;
import com.ebiz.main.biz.entity.ZuulConfigVO;
import com.ebiz.main.enums.REDIS_KEY_ENUMS;
import com.ebiz.main.enums.ZUUL_FUNCTION_ENUMS;
import com.ebiz.main.filter.IPFilter;
import com.ebiz.main.rateLimit.Rate;
import com.ebiz.main.rateLimit.RedisRateLimiter;
import com.google.common.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/2.
 */
//@RestController
public class test {

    @Autowired
    private JedisUtil jedisUtil;

    @GetMapping("/test")
    public String test(HttpServletRequest request) {
        String key = request.getRequestURI();
        String host = RequestUtils.getClientIp(request);
        String token = RedisRateLimiter.acquireTokenFromBucket(
                new Rate(key+host), jedisUtil.getPool().getResource(), 1, 60000);

        return token;
    }

    @GetMapping("/test2")
    public String test2(HttpServletRequest request) {
        String key = request.getRequestURI();
        String host = RequestUtils.getClientIp(request);
        String token = RedisRateLimiter.acquireTokenFromBucket(new Rate(key+host),jedisUtil.getPool().getResource(), 1, 60000);
        return token;
    }


//    public static void main(String[] args) {
//        JRedisPoolConfig.REDIS_IP = "10.10.10.148";
//        JRedisPoolConfig.REDIS_PASSWORD = "redis";
//        JRedisPoolConfig.REDIS_PORT = 6379;
//        JRedisPoolConfig.testOnBorrow = false;
//        JedisUtil jedisUtil =  JedisUtil.getInstance();
//        JedisUtil.Hash hash = jedisUtil.new Hash();
//
////        ZuulConfigVO zuulConfigVO = new ZuulConfigVO();
////        zuulConfigVO.setName(ZUUL_FUNCTION_ENUMS.RATE_LIMIT.getLabel());
////        zuulConfigVO.setStatus(false);
////        hash.hset(REDIS_KEY_ENUMS.CONFIG.getLabel(), ZUUL_FUNCTION_ENUMS.RATE_LIMIT.getLabel()
////                , GsonUtil.toJson(zuulConfigVO));
////
////        zuulConfigVO.setName(ZUUL_FUNCTION_ENUMS.IP_FILTER.getLabel());
////        zuulConfigVO.setStatus(false);
////        hash.hset(REDIS_KEY_ENUMS.CONFIG.getLabel(), ZUUL_FUNCTION_ENUMS.IP_FILTER.getLabel()
////                , GsonUtil.toJson(zuulConfigVO));
////
////        zuulConfigVO.setName(ZUUL_FUNCTION_ENUMS.AUTH.getLabel());
////        zuulConfigVO.setStatus(false);
////        hash.hset(REDIS_KEY_ENUMS.CONFIG.getLabel(), ZUUL_FUNCTION_ENUMS.AUTH.getLabel()
////                , GsonUtil.toJson(zuulConfigVO));
//
//        Map<String, IPFilterVO> map = new HashMap<>();
//        IPFilterVO ipFilter = new IPFilterVO();
//        ipFilter.setId("abc");
//        ipFilter.setPath("/abc");
//        map.put("abc", ipFilter);
//        hash.hset("abc","/abc", GsonUtil.toJson(map));
//        Map<String, IPFilterVO> object = GsonUtil.fromJson(hash.hget("abc","/abc"), new TypeToken<Map<String, IPFilterVO>>(){}.getType());
//        System.out.println(object.get("abc").getPath());
//    }
}