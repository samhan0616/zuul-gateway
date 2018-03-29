package com.ebiz.main.rateLimit;

/**
 * Created by Administrator on 2018/3/9.
 */
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.ZParams;

import java.util.List;
import java.util.UUID;

/**
 * redis限流
 */
public class RedisRateLimiter {


    public static String acquireTokenFromBucket(Rate rate, Jedis jedis, int limit, long timeout) {
        //传入秒
        timeout = timeout * 1000;
        String identifier = UUID.randomUUID().toString();
        long now = System.currentTimeMillis();
        Transaction transaction = jedis.multi();

        //删除信号量
        transaction.zremrangeByScore(rate.getIp_bucket_MONITOR().getBytes(), "-inf".getBytes(), String.valueOf(now - timeout).getBytes());
        ZParams params = new ZParams();
        params.weightsByDouble(1.0,0.0);
        transaction.zinterstore(rate.getIp_bucket(), params, rate.getIp_bucket(), rate.getIp_bucket_MONITOR());

        //计数器自增
        transaction.incr(rate.getIp_bucket_COUNT());
        List<Object> results = transaction.exec();
        long counter = (Long) results.get(results.size() - 1);

        transaction = jedis.multi();
        transaction.zadd(rate.getIp_bucket_MONITOR(), now, identifier);
        transaction.zadd(rate.getIp_bucket(), counter, identifier);
        transaction.zrank(rate.getIp_bucket(), identifier);
        results = transaction.exec();
        //获取排名，判断请求是否取得了信号量
        long rank = (Long) results.get(results.size() - 1);
        try{
            if (rank < limit) {
                return identifier;
            } else {//没有获取到信号量，清理之前放入redis 中垃圾数据
                transaction = jedis.multi();
                transaction.zrem(rate.getIp_bucket_MONITOR(), identifier);
                transaction.zrem(rate.getIp_bucket(), identifier);
                transaction.exec();
            }
        }catch (Exception e) {
        } finally {
            jedis.close();
        }
        return null;
    }
}