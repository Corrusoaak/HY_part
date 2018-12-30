package com.heye.crm.common.utils;

import com.google.common.base.MoreObjects;

/**
 * code = 0: 正确返回。可以从api返回的对应字段中取数据。
 * code > 0: 调用API时发生错误，需要开发者进行相应的处理。
 * -50 < code < 0: 权限验证失败，需要开发者进行相应的处理。
 * code = -50: 系统内部错误，请联系技术支持，调查问题原因并获得解决方案。
 */
public class SmsApiResult {
    private int code;
    private String msg;
    private long count;
    private float fee;
    private String unit;
    private String mobile;
    private long sid;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("code", this.code)
                .add("msg", this.msg)
                .add("count", this.count)
                .add("fee", this.fee)
                .add("unit", this.unit)
                .add("mobile", this.mobile)
                .add("sid", this.sid)
                .toString();
    }
}
