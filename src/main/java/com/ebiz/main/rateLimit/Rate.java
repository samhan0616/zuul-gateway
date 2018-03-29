package com.ebiz.main.rateLimit;

/**
 * Created by Administrator on 2018/3/9.
 */
public class Rate {

    private String ip_bucket;
    private String ip_bucket_COUNT;
    private String ip_bucket_MONITOR;

    public Rate(String ip_bucket) {
        this.ip_bucket = "ratelimit:"+ip_bucket;
        this.ip_bucket_COUNT = ip_bucket + "_COUNT";
        this.ip_bucket_MONITOR = ip_bucket + "_MONITOR";
    }

    public String getIp_bucket() {
        return ip_bucket;
    }

    public void setIp_bucket(String ip_bucket) {
        this.ip_bucket = ip_bucket;
    }

    public String getIp_bucket_COUNT() {
        return ip_bucket_COUNT;
    }

    public void setIp_bucket_COUNT(String ip_bucket_COUNT) {
        this.ip_bucket_COUNT = ip_bucket_COUNT;
    }

    public String getIp_bucket_MONITOR() {
        return ip_bucket_MONITOR;
    }

    public void setIp_bucket_MONITOR(String ip_bucket_MONITOR) {
        this.ip_bucket_MONITOR = ip_bucket_MONITOR;
    }
}