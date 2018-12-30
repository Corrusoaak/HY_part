package com.heye.crm.server.consts;

/**
 * @author : lishuming
 */
public enum CRole {
    SUPER_ADMIN(1),
    PROVINCE_ADMIN(2),
    CITY_ADMIN(3), // 地区
    REGION_ADMIN(4), // 区域
    STORE_ADMIN(5),
    CUSTOMER(6),
    UNKNOWN(7);

    private int roleId;

    public static CRole getRole(int roleId) {
        switch (roleId) {
        case 1:
            return SUPER_ADMIN;
        case 2:
            return PROVINCE_ADMIN;
        case 3:
            return CITY_ADMIN;
        case 4:
            return REGION_ADMIN;
        case 5:
            return STORE_ADMIN;
        case 6:
            return CUSTOMER;
        case 7:
        default:
            return UNKNOWN;
        }
    }

    public static CRole getParentRole(int roleId) {
        switch (roleId) {
        case 1:
            return SUPER_ADMIN;
        case 2:
            return SUPER_ADMIN;
        case 3:
            return PROVINCE_ADMIN;
        case 4:
            return CITY_ADMIN;
        case 5:
            return REGION_ADMIN;
        case 6:
            return STORE_ADMIN;
        default:
            return UNKNOWN;
        }
    }

    CRole(int roleId) {
        this.roleId = roleId;
    }

    public static int getMaxNoDeletedRoleId() {
        return CRole.REGION_ADMIN.ordinal();
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
