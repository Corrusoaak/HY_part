package com.heye.crm.server.vo;

import javax.persistence.Column;
import java.sql.Timestamp;

/**
 * @author : fanjinqian
 */
public class HyRoleRightJoin {
    @Column(name = "rr_id")
    private long rrId;
    @Column(name = "role_id")
    private long roleId;
    @Column(name = "right_id")
    private long rightId;
    @Column(name = "right_type")
    private int rightType;

    @Column(name = "role_parent_id")
    private long roleParentId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_range_type")
    private Integer roleRangeType;
    @Column(name = "role_state")
    private Integer roleState;
    @Column(name = "role_desc")
    private String roleDesc;
    @Column(name = "created_at")
    private Timestamp createdAt;

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public long getRightId() {
        return rightId;
    }

    public void setRightId(long rightId) {
        this.rightId = rightId;
    }

    public int getRightType() {
        return rightType;
    }

    public void setRightType(int rightType) {
        this.rightType = rightType;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
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

    public Integer getRoleRangeType() {
        return roleRangeType;
    }

    public void setRoleRangeType(Integer roleRangeType) {
        this.roleRangeType = roleRangeType;
    }

    public Integer getRoleState() {
        return roleState;
    }

    public void setRoleState(Integer roleState) {
        this.roleState = roleState;
    }

    public long getRrId() {
        return rrId;
    }

    public void setRrId(long rrId) {
        this.rrId = rrId;
    }
}
