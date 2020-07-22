package com.github.willspader.urlshortenerbackend.exception;

import com.github.willspader.urlshortenerbackend.domain.BusinessError;

public class BusinessException extends Error {

    private final String code;

    public BusinessException(BusinessError businessError) {
        super(businessError.getMsg());
        this.code = businessError.getCode();
    }

    public String getCode() {
        return code;
    }

}
