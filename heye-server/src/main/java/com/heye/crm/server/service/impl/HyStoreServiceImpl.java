package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyStore;
import com.heye.crm.common.model.HyStoreWithHyUser;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyStoreMapper;
import com.heye.crm.server.service.HyStoreService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author : lishuming
 */
@Service
@Transactional
public class HyStoreServiceImpl extends AbstractService<HyStore> implements HyStoreService {
    @Resource
    private HyStoreMapper hyStoreMapper;

    @Override
    public List<HyStoreWithHyUser> getStoreInfoWithHyUser(Map<String, String> cond) {
        return hyStoreMapper.getStoreInfoWithHyUser(cond);
    }
}
