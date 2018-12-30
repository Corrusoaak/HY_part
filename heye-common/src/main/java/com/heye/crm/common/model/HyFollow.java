package com.heye.crm.common.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : lishuing
 */
public class HyFollow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long followId;
    @Column(name = "store_id")
    private long storeId;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "express_id")
    private String expressId;
    @Column(name = "express_company")
    private String expressCompany;
    @Column(name = "store_telephone")
    private String storeTelephone;
    @Column(name = "store_manager")
    private String storeManager;
    @Column(name = "store_detail_address")
    private String storeDetailAddress;
    @Column(name = "deliver_house")
    private String deliverHouse;
    @Column(name = "deliver_id")
    private String deliverId;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    @JSONField(serialize = false)
    private Timestamp updatedAt;

    public long getFollowId() {
        return followId;
    }

    public void setFollowId(long followId) {
        this.followId = followId;
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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
