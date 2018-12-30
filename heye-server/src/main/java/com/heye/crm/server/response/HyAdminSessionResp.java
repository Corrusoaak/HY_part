package com.heye.crm.server.response;

import com.heye.crm.common.model.HyUser;
import com.heye.crm.server.consts.CRight;
import com.heye.crm.server.consts.CRole;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author : lishuming
 */
public class HyAdminSessionResp {
    private static final Logger LOGGER = LoggerFactory.getLogger(HyAdminSessionResp.class);

    private HyUser hyUser;
    private String sessionId;
    private String rightList;
    private int roleId;

    public HyAdminSessionResp() {

    }

    public HyAdminSessionResp(HyUser hyUser, String sessionId, int roleId, String rightList) {
        this.hyUser = hyUser;
        this.sessionId = sessionId;
        this.rightList = rightList;
        this.roleId = roleId;
    }

    public HyUser getHyUser() {
        return hyUser;
    }

    public void setHyUser(HyUser hyUser) {
        this.hyUser = hyUser;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getRightList() {
        return rightList;
    }

    public void setRightList(String rightList) {
        this.rightList = rightList;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public boolean hasRight(CRight cRight) {
        if (cRight.equals(CRight.UNKOWN)) {
            return true;
        }

        try {
            CRole cRole = CRole.getRole(this.getRoleId());
            switch (cRole) {
            case SUPER_ADMIN:
            case PROVINCE_ADMIN:
            case CITY_ADMIN:
            case REGION_ADMIN:
            case STORE_ADMIN:
            case CUSTOMER:
                // TODO: we should define role -> right list detail:
                return true;
            case UNKNOWN:
                LOGGER.info("Use user define right");
            }

            if (StringUtils.isEmpty(this.getRightList())) {
                LOGGER.warn("user defined crights empty!");
                return false;
            }

            String[] arr = this.getRightList().split(",");
            for (String s : arr) {
                Integer sOrder = Integer.parseInt(s);
                switch (CRight.getCRight(sOrder)) {
                case SUPER_RIGHT:
                    return true;
                case UNKOWN:
                    return false;
                default:
                    if (sOrder == cRight.ordinal()) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.warn("has right check failed:", e);
            return false;
        }

        return false;
    }
}
