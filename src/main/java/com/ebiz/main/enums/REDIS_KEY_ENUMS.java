package com.ebiz.main.enums;

/**
 * Created by Administrator on 2018/3/9.
 */
public enum  REDIS_KEY_ENUMS {

    ROUTE("ZUULROUTE"),
    CONFIG("ZUULCONFIG"),
    RATE_LIMIT("RATE-LIMIT");


    private String label;

    private REDIS_KEY_ENUMS(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }
}