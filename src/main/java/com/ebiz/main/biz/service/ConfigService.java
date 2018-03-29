package com.ebiz.main.biz.service;

import com.ebiz.java.util.common.GsonUtil;
import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.entity.ZuulConfigVO;
import com.ebiz.main.enums.REDIS_KEY_ENUMS;
import com.google.common.reflect.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2018/3/10.
 */
@Service
public class ConfigService {

    private Logger logger = LoggerFactory.getLogger(ConfigService.class);

    @Autowired
    private JedisUtil jedisUtil;

    //增
    public void create(ZuulConfigVO zuulConfigVO) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        hash.hset(REDIS_KEY_ENUMS.CONFIG.getLabel(), zuulConfigVO.getName(), GsonUtil.toJson(zuulConfigVO));
    }
    //改
    public void update(ZuulConfigVO zuulConfigVO) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        hash.hset(REDIS_KEY_ENUMS.CONFIG.getLabel(), zuulConfigVO.getName(), GsonUtil.toJson(zuulConfigVO));
    }
    //查
    public List<ZuulConfigVO> query() {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        return GsonUtil.json2Collection(hash.hvals(REDIS_KEY_ENUMS.CONFIG.getLabel()).toString(),
                new TypeToken<List<ZuulConfigVO>>(){}.getType());
    }
    //按名字查
    public ZuulConfigVO queryByName(String name) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        return GsonUtil.fromJson(hash.hget(REDIS_KEY_ENUMS.CONFIG.getLabel(),name), ZuulConfigVO.class);
    }

    //按名字查状态
    public boolean queryStatus(String name) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        return GsonUtil.fromJson(hash.hget(REDIS_KEY_ENUMS.CONFIG.getLabel(),name), ZuulConfigVO.class).isStatus();
    }

}