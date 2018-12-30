package com.heye.crm.server.consts;

/**
 * @author : lishuming
 */
public enum CRight {
    SUPER_RIGHT,
    CUSTOM_MANAGE,
    CUSTOM_ANALYZE,
    CUSTOM_REVISIT,
    PRODUCT_TRACK,
    PRODUCT_ADVISE,
    CUSTOM_CARE,
    UNKOWN;

    public static CRight getCRight(int right) {
        switch (right) {
        case 1:
            return SUPER_RIGHT;
        case 2:
            return CUSTOM_MANAGE;
        case 3:
            return CUSTOM_ANALYZE;
        case 4:
            return CUSTOM_REVISIT;
        case 5:
            return PRODUCT_TRACK;
        case 6:
            return PRODUCT_ADVISE;
        case 7:
            return CUSTOM_CARE;
        default:
            return UNKOWN;
        }
    }
}
