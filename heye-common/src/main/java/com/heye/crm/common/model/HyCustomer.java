package com.heye.crm.common.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long ctmId;
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
    @Column(name = "latitude_x")
    private double latitudeX;
    @Column(name = "latitude_y")
    private double latitudeY;

    public long getCtmId() {
        return ctmId;
    }

    public void setCtmId(long ctmId) {
        this.ctmId = ctmId;
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

    public String getCtmEmail() {
        return ctmEmail;
    }

    public void setCtmEmail(String ctmEmail) {
        this.ctmEmail = ctmEmail;
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

    public Date getCtmBirthDay() {
        return ctmBirthDay;
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

    public void setCtmBirthDay(Date ctmBirthDay) {
        this.ctmBirthDay = ctmBirthDay;
    }

    public String getCtmProvince() {
        return ctmProvince;
    }

    public void setCtmProvince(String ctmProvince) {
        this.ctmProvince = ctmProvince;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getCtmPicture() {
        return ctmPicture;
    }

    public void setCtmPicture(String ctmPicture) {
        this.ctmPicture = ctmPicture;
    }

    public int getCtmState() {
        return ctmState;
    }

    public void setCtmState(int ctmState) {
        this.ctmState = ctmState;
    }

    public String getCtmCity() {
        return ctmCity;
    }

    public void setCtmCity(String ctmCity) {
        this.ctmCity = ctmCity;
    }

    public long getStoreId() {
        return storeId;
    }

    public void setStoreId(long storeId) {
        this.storeId = storeId;
    }
}
