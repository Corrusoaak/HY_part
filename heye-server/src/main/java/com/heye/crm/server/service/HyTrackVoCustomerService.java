package com.heye.crm.server.service;

import com.heye.crm.server.core.Service;
import com.heye.crm.server.vo.HyTrackVoCustomer;

import java.util.List;
import java.util.Map;

/**
 * @author : fanjinqian
 */
public interface HyTrackVoCustomerService extends Service<HyTrackVoCustomer> {
    List<HyTrackVoCustomer> searchTrackList(Map map);
}
