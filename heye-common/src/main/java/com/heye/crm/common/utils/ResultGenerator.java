package com.heye.crm.common.utils;

/**
 * Created by lishuming on 2017/9/6.
 */
public class ResultGenerator {
    private static final String DEFAULT_SUCCESS_MESSAGE = "SUCCESS";
    private static final String UNSUPPORTED = "NOT_SUPPORTED";

    public static Result genSuccessResult() {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE);
    }

    public static Result genSuccessResult(Object data) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(DEFAULT_SUCCESS_MESSAGE)
                .setData(data);
    }

    public static Result genSuccessResultByMessage(String message) {
        return new Result()
                .setCode(ResultCode.SUCCESS)
                .setMsg(message);
    }

    public static Result genFailResult(String message) {
        return new Result()
                .setCode(ResultCode.FAIL)
                .setMsg(message.toUpperCase());
    }
}
