package com.heye.crm.common.model;

import javax.persistence.Column;

/**
 * @author : lishuming
 */
public class HyStoreWithHyUser extends HyStore {
    @Column(name = "user_name")
    private String userName;
    @Column(name = "user_account_name")
    private String userAccountName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAccountName() {
        return userAccountName;
    }

    public void setUserAccountName(String userAccountName) {
        this.userAccountName = userAccountName;
    }
}
