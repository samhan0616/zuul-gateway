package com.ebiz.main.constant;

import com.ebiz.main.biz.entity.IPFilterVO;
import com.ebiz.main.biz.service.IPService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/16.
 */
public class WhiteListConstant {

    public static Map<String, List<String>> pathMap = new HashedMap();
    private static WhiteListConstant whiteListConstant;
    @Autowired
    private IPService ipService;

    public void setRateLimitService(IPService ipService) {
        this.ipService = ipService;
    }

    @PostConstruct
    public void init() {
        whiteListConstant = this;
        ipService.refreshWhiteList();
    }
}