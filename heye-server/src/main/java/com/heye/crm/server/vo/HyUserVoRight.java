package com.heye.crm.server.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : Zhanhanbing
 */
public class HyUserVoRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "role_id")
    private int roleId;
    @Column(name = "right_id")
    private long rightId;
    @Column(name = "right_parent_id")
    private long rightParentId;
    @Column(name = "right_name")
    private String rightName;
    @Column(name = "right_desc")
    private String rightDesc;
    @Column(name = "right_created_at")
    private Timestamp rightCreatedAt;
    @Column(name = "right_updated_at")
    private Timestamp rightUpdatedAt;

    public HyUserVoRight() {
    }

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

    public long getRightParentId() {
        return rightParentId;
    }

    public void setRightParentId(long rightParentId) {
        this.rightParentId = rightParentId;
    }

    public String getRightName() {
        return rightName;
    }

    public void setRightName(String rightName) {
        this.rightName = rightName;
    }

    public String getRightDesc() {
        return rightDesc;
    }

    public void setRightDesc(String rightDesc) {
        this.rightDesc = rightDesc;
    }

    public Timestamp getRightCreatedAt() {
        return rightCreatedAt;
    }

    public void setRightCreatedAt(Timestamp rightCreatedAt) {
        this.rightCreatedAt = rightCreatedAt;
    }

    public Timestamp getRightUpdatedAt() {
        return rightUpdatedAt;
    }

    public void setRightUpdatedAt(Timestamp rightUpdatedAt) {
        this.rightUpdatedAt = rightUpdatedAt;
    }

    public long getRightId() {
        return rightId;
    }

    public void setRightId(long rightId) {
        this.rightId = rightId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
