package com.heye.crm.common.model;

import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : fanjinqian
 */
public class HyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long roleId;
    @Column(name = "role_parent_id")
    private long roleParentId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_state")
    private Integer roleState;
    @Column(name = "role_range_type")
    private Integer roleRangeType;
    @Column(name = "role_desc")
    private String roleDesc;
    @Column(name = "created_at")
    private Timestamp createdAt;

    @Column(name = "updated_at")
    @JSONField(serialize = false)
    private Timestamp updatedAt;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getRoleParentId() {
        return roleParentId;
    }

    public void setRoleParentId(long roleParentId) {
        this.roleParentId = roleParentId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }

    public Integer getRoleRangeType() {
        return roleRangeType;
    }

    public void setRoleRangeType(Integer roleRangeType) {
        this.roleRangeType = roleRangeType;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
