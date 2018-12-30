package com.heye.crm.common.consts;

/**
 * @author : lishuming
 */
public enum HyAnalyzeType {
    HY_ANALYZE_TYPE_SEX_NEWLY(1),
    HY_ANALYZE_TYPE_SEX_SUM(2),
    HY_ANALYZE_TYPE_REGION_NEWLY(3),
    HY_ANALYZE_TYPE_REGION_SUM(4),
    HY_ANALYZE_TYPE_CITY_NEWLY(5),
    HY_ANALYZE_TYPE_CITY_SUM(6),
    HY_ANALYZE_TYPE_PROVINCE_NEWLY(7),
    HY_ANALYZE_TYPE_PROVINCE_SUM(8),
    HY_ANALYZE_TYPE_AGE_NEWLY(9),
    HY_ANALYZE_TYPE_AGE_SUM(10),
    HY_ANALYZE_TYPE_PRODUCT_NEWLY(11),
    HY_ANALYZE_TYPE_PRODUCT_SUM(12);

    private Integer type;

    HyAnalyzeType(Integer type) {
        this.type = type;
    }

    public static Boolean isNewlyType(HyAnalyzeType t) {
        Integer[] newlyTypes = new Integer[]{1, 3, 5, 7, 9, 11};
        for (Integer newlyType: newlyTypes) {
            if (newlyType == t.type) {
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
