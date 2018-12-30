package com.heye.crm.server.request;

import com.heye.crm.common.model.HyCare;

import java.sql.Date;

/**
 * @author : lishuming
 */
public class HyCareReq extends Req {
    private Long careId;
    private String festName;
    private Date festDate;
    private String sendTime;
    private String sendDesc;
    private Integer verifyState;
    private Integer sendState;
    private Integer switchState;
    private Integer ctmRangeType;
    private Integer ctmSex;
    private String ageRange;
    private String consumeRange;

    public HyCare toHyCare() {
        HyCare hyCare = new HyCare();

        hyCare.setFestName(this.festName);
        hyCare.setFestDate(this.festDate);
        hyCare.setSendTime(this.sendTime);
        hyCare.setSendDesc(this.sendDesc);

        hyCare.setVerifyState(this.verifyState);
        hyCare.setSendState(this.sendState);
        hyCare.setSwitchState(this.switchState);
        hyCare.setCtmRangeType(this.ctmRangeType);
        hyCare.setCtmSex(this.ctmSex);
        hyCare.setAgeRange(this.ageRange);
        hyCare.setConsumeRange(this.consumeRange);

        return hyCare;
    }

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
}
