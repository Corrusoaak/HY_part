package com.heye.crm.server.service;

import com.heye.crm.server.core.Service;
import com.heye.crm.server.vo.HyAdviseVoCustomer;

import java.util.List;
import java.util.Map;

/**
 * @author : fanjinqian
 */
public interface HyAdviseVoCustomerService extends Service<HyAdviseVoCustomer> {
    List<HyAdviseVoCustomer> searchAdviseList(Map map);
}
