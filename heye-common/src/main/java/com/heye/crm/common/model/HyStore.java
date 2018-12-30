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
public class HyStore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long storeId;
    @Column(name = "store_manager_id")
    private long storeManagerId;
    @Column(name = "store_phone_num")
    private String storePhoneNum;
    @Column(name = "store_telephone")
    private String storeTelephone;
    @Column(name = "store_name")
    private String storeName;
    @Column(name = "store_position")
    private String storePosition;
    @Column(name = "store_province")
    private String storeProvince;
    @Column(name = "store_city")
    private String storeCity;
    @Column(name = "store_address")
    private String storeAddress;
    @Column(name = "store_state")
    private int storeState;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "latitude_x")
    private double latitudeX;
    @Column(name = "latitude_y")
    private double latitudeY;

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public long getStoreManagerId() {
        return storeManagerId;
    }

    public void setStoreManagerId(long storeManagerId) {
        this.storeManagerId = storeManagerId;
    }

    public String getStorePhoneNum() {
        return storePhoneNum;
    }

    public void setStorePhoneNum(String storePhoneNum) {
        this.storePhoneNum = storePhoneNum;
    }

    public String getStoreTelephone() {
        return storeTelephone;
    }

    public void setStoreTelephone(String storeTelephone) {
        this.storeTelephone = storeTelephone;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePosition() {
        return storePosition;
    }

    public void setStorePosition(String storePosition) {
        this.storePosition = storePosition;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdateAt() {
        return updatedAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updatedAt = updateAt;
    }

    public int getStoreState() {
        return storeState;
    }

    public void setStoreState(int storeState) {
        this.storeState = storeState;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getStoreProvince() {
        return storeProvince;
    }

    public void setStoreProvince(String storeProvince) {
        this.storeProvince = storeProvince;
    }

    public String getStoreCity() {
        return storeCity;
    }

    public void setStoreCity(String storeCity) {
        this.storeCity = storeCity;
    }

    public String getStoreAddress() {
        return storeAddress;
    }

    public void setStoreAddress(String storeAddress) {
        this.storeAddress = storeAddress;
    }

    public double getLatitudeX() {
        return latitudeX;
    }

    public void setLatitudeX(double latitudeX) {
        this.latitudeX = latitudeX;
    }

    public double getLatitudeY() {
        return latitudeY;
    }

    public void setLatitudeY(double latitudeY) {
        this.latitudeY = latitudeY;
    }
}
