package com.heye.crm.common.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : fanjinqian
 */
public class HyWeixin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long weixinId;
    @JSONField(serialize = false)
    @Column(name = "open_id")
    private String openId;
    @Column(name = "ctm_id")
    private long ctmId;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(long weixinId) {
        this.weixinId = weixinId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getCtmId() {
        return ctmId;
    }

    public void setCtmId(long ctmId) {
        this.ctmId = ctmId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
