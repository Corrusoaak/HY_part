package com.heye.crm.server.request;

import com.heye.crm.common.model.HyRole;
import com.heye.crm.common.model.HyRoleRight;

import java.sql.Timestamp;

/**
 * @author : lishuming
 */
public class HyRoleReq extends Req {
    private long rrId;
    private long roleId;
    private long rightId;
    private String roleName;
    private String roleDesc;
    // 父级角色Id
    private Long parentRoleId;
    // 多个权限之间用,分割
    private String rightList;
    private Integer rangeType;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRightList() {
        return rightList;
    }

    public void setRightList(String rightList) {
        this.rightList = rightList;
    }

    public Long getParentRoleId() {
        return parentRoleId;
    }

    public void setParentRoleId(Long parentRoleId) {
        this.parentRoleId = parentRoleId;
    }

    public Integer getRangeType() {
        return rangeType;
    }

    public void setRangeType(Integer rangeType) {
        this.rangeType = rangeType;
    }

    public HyRole toHyRole() {
        HyRole hyRole = new HyRole();
        hyRole.setRoleParentId(this.parentRoleId);
        hyRole.setRoleName(this.roleName);
        hyRole.setRoleDesc(this.getRoleDesc());
        hyRole.setRoleRangeType(this.rangeType);
        return hyRole;
    }

    public HyRoleRight toHyRoleRight(long roleId, long rightId) {
        HyRoleRight hyRoleRight = new HyRoleRight();
        hyRoleRight.setRoleId(roleId);
        hyRoleRight.setRightId(rightId);
        hyRoleRight.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        // TODO: what's rightType?
        return hyRoleRight;
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

    public long getRrId() {
        return rrId;
    }

    public void setRrId(long rrId) {
        this.rrId = rrId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
