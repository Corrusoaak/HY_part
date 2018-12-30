package com.heye.crm.common.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyCare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long careId;

    @Column(name = "fest_name")
    String festName;
    @Column(name = "fest_date")
    Date festDate;
    @Column(name = "send_time")
    String sendTime;
    @Column(name = "send_desc")
    String sendDesc;
    @Column(name = "verify_state")
    Integer verifyState;
    @Column(name = "send_state")
    Integer sendState;
    @Column(name = "switch_state")
    Integer switchState;
    @Column(name = "ctm_range_type")
    Integer ctmRangeType;
    @Column(name = "ctm_sex")
    Integer ctmSex;
    @Column(name = "age_range")
    String ageRange;
    @Column(name = "consume_range")
    String consumeRange;
    @Column(name = "created_at")
    Timestamp createdAt;
    @Column(name = "updated_at")
    Timestamp updatedAt;

    public Long getCareId() {
        return careId;
    }

    public void setCareId(Long careId) {
        this.careId = careId;
    }

    public String getFestName() {
        return festName;
    }

    public void setFestName(String festName) {
        this.festName = festName;
    }

    public Date getFestDate() {
        return festDate;
    }

    public void setFestDate(Date festDate) {
        this.festDate = festDate;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSendDesc() {
        return sendDesc;
    }

    public void setSendDesc(String sendDesc) {
        this.sendDesc = sendDesc;
    }

    public Integer getVerifyState() {
        return verifyState;
    }

    public void setVerifyState(Integer verifyState) {
        this.verifyState = verifyState;
    }

    public Integer getSendState() {
        return sendState;
    }

    public void setSendState(Integer sendState) {
        this.sendState = sendState;
    }

    public Integer getSwitchState() {
        return switchState;
    }

    public void setSwitchState(Integer switchState) {
        this.switchState = switchState;
    }

    public Integer getCtmRangeType() {
        return ctmRangeType;
    }

    public void setCtmRangeType(Integer ctmRangeType) {
        this.ctmRangeType = ctmRangeType;
    }

    public Integer getCtmSex() {
        return ctmSex;
    }

    public void setCtmSex(Integer ctmSex) {
        this.ctmSex = ctmSex;
    }

    public String getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(String ageRange) {
        this.ageRange = ageRange;
    }

    public String getConsumeRange() {
        return consumeRange;
    }

    public void setConsumeRange(String consumeRange) {
        this.consumeRange = consumeRange;
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
