package com.ebiz.main.biz.service;

import com.ebiz.java.util.common.DateUtil;
import com.ebiz.java.util.common.GsonUtil;
import com.ebiz.java.util.common.StringUtil;
import com.ebiz.java.util.http.RequestUtils;
import com.ebiz.java.util.jedis.JedisUtil;
import com.ebiz.main.biz.entity.RateLimitVO;
import com.ebiz.main.biz.entity.StatisticConditionVO;
import com.ebiz.main.biz.entity.StatisticVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * Created by Administrator on 2018/3/12.
 */
@Service
public class StatisticService {

    @Autowired
    private JedisUtil jedisUtil;

    public void record(String status, HttpServletRequest request, Map.Entry entry, String reason) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd:HH");
        PathMatcher pathMatcher = new AntPathMatcher();

        String uri = request.getRequestURI();
        String ip = RequestUtils.getClientIp(request);
        String token = request.getHeader("token");

        JedisUtil.Hash hash = jedisUtil.new Hash();
        StringBuffer stringBuffer = new StringBuffer(status);
        StatisticVO statisticVO = new StatisticVO();
        statisticVO.setPath(String.valueOf(entry.getKey()));
        statisticVO.setHistory(uri);
        statisticVO.setVisitTime(System.currentTimeMillis());
        statisticVO.setOrigin(ip);
        statisticVO.setToken(token);
        statisticVO.setReason(reason);

        hash.hset(status, new StringBuffer(sdf.format(System.currentTimeMillis())).append("-").append(entry.getKey())
                        .append("-").append(System.currentTimeMillis()).toString()
                , GsonUtil.toJson(statisticVO));
    }


    public Map query(StatisticConditionVO statisticConditionVO) {
        String date = statisticConditionVO.getDate();
        String path = statisticConditionVO.getPath();
        String status = statisticConditionVO.getStatus();
        String key = "";

        if (!StringUtil.isEmpty(path) || !StringUtil.isEmpty(date)) {
            key = (StringUtil.isEmpty(date) ? "*-" : (date + "*-")) +
                    (StringUtil.isEmpty(path) ? "*" : null) ;
        }
        return null;
    }
}