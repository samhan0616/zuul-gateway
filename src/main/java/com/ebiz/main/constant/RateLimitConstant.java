package com.ebiz.main.constant;

import com.ebiz.main.biz.entity.RateLimitVO;
import com.ebiz.main.biz.service.RateLimitService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/12.
 */
@Component
public class RateLimitConstant {

    public static Map<String, RateLimitVO> pathMap = new HashedMap();
    private static RateLimitConstant rateLimitConstant;
    @Autowired
    private RateLimitService rateLimitService;

    public void setRateLimitService(RateLimitService rateLimitService) {
        this.rateLimitService = rateLimitService;
    }

    @PostConstruct
    public void init() {
        rateLimitConstant = this;
        rateLimitService.refreshMap();
    }
}