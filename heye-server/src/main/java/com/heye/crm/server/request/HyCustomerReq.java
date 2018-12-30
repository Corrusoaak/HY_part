package com.heye.crm.server.request;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyCustomerReq extends Req {
    private int filterByDateType = -1;
    private long ctmId;
    private long storeId;
    private String ctmIds;
    private String ctmName;
    private String ctmAccountName;
    private String ctmPasswd;
    private int ctmSex;
    private String ctmEmail;
    private String ctmPicture;
    private String ctmPhoneNum;
    private String ctmTelephone;
    private Date ctmBirthDay;

    private String ctmProvince;
    private String ctmCity;
    private String ctmLocation;

    private String managerName;
    private String ctmDetailAddress;
    private String ctmZipCode;
    private String ctmIdentity;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private int ctmState;
    private double latitudeX;
    private double latitudeY;

    public int getFilterByDateType() {
        return filterByDateType;
    }

    public void setFilterByDateType(int filterByDateType) {
        this.filterByDateType = filterByDateType;
    }

    public long getCtmId() {
        return ctmId;
    }

    public void setCtmId(long ctmId) {
        this.ctmId = ctmId;
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

    public int getCtmState() {
        return ctmState;
    }

    public void setCtmState(int ctmState) {
        this.ctmState = ctmState;
    }

    public String getCtmIds() {
        return ctmIds;
    }

    public String getCtmCity() {
        return ctmCity;
    }

    public void setCtmCity(String ctmCity) {
        this.ctmCity = ctmCity;
    }

    public void setCtmIds(String ctmIds) {
        this.ctmIds = ctmIds;
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
/**
 public HyCustomer toHyCustomer() {
 HyCustomer hyCustomer = new HyCustomer();
 hyCustomer.setCreatedAt(this.createdAt);
 hyCustomer.setStoreId(this.storeId);
 hyCustomer.setCtmAccountName(this.ctmAccountName);
 hyCustomer.setCtmBirthDay(this.ctmBirthDay);
 hyCustomer.setCtmDetailAddress(this.ctmDetailAddress);
 hyCustomer.setCtmEmail(this.ctmEmail);
 hyCustomer.setCtmIdentity(this.ctmIdentity);
 hyCustomer.setCtmLocation(this.ctmLocation);
 hyCustomer.setCtmName(this.ctmName);
 hyCustomer.setCtmPasswd(this.ctmPasswd);
 hyCustomer.setCtmPhoneNum(this.ctmPhoneNum);
 hyCustomer.setCtmPicture(this.ctmPicture);
 hyCustomer.setCtmProvince(this.ctmProvince);
 hyCustomer.setCtmSex(this.ctmSex);
 hyCustomer.setCtmTelephone(this.ctmTelephone);
 hyCustomer.setCtmCity(this.ctmCity);
 hyCustomer.setCtmZipCode(this.ctmZipCode);
 hyCustomer.setManagerName(this.managerName);
 hyCustomer.setCtmState(this.ctmState);
 return hyCustomer;
 }

 public HyCustomer updateHyCustomer(HyCustomer hyCustomer) {
 hyCustomer.setCreatedAt(this.createdAt);
 hyCustomer.setCtmAccountName(this.ctmAccountName);
 hyCustomer.setCtmBirthDay(this.ctmBirthDay);
 hyCustomer.setCtmDetailAddress(this.ctmDetailAddress);
 hyCustomer.setCtmEmail(this.ctmEmail);
 hyCustomer.setCtmIdentity(this.ctmIdentity);
 hyCustomer.setCtmLocation(this.ctmLocation);
 hyCustomer.setCtmName(this.ctmName);
 hyCustomer.setCtmPasswd(this.ctmPasswd);
 hyCustomer.setCtmPhoneNum(this.ctmPhoneNum);
 hyCustomer.setCtmPicture(this.ctmPicture);
 hyCustomer.setCtmProvince(this.ctmProvince);
 hyCustomer.setCtmSex(this.ctmSex);
 hyCustomer.setCtmTelephone(this.ctmTelephone);
 hyCustomer.setCtmCity(this.ctmCity);
 hyCustomer.setCtmZipCode(this.ctmZipCode);
 hyCustomer.setManagerName(this.managerName);
 hyCustomer.setCtmState(this.ctmState);
 return hyCustomer;
 }*/
}
