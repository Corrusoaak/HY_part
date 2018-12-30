package com.heye.crm.server.dao;

import com.heye.crm.common.model.HyStore;
import com.heye.crm.common.model.HyStoreWithHyUser;
import com.heye.crm.server.core.MybatisMapper;

import java.util.List;
import java.util.Map;

/**
 * @author : Zhan Hanbing
 */
public interface HyStoreMapper extends MybatisMapper<HyStore> {
    List<HyStoreWithHyUser> getStoreInfoWithHyUser(Map map);
}
