package com.heye.crm.server.dao;

import com.heye.crm.server.core.MybatisMapper;
import com.heye.crm.server.vo.HyAdviseVoCustomer;

import java.util.List;
import java.util.Map;

/**
 * @author : lishuming
 */
public interface HyAdviseVoCustomerMapper extends MybatisMapper<HyAdviseVoCustomer> {
    List<HyAdviseVoCustomer> searchAdviseList(Map map);
}
