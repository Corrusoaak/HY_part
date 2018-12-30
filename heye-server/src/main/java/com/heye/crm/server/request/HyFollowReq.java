package com.heye.crm.server.request;

import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyFollowReq extends Req {
    private long storeId;
    private String orderId;
    private String expressId;
    private String expressCompany;
    private String storeTelephone;
    private String storeManager;
    private String storeDetailAddress;
    private String deliverHouse;
    private String deliverId;
    private Long startStamp;
    private Long endStamp;
    private Timestamp createdAt;

    public Long getStartStamp() {
        return startStamp;
    }

    public void setStartStamp(Long startStamp) {
        this.startStamp = startStamp;
    }

    public Long getEndStamp() {
        return endStamp;
    }

    public void setEndStamp(Long endStamp) {
        this.endStamp = endStamp;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getExpressCompany() {
        return expressCompany;
    }

    public void setExpressCompany(String expressCompany) {
        this.expressCompany = expressCompany;
    }

    public String getStoreTelephone() {
        return storeTelephone;
    }

    public void setStoreTelephone(String storeTelephone) {
        this.storeTelephone = storeTelephone;
    }

    public String getStoreManager() {
        return storeManager;
    }

    public void setStoreManager(String storeManager) {
        this.storeManager = storeManager;
    }

    public String getStoreDetailAddress() {
        return storeDetailAddress;
    }

    public void setStoreDetailAddress(String storeDetailAddress) {
        this.storeDetailAddress = storeDetailAddress;
    }

    public String getDeliverHouse() {
        return deliverHouse;
    }

    public void setDeliverHouse(String deliverHouse) {
        this.deliverHouse = deliverHouse;
    }

    public String getDeliverId() {
        return deliverId;
    }

    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
