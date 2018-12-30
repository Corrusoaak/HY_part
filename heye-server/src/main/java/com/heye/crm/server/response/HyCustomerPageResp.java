package com.heye.crm.server.response;

import com.heye.crm.common.model.HyCustomer;

import java.util.List;

/**
 * @author : fanjinqian
 */
public class HyCustomerPageResp {
    private List<HyCustomer> hyCustomers;
    private Integer totalNum;

    public HyCustomerPageResp(List<HyCustomer> hyCustomers, Integer totalNum) {
        this.hyCustomers = hyCustomers;
        this.totalNum = totalNum;
    }

    public List<HyCustomer> getHyCustomers() {
        return hyCustomers;
    }

    public void setHyCustomers(List<HyCustomer> hyCustomers) {
        this.hyCustomers = hyCustomers;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }
}
