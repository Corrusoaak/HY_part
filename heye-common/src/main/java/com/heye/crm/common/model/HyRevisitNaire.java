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
public class HyRevisitNaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long naireId;

    @Column(name = "naire_state")
    private int naireState;
    @Column(name = "naire_type")
    private int naireType;
    @Column(name = "naire_name")
    private String naireName;
    @Column(name = "naire_list")
    private String naireList;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getNaireId() {
        return naireId;
    }

    public void setNaireId(long naireId) {
        this.naireId = naireId;
    }

    public int getNaireState() {
        return naireState;
    }

    public void setNaireState(int naireState) {
        this.naireState = naireState;
    }

    public int getNaireType() {
        return naireType;
    }

    public void setNaireType(int naireType) {
        this.naireType = naireType;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getNaireName() {
        return naireName;
    }

    public void setNaireName(String naireName) {
        this.naireName = naireName;
    }

    public String getNaireList() {
        return naireList;
    }

    public void setNaireList(String naireList) {
        this.naireList = naireList;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}

