package com.heye.crm.common.utils;

import com.google.common.base.MoreObjects;
import com.google.gson.Gson;

/**
 * @author : lishuming
 */
public class Result {
    private static final long serialVersionUID = 1L;
    private static final Gson GSON = new Gson();

    private int code;
    private Object data;
    private String msg;

    public Result() {
    }

    public Result(boolean success, String msg) {
        this(success ? 0 : -1, "", msg);
    }

    public Result(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public Result setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public Result setData(Object d) {
        this.data = d;
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Result setCode(ResultCode code) {
        this.code = code.code;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("data", data != null ? GSON.toJson(data) : "")
                .add("msg", msg)
                .toString();
    }
}
