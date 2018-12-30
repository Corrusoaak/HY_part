package com.heye.crm.common.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyAdvise {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long adviseId;
    @Column(name = "ctm_id")
    private long ctmId;
    @Column(name = "advise_state")
    private int adviseState;
    @Column(name = "advise_type")
    private int adviseType;
    @Column(name = "advise_desc")
    private String adviseDesc;
    @Column(name = "advise_pictures")
    private String advisePictures;
    @Column(name = "advise_replies")
    private String aviseReplies;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getAdviseId() {
        return adviseId;
    }

    public void setAdviseId(long adviseId) {
        this.adviseId = adviseId;
    }

    public long getCtmId() {
        return ctmId;
    }

    public void setCtmId(long ctmId) {
        this.ctmId = ctmId;
    }

    public int getAdviseState() {
        return adviseState;
    }

    public void setAdviseState(int adviseState) {
        this.adviseState = adviseState;
    }

    public int getAdviseType() {
        return adviseType;
    }

    public void setAdviseType(int adviseType) {
        this.adviseType = adviseType;
    }

    public String getAdviseDesc() {
        return adviseDesc;
    }

    public void setAdviseDesc(String adviseDesc) {
        this.adviseDesc = adviseDesc;
    }

    public String getAdvisePictures() {
        return advisePictures;
    }

    public void setAdvisePictures(String advisePictures) {
        this.advisePictures = advisePictures;
    }

    public String getAviseReplies() {
        return aviseReplies;
    }

    public void setAviseReplies(String aviseReplies) {
        this.aviseReplies = aviseReplies;
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
