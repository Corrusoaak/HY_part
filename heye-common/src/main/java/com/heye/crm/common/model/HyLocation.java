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
public class HyLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long locId;
    @Column(name = "loc_province")
    private String locProvince;
    @Column(name = "loc_city")
    private String locCity;
    @Column(name = "loc_location")
    private String locLocation;
    @Column(name = "loc_state")
    private int locState;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getLocId() {
        return locId;
    }

    public void setLocId(long locId) {
        this.locId = locId;
    }

    public String getLocProvince() {
        return locProvince;
    }

    public void setLocProvince(String locProvince) {
        this.locProvince = locProvince;
    }

    public String getLocCity() {
        return locCity;
    }

    public void setLocCity(String locCity) {
        this.locCity = locCity;
    }

    public String getLocLocation() {
        return locLocation;
    }

    public void setLocLocation(String locLocation) {
        this.locLocation = locLocation;
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
