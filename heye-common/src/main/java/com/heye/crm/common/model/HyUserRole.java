package com.heye.crm.common.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyUserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long urId;
    @Column(name = "user_id")
    private long userId;
    @Column(name = "role_id")
    private long roleId;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    public long getUrId() {
        return urId;
    }

    public void setUrId(long urId) {
        this.urId = urId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
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
}
