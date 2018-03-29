package com.ebiz.main.config;

import com.ebiz.main.biz.service.StatisticService;
import com.ebiz.main.filter.AccessFilter;
import com.ebiz.main.filter.IPFilter;
import com.ebiz.main.filter.RateLimitFilter;
import com.ebiz.main.filter.StatisticFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/3/5.
 */
@Configuration
public class WebConfig {

    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }

    @Bean
    public RateLimitFilter rateLimitFilter() {
        return new RateLimitFilter();
    }

    @Bean
    public IPFilter ipFilter() {
        return new IPFilter();
    }

    @Bean
    public StatisticFilter statisticFilter() {
        return new StatisticFilter();
    }
}