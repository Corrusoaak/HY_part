package com.heye.crm.common.utils;

/**
 * @author : lishuming
 */
public class WebUtil {
    public static Result success(Object data) {
        return new Result(0, data, "success");
    }

    public static Result fail(String failedMsg) {
        return new Result(-1, null, failedMsg);
    }
}
