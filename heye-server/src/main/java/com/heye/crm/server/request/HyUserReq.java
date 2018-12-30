package com.heye.crm.server.request;

import com.heye.crm.common.model.HyUser;

import java.sql.Date;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyUserReq extends Req {
    private long userId;
    private long roleId;
    private long userParentId;
    private String userParentName;
    private String userName;
    private String userAccountName;
    private String userPasswd;
    private int userSex;
    private int userState;
    private String userPicture;
    private String userEmail;
    private String userPhoneNum;
    private String userTelephone;
    private String userProvince;
    private String userCity;
    private String userLocation;
    private Date userBirthDay;
    private String userDetailAddress;
    private String userZipCode;
    private String userIdentity;
    private Timestamp createdAt;

    private String originPasswd;

    private String realName;
    private String roleName;
    private String locationName;
    private Integer userRangeType;

    private String newTelephoenNum;
    private String originTelephoenNum;
    private String telMsgCode;

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

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public String getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(String userPicture) {
        this.userPicture = userPicture;
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

    public String getUserProvince() {
        return userProvince;
    }

    public void setUserProvince(String userProvince) {
        this.userProvince = userProvince;
    }

    public Date getUserBirthDay() {
        return userBirthDay;
    }

    public void setUserBirthDay(Date userBirthDay) {
        this.userBirthDay = userBirthDay;
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

    public HyUser toHyUser() {
        HyUser hyUser = new HyUser();
        hyUser.setUserId(this.getUserId());
        hyUser.setCreatedAt(this.createdAt);
        hyUser.setUserAccountName(this.userAccountName);
        hyUser.setUserDetailAddress(this.userDetailAddress);
        hyUser.setUserEmail(this.userEmail);
        hyUser.setUserLocation(this.userLocation);
        hyUser.setUserIdentity(this.userIdentity);
        hyUser.setUserName(this.userName);
        hyUser.setUserPasswd(this.userPasswd);
        hyUser.setUserSex(this.userSex);
        hyUser.setUserTelephone(this.userTelephone);
        hyUser.setUserPhoneNum(this.userPhoneNum);
        hyUser.setUserZipCode(this.userZipCode);
        hyUser.setUserState(this.userState);
        hyUser.setUserBirthDay(this.userBirthDay);
        hyUser.setUserProvince(this.userProvince);
        hyUser.setUserPicture(this.userPicture);
        hyUser.setRoleId(this.roleId);
        hyUser.setUserParentId(this.userParentId);
        hyUser.setUserParentName(this.userParentName);

        return hyUser;
    }

    public String getUserCity() {
        return userCity;
    }

    public void setUserCity(String userCity) {
        this.userCity = userCity;
    }

    public String getOriginPasswd() {
        return originPasswd;
    }

    public void setOriginPasswd(String originPasswd) {
        this.originPasswd = originPasswd;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Integer getUserRangeType() {
        return userRangeType;
    }

    public void setUserRangeType(Integer userRangeType) {
        this.userRangeType = userRangeType;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getNewTelephoenNum() {
        return newTelephoenNum;
    }

    public void setNewTelephoenNum(String newTelephoenNum) {
        this.newTelephoenNum = newTelephoenNum;
    }

    public String getOriginTelephoenNum() {
        return originTelephoenNum;
    }

    public void setOriginTelephoenNum(String originTelephoenNum) {
        this.originTelephoenNum = originTelephoenNum;
    }

    public String getTelMsgCode() {
        return telMsgCode;
    }

    public void setTelMsgCode(String telMsgCode) {
        this.telMsgCode = telMsgCode;
    }
}
