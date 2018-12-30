package com.heye.crm.common.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyAnalyze {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long anaId;

    @Column(name = "ana_name")
    String anaName;
    @Column(name = "agg_level")
    Integer aggLevel;
    @Column(name = "ana_type")
    Integer anaType;
    @Column(name = "label_name")
    String labelName;
    @Column(name = "label_value")
    Integer labelValue;
    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at")
    Timestamp updatedAt;

    public Long getAnaId() {
        return anaId;
    }

    public void setAnaId(Long anaId) {
        this.anaId = anaId;
    }

    public String getAnaName() {
        return anaName;
    }

    public void setAnaName(String anaName) {
        this.anaName = anaName;
    }

    public Integer getAnaType() {
        return anaType;
    }

    public void setAnaType(Integer anaType) {
        this.anaType = anaType;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public Integer getLabelValue() {
        return labelValue;
    }

    public void setLabelValue(Integer labelValue) {
        this.labelValue = labelValue;
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

    public Integer getAggLevel() {
        return aggLevel;
    }

    public void setAggLevel(Integer aggLevel) {
        this.aggLevel = aggLevel;
    }
}
