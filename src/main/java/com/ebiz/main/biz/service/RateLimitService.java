package com.ebiz.main.biz.service;

import com.ebiz.java.util.common.GsonUtil;
import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.entity.RateLimitVO;
import com.ebiz.main.constant.RateLimitConstant;
import com.ebiz.main.enums.REDIS_KEY_ENUMS;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Administrator on 2018/3/12.
 */
@Service
public class RateLimitService {

    private Logger logger = LoggerFactory.getLogger(RateLimitService.class);

    @Autowired
    private JedisUtil jedisUtil;

    //增
    public void create(RateLimitVO rateLimitVO) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        rateLimitVO.setId(UUID.randomUUID().toString());
        hash.hset(REDIS_KEY_ENUMS.RATE_LIMIT.getLabel(), rateLimitVO.getId(), GsonUtil.toJson(rateLimitVO));
    }

    //删
    public void delete(String id) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        hash.hdel(REDIS_KEY_ENUMS.RATE_LIMIT.getLabel(), id);
    }

    //改
    public void update(RateLimitVO rateLimitVO) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        hash.hset(REDIS_KEY_ENUMS.RATE_LIMIT.getLabel(), rateLimitVO.getId(), GsonUtil.toJson(rateLimitVO));
    }

    //查
    public List<RateLimitVO> query() {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        return GsonUtil.json2Collection(hash.hvals(REDIS_KEY_ENUMS.RATE_LIMIT.getLabel()).toString(),
                new TypeToken<List<RateLimitVO>>(){}.getType());
    }
    //根据serviceId查
    public RateLimitVO query(String id) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        return GsonUtil.fromJson(hash.hget(REDIS_KEY_ENUMS.RATE_LIMIT.getLabel(),id), RateLimitVO.class);
    }

    public void refreshMap() {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        List<RateLimitVO> rateLimitVOs = GsonUtil.json2Collection(hash.hvals(REDIS_KEY_ENUMS.RATE_LIMIT.getLabel()).toString(),
                new TypeToken<List<RateLimitVO>>(){}.getType());
        Map<String, RateLimitVO> pathMap = new HashMap<>();
        for (RateLimitVO rateLimitVO : rateLimitVOs) {
            pathMap.put(rateLimitVO.getPath(), rateLimitVO);
        }
        synchronized (RateLimitConstant.pathMap) {
            RateLimitConstant.pathMap = pathMap;
        }
        logger.info("=======已成功更新限流属性=======");
        logger.info(RateLimitConstant.pathMap.toString());
    }
}