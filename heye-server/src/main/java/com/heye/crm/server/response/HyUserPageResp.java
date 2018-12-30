package com.heye.crm.server.response;

import com.heye.crm.common.model.HyUser;

import java.util.List;

/**
 * @author : lishuming
 */
public class HyUserPageResp {
    List<HyUser> hyUsers;
    Integer totalNum;

    public HyUserPageResp(List<HyUser> hyUsers, Integer totalNum) {
        this.hyUsers = hyUsers;
        this.totalNum = totalNum;
    }

    public List<HyUser> getHyUsers() {
        return hyUsers;
    }

    public void setHyUsers(List<HyUser> hyUsers) {
        this.hyUsers = hyUsers;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
