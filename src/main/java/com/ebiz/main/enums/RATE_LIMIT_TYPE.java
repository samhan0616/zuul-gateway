package com.ebiz.main.enums;

/**
 * Created by Administrator on 2018/3/12.
 */
public enum RATE_LIMIT_TYPE {

    ORIGIN("ORIGIN"),
    USER("USER");


    private String label;

    private RATE_LIMIT_TYPE(String label) {
        this.label = label;
    }


    public String getLabel() {
        return label;
    }
}