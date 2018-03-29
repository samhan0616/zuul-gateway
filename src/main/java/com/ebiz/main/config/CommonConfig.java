package com.ebiz.main.config;

import com.ebiz.java.util.jedis.JRedisPoolConfig;
import com.ebiz.java.util.jedis.JedisUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2018/3/2.
 */
@Configuration
public class CommonConfig {

    //jedis
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
    @Value("${spring.redis.password}")
    private String password;

    /**
     * 获得jedis
     * @return
     */

    @Bean(value = "jedisUtil")
    public JedisUtil getJedis() {
        JRedisPoolConfig.REDIS_IP = host;
        JRedisPoolConfig.REDIS_PASSWORD = password;
        JRedisPoolConfig.REDIS_PORT = port;
        JRedisPoolConfig.testOnBorrow = false;
        return JedisUtil.getInstance();
    }

}