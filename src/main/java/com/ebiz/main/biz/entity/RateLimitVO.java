package com.ebiz.main.biz.entity;

/**
 * Created by Administrator on 2018/3/9.
 */
public class RateLimitVO {

    private String id;
    //private String ip;
    //private String routeId;
    private String path;
    private boolean enable;
    private Integer limit;
    private Integer refresh;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getIp() {
//        return ip;
//    }
//
//    public void setIp(String ip) {
//        this.ip = ip;
//    }
//
//    public String getRouteId() {
//        return routeId;
//    }
//
//    public void setRouteId(String routeId) {
//        this.routeId = routeId;
//    }


    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getRefresh() {
        return refresh;
    }

    public void setRefresh(Integer refresh) {
        this.refresh = refresh;
    }
}