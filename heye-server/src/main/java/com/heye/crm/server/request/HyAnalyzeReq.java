package com.heye.crm.server.request;

import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyAnalyzeReq extends Req {
    private Integer anaType;
    private Integer aggLevel;
    private Timestamp startDate;
    private Timestamp endDate;

    public Integer getAnaType() {
        return anaType;
    }

    public void setAnaType(Integer anaType) {
        this.anaType = anaType;
    }

    public Integer getAggLevel() {
        return aggLevel;
    }

    public void setAggLevel(Integer aggLevel) {
        this.aggLevel = aggLevel;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }
}
