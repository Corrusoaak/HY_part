package com.heye.crm.common.utils;

/**
 * Created by lishuming on 2017/9/6.
 */
public enum ResultCode {
    SUCCESS(200),
    FAIL(400),
    UNSUPPORTED(500),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
