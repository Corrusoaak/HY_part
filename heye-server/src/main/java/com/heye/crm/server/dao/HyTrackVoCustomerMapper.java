package com.heye.crm.server.dao;

import com.heye.crm.server.core.MybatisMapper;
import com.heye.crm.server.vo.HyTrackVoCustomer;

import java.util.List;
import java.util.Map;

/**
 * @author : lishuming
 */
public interface HyTrackVoCustomerMapper extends MybatisMapper<HyTrackVoCustomer> {
    List<HyTrackVoCustomer> searchTrackList(Map map);
}
