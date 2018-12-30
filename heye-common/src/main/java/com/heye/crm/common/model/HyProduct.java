package com.heye.crm.common.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigInteger;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long proId;
    @Column(name = "pro_name")
    private String proName;
    @Column(name = "pro_model")
    private String proModel;
    @Column(name = "pro_price")
    private BigInteger proPrice;
    @Column(name = "pro_bar_code")
    private String proBarCode;
    @Column(name = "pro_desc")
    private String proDesc;
    @Column(name = "pro_pictures")
    private String proPictures;
    @Column(name = "pro_state")
    private int proState;
    @Column(name = "pro_display")
    private int proDisplay;
    @Column(name = "pro_unit_price")
    private long proUnitPrice;
    @Column(name = "pro_type")
    private String proType;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getProId() {
        return proId;
    }

    public void setProId(long proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public String getProModel() {
        return proModel;
    }

    public void setProModel(String proModel) {
        this.proModel = proModel;
    }

    public BigInteger getProPrice() {
        return proPrice;
    }

    public void setProPrice(BigInteger proPrice) {
        this.proPrice = proPrice;
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

    public int getProState() {
        return proState;
    }

    public void setProState(int proState) {
        this.proState = proState;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setProBarCode(String proBarCode) {
        this.proBarCode = proBarCode;
    }

    public String getProDesc() {
        return proDesc;
    }

    public void setProDesc(String proDesc) {
        this.proDesc = proDesc;
    }

    public String getProPictures() {
        return proPictures;
    }

    public void setProPictures(String proPictures) {
        this.proPictures = proPictures;
    }

    public String getProBarCode() {
        return proBarCode;
    }

    public int getProDisplay() {
        return proDisplay;
    }

    public void setProDisplay(int proDisplay) {
        this.proDisplay = proDisplay;
    }

    public long getProUnitPrice() {
        return proUnitPrice;
    }

    public void setProUnitPrice(long proUnitPrice) {
        this.proUnitPrice = proUnitPrice;
    }

    public String getProType() {
        return proType;
    }

    public void setProType(String proType) {
        this.proType = proType;
    }
}
