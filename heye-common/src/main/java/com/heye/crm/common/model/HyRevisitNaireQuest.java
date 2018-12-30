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
public class HyRevisitNaireQuest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long nqId;
    @Column(name = "quest_id")
    private long questId;
    @Column(name = "naire_id")
    private long naireId;

    @Column(name = "nq_state")
    private int nqState;

    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getNqId() {
        return nqId;
    }

    public void setNqId(long nqId) {
        this.nqId = nqId;
    }

    public long getQuestId() {
        return questId;
    }

    public void setQuestId(long questId) {
        this.questId = questId;
    }

    public long getNaireId() {
        return naireId;
    }

    public void setNaireId(long naireId) {
        this.naireId = naireId;
    }

    public int getNqState() {
        return nqState;
    }

    public void setNqState(int nqState) {
        this.nqState = nqState;
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
