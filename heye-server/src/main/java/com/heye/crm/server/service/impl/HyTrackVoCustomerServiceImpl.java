package com.heye.crm.server.service.impl;

import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyTrackVoCustomerMapper;
import com.heye.crm.server.service.HyTrackVoCustomerService;
import com.heye.crm.server.vo.HyTrackVoCustomer;
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
public class HyTrackVoCustomerServiceImpl extends AbstractService<HyTrackVoCustomer> implements HyTrackVoCustomerService  {
    @Resource
    private HyTrackVoCustomerMapper hyTrackVoCustomerMapper;

    @Override
    public List<HyTrackVoCustomer> searchTrackList(Map map) {
        return hyTrackVoCustomerMapper.searchTrackList(map);
    }
}
