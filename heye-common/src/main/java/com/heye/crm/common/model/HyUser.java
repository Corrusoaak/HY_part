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
public class HyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_account_name")
    private String userAccountName;
    @JSONField(serialize = false)
    @Column(name = "user_password")
    private String userPasswd;
    @Column(name = "user_sex")
    private int userSex;
    @Column(name = "user_range_type")
    private int userRangeType;
    @Column(name = "user_state")
    private int userState;
    @Column(name = "user_picture")
    private String userPicture;
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_phone_num")
    private String userPhoneNum;
    @Column(name = "user_telephone")
    private String userTelephone;
    @Column(name = "user_province")
    private String userProvince;
    @Column(name = "user_city")
    private String userCity;
    @Column(name = "user_birth_day")
    private Date userBirthDay;
    @Column(name = "user_location")
    private String userLocation;
    @Column(name = "user_detail_address")
    private String userDetailAddress;
    @Column(name = "user_zip_code")
    private String userZipCode;
    @Column(name = "user_identity")
    private String userIdentity;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @JSONField(serialize = false)
    @Column(name = "updated_at")
    private Timestamp updatedAt;
    @Column(name = "role_id")
    private long roleId;
    @JSONField(serialize = false)
    @Column(name = "user_parent_id")
    private long userParentId;
    @Column(name = "user_parent_name")
    private String userParentName;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccountName() {
        return userAccountName;
    }

    public void setUserAccountName(String userAccountName) {
        this.userAccountName = userAccountName;
    }

    public String getUserPasswd() {
        return userPasswd;
    }

    public void setUserPasswd(String userPasswd) {
        this.userPasswd = userPasswd;
    }

    public int getUserSex() {
        return userSex;
    }

    public void setUserSex(int userSex) {
        this.userSex = userSex;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNum() {
        return userPhoneNum;
    }

    public void setUserPhoneNum(String userPhoneNum) {
        this.userPhoneNum = userPhoneNum;
    }

    public String getUserTelephone() {
        return userTelephone;
    }

    public void setUserTelephone(String userTelephone) {
        this.userTelephone = userTelephone;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getUserDetailAddress() {
        return userDetailAddress;
    }

    public void setUserDetailAddress(String userDetailAddress) {
        this.userDetailAddress = userDetailAddress;
    }

    public String getUserZipCode() {
        return userZipCode;
    }

    public void setUserZipCode(String userZipCode) {
        this.userZipCode = userZipCode;
    }

    public String getUserIdentity() {
        return userIdentity;
    }

    public void setUserIdentity(String userIdentity) {
        this.userIdentity = userIdentity;
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

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public Date getUserBirthDay() {
        return userBirthDay;
    }

    public void setUserBirthDay(Date userBirthDay) {
        this.userBirthDay = userBirthDay;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getUserParentId() {
        return userParentId;
    }

    public void setUserParentId(long userParentId) {
        this.userParentId = userParentId;
    }

    public String getUserParentName() {
        return userParentName;
    }

    public void setUserParentName(String userParentName) {
        this.userParentName = userParentName;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public int getUserRangeType() {
        return userRangeType;
    }

    public void setUserRangeType(int userRangeType) {
        this.userRangeType = userRangeType;
    }
}
