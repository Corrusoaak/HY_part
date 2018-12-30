package com.heye.crm.common.utils;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.base.MoreObjects;

/**
 * @author : lishuming
 */
public class WeiXinCode {
    @JSONField(name = "session_key")
    String sessionKey;
    @JSONField(name = "expires_in")
    String expiresIn;
    @JSONField(name = "openid")
    String openId;

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("open_id", this.openId)
                .add("expires_in", this.expiresIn)
                .add("session_key", this.sessionKey)
                .toString();
    }
}
