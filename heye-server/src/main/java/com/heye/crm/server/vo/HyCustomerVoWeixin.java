package com.heye.crm.server.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.heye.crm.common.model.HyCustomer;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyCustomerVoWeixin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ctmId;

    @JSONField(serialize = false)
    @Column(name = "open_id")
    private String openId;

    @Column(name = "weixin_id")
    private long weixinId;

    @Column(name = "store_id")
    private long storeId;
    @Column(name = "ctm_name")
    private String ctmName;
    @Column(name = "ctm_account_name")
    private String ctmAccountName;
    @JSONField(serialize = false)
    @Column(name = "ctm_password")
    private String ctmPasswd;
    @Column(name = "ctm_sex")
    private int ctmSex;
    @Column(name = "ctm_state")
    private int ctmState;
    @Column(name = "ctm_email")
    private String ctmEmail;
    @Column(name = "ctm_picture")
    private String ctmPicture;
    @Column(name = "ctm_phone_num")
    private String ctmPhoneNum;
    @Column(name = "ctm_telephone")
    private String ctmTelephone;
    @Column(name = "ctm_birth_day")
    private Date ctmBirthDay;
    @Column(name = "ctm_province")
    private String ctmProvince;
    @Column(name = "ctm_city")
    private String ctmCity;
    @Column(name = "ctm_manager_name")
    private String managerName;
    @Column(name = "ctm_location")
    private String ctmLocation;
    @Column(name = "ctm_detail_address")
    private String ctmDetailAddress;
    @Column(name = "ctm_zip_code")
    private String ctmZipCode;
    @JSONField(serialize = false)
    @Column(name = "ctm_identity")
    private String ctmIdentity;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public HyCustomerVoWeixin(HyCustomer hyCustomer, long weixinId) {
        this.ctmId = hyCustomer.getCtmId();
        this.storeId = hyCustomer.getStoreId();
        this.ctmName = hyCustomer.getCtmName();
        this.ctmAccountName = hyCustomer.getCtmAccountName();
        this.ctmPasswd = hyCustomer.getCtmPasswd();
        this.ctmSex = hyCustomer.getCtmSex();
        this.ctmState = hyCustomer.getCtmState();
        this.ctmEmail = hyCustomer.getCtmEmail();
        this.ctmPicture = hyCustomer.getCtmPicture();
        this.ctmPhoneNum = hyCustomer.getCtmPhoneNum();
        this.ctmTelephone = hyCustomer.getCtmTelephone();
        this.ctmBirthDay = hyCustomer.getCtmBirthDay();
        this.ctmProvince = hyCustomer.getCtmProvince();
        this.ctmCity = hyCustomer.getCtmCity();
        this.managerName = hyCustomer.getManagerName();
        this.ctmLocation = hyCustomer.getCtmLocation();
        this.ctmDetailAddress = hyCustomer.getCtmDetailAddress();
        this.ctmZipCode = hyCustomer.getCtmZipCode();
        this.ctmIdentity = hyCustomer.getCtmIdentity();
        this.createdAt = hyCustomer.getCreatedAt();
        this.updatedAt = hyCustomer.getUpdatedAt();
        this.weixinId = weixinId;
    }

    public long getCtmId() {
        return ctmId;
    }

    public void setCtmId(long ctmId) {
        this.ctmId = ctmId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }

    public String getCtmName() {
        return ctmName;
    }

    public void setCtmName(String ctmName) {
        this.ctmName = ctmName;
    }

    public String getCtmAccountName() {
        return ctmAccountName;
    }

    public void setCtmAccountName(String ctmAccountName) {
        this.ctmAccountName = ctmAccountName;
    }

    public String getCtmPasswd() {
        return ctmPasswd;
    }

    public void setCtmPasswd(String ctmPasswd) {
        this.ctmPasswd = ctmPasswd;
    }

    public int getCtmSex() {
        return ctmSex;
    }

    public void setCtmSex(int ctmSex) {
        this.ctmSex = ctmSex;
    }

    public int getCtmState() {
        return ctmState;
    }

    public void setCtmState(int ctmState) {
        this.ctmState = ctmState;
    }

    public String getCtmEmail() {
        return ctmEmail;
    }

    public void setCtmEmail(String ctmEmail) {
        this.ctmEmail = ctmEmail;
    }

    public String getCtmPicture() {
        return ctmPicture;
    }

    public void setCtmPicture(String ctmPicture) {
        this.ctmPicture = ctmPicture;
    }

    public String getCtmPhoneNum() {
        return ctmPhoneNum;
    }

    public void setCtmPhoneNum(String ctmPhoneNum) {
        this.ctmPhoneNum = ctmPhoneNum;
    }

    public String getCtmTelephone() {
        return ctmTelephone;
    }

    public void setCtmTelephone(String ctmTelephone) {
        this.ctmTelephone = ctmTelephone;
    }

    public Date getCtmBirthDay() {
        return ctmBirthDay;
    }

    public void setCtmBirthDay(Date ctmBirthDay) {
        this.ctmBirthDay = ctmBirthDay;
    }

    public String getCtmProvince() {
        return ctmProvince;
    }

    public void setCtmProvince(String ctmProvince) {
        this.ctmProvince = ctmProvince;
    }

    public String getCtmCity() {
        return ctmCity;
    }

    public void setCtmCity(String ctmCity) {
        this.ctmCity = ctmCity;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getCtmLocation() {
        return ctmLocation;
    }

    public void setCtmLocation(String ctmLocation) {
        this.ctmLocation = ctmLocation;
    }

    public String getCtmDetailAddress() {
        return ctmDetailAddress;
    }

    public void setCtmDetailAddress(String ctmDetailAddress) {
        this.ctmDetailAddress = ctmDetailAddress;
    }

    public String getCtmZipCode() {
        return ctmZipCode;
    }

    public void setCtmZipCode(String ctmZipCode) {
        this.ctmZipCode = ctmZipCode;
    }

    public String getCtmIdentity() {
        return ctmIdentity;
    }

    public void setCtmIdentity(String ctmIdentity) {
        this.ctmIdentity = ctmIdentity;
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

    public long getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(long weixinId) {
        this.weixinId = weixinId;
    }
}
