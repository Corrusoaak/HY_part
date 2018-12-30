package com.heye.crm.server.service;

import com.heye.crm.common.model.HyStore;
import com.heye.crm.common.model.HyStoreWithHyUser;
import com.heye.crm.server.core.Service;

import java.util.List;
import java.util.Map;

/**
 * @author : lishuming
 */
public interface HyStoreService extends Service<HyStore> {
    List<HyStoreWithHyUser> getStoreInfoWithHyUser(Map<String, String> cond);
}
