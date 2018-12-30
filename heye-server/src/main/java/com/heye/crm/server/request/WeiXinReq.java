package com.heye.crm.server.request;

import com.heye.crm.common.model.HyCustomer;

import java.sql.Date;

/**
 * @author : lishuming
 */
public class WeiXinReq {
    private String url;
    private String code;
    private long weixinId;
    private String ctmTelephone;
    private String verifyCode;

    private long ctmId;
    private long storeId;
    private String ctmName;
    private String ctmAccountName;
    private String ctmPasswd;
    private int ctmSex;
    private int ctmState;
    private String ctmEmail;
    private String ctmPicture;
    private String ctmPhoneNum;
    private Date ctmBirthDay;
    private String ctmProvince;
    private String managerName;
    private String ctmLocation;
    private String ctmDetailAddress;
    private String ctmZipCode;
    private String ctmIdentity;

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCtmTelephone() {
        return ctmTelephone;
    }

    public void setCtmTelephone(String ctmTelephone) {
        this.ctmTelephone = ctmTelephone;
    }

    public long getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(long weixinId) {
        this.weixinId = weixinId;
    }

    public HyCustomer toHyCustomer() {
        HyCustomer hyCustomer = new HyCustomer();
        hyCustomer.setCtmId(this.ctmId);
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
        hyCustomer.setCtmZipCode(this.ctmZipCode);
        hyCustomer.setManagerName(this.managerName);
        hyCustomer.setCtmState(this.ctmState);
        return hyCustomer;
    }
}
