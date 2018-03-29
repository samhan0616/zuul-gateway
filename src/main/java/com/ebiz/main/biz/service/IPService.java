package com.ebiz.main.biz.service;

import com.ebiz.java.util.common.GsonUtil;
import com.ebiz.java.util.common.UUIDUtil;
import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.entity.IPFilterVO;
import com.ebiz.main.constant.RateLimitConstant;
import com.ebiz.main.constant.WhiteListConstant;
import com.ebiz.main.enums.ZUUL_FUNCTION_ENUMS;
import com.google.common.reflect.TypeToken;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Administrator on 2018/3/12.
 */
@Service
public class IPService {


    private Logger logger = LoggerFactory.getLogger(IPService.class);

    @Autowired
    private JedisUtil jedisUtil;
    //增
    public void create(IPFilterVO ipFilterVO) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        ipFilterVO.setId(UUIDUtil.randomUUID());

        Map<String, IPFilterVO> map =  GsonUtil.fromJson(hash.hget(ZUUL_FUNCTION_ENUMS.WHITELIST.getLabel(),ipFilterVO.getPath()),
                new TypeToken<Map<String, IPFilterVO>>(){}.getType());
        if (MapUtils.isEmpty(map)) {
            map = new HashMap<>();
        }
        map.put(ipFilterVO.getId(), ipFilterVO);
        hash.hset(ZUUL_FUNCTION_ENUMS.WHITELIST.getLabel(),
                ipFilterVO.getPath(), GsonUtil.toJson(map));
    }

    //删
    public void delete(IPFilterVO ipFilterVO) {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        ipFilterVO.setId(UUIDUtil.randomUUID());

        Map<String, IPFilterVO> map =  GsonUtil.fromJson(hash.hget(ZUUL_FUNCTION_ENUMS.WHITELIST.getLabel(),ipFilterVO.getPath()),
                new TypeToken<Map<String, IPFilterVO>>(){}.getType());
        map.remove(ipFilterVO.getId());
        hash.hset(ZUUL_FUNCTION_ENUMS.WHITELIST.getLabel(),
                ipFilterVO.getPath(), GsonUtil.toJson(map));
    }

    //查
    public Map<String, Collection<IPFilterVO>> query() {
        return getWhiteList();
    }

    private Map<String, Collection<IPFilterVO>> getWhiteList() {
        JedisUtil.Hash hash = jedisUtil.new Hash();
        Set<String> keys = hash.hkeys(ZUUL_FUNCTION_ENUMS.WHITELIST.getLabel());
        Map<String, Collection<IPFilterVO>> map = new HashMap<>();
        for (String key : keys) {
            Map<String, IPFilterVO> keyMap =  GsonUtil.fromJson(hash.hget(ZUUL_FUNCTION_ENUMS.WHITELIST.getLabel(), key),
                    new TypeToken<Map<String, IPFilterVO>>(){}.getType());
            map.put(key, keyMap.values());
        }
        return map;
    }

    public Map<String, List<String>> refreshWhiteList() {
        Map<String, Collection<IPFilterVO>> map = getWhiteList();
        Map<String, List<String>> result = new HashMap<String, List<String>>();
        for (String key : map.keySet()) {
            Collection<IPFilterVO> ipFilterVOs = map.get(key);
            List<String> ips = new ArrayList<>();
            for (IPFilterVO ipFilterVO : ipFilterVOs) {
                if (ipFilterVO.isEnable()) {
                    ips.add(ipFilterVO.getIp());
                }
            }
            result.put(key, ips);
        }
        synchronized (WhiteListConstant.pathMap) {
            WhiteListConstant.pathMap = result;
        }
        logger.info("=======已成功更新白名单=======");
        logger.info(WhiteListConstant.pathMap.toString());
        return result;
    }
}