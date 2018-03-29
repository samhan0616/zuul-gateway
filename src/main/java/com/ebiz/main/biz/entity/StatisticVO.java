package com.ebiz.main.biz.entity;

/**
 * Created by Administrator on 2018/3/12.
 */
public class StatisticVO {
    private String path;
    private String origin;
    private String token;
    private long visitTime;
    private String history;
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(long visitTime) {
        this.visitTime = visitTime;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }
}