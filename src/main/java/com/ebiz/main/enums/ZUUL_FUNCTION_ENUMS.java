package com.ebiz.main.enums;

/**
 * Created by Administrator on 2018/3/10.
 */
public enum ZUUL_FUNCTION_ENUMS {

    RATE_LIMIT("RATE-LIMIT"),
    IP_FILTER("IP-FILTER"),
    AUTH("AUTH"),
    STAT_SUCCESS("STAT-SUCCESS"),
    STAT_FAILURE("STAT-FAILURE"),
    WHITELIST ("WHITELIST");



    private String label;

    private ZUUL_FUNCTION_ENUMS(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }
}