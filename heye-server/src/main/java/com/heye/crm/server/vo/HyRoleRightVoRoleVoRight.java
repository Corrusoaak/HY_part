package com.heye.crm.server.vo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * @author : fanjinqian
 */
public class HyRoleRightVoRoleVoRight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rrId;
    @Column(name = "role_id")
    private long roleId;
    @Column(name = "right_id")
    private long rightId;
    @Column(name = "right_type")
    private int rightType;
    @Column(name = "created_at")
    private Timestamp createdAt;
    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "role_parent_id")
    private long roleParentId;
    @Column(name = "role_name")
    private String roleName;
    @Column(name = "role_desc")
    private String roleDesc;

    @Column(name = "right_parent_id")
    private long rightParentId;
    @Column(name = "right_name")
    private String rightName;
    @Column(name = "right_desc")
    private String rightDesc;

    public long getRrId() {
        return rrId;
    }

    public void setRrId(long rrId) {
        this.rrId = rrId;
    }

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

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
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
}
