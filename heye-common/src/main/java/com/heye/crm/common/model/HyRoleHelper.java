package com.heye.crm.common.model;

/**
 * @author : lishuming
 */
public class HyRoleHelper extends HyRole {
    private String rightList;
    private Integer canDelete;

    public String getRightList() {
        return rightList;
    }

    public void setRightList(String rightList) {
        this.rightList = rightList;
    }

    public Integer getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Integer canDelete) {
        this.canDelete = canDelete;
    }
}
