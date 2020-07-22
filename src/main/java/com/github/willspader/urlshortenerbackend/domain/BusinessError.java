package com.github.willspader.urlshortenerbackend.domain;

public enum BusinessError {

    CUSTOM_URL_ALREADY_EXISTS("001", "Custom URL already exists");

    private final String code;
    private final String msg;

    BusinessError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
