package com.heye.crm.server.service.impl;

import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyAdviseVoCustomerMapper;
import com.heye.crm.server.service.HyAdviseVoCustomerService;
import com.heye.crm.server.vo.HyAdviseVoCustomer;
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
public class HyAdviseVoCustomerServiceImpl extends AbstractService<HyAdviseVoCustomer> implements HyAdviseVoCustomerService {
    @Resource
    private HyAdviseVoCustomerMapper hyAdviseVoCustomerMapper;

    @Override
    public List<HyAdviseVoCustomer> searchAdviseList(Map map) {
        return hyAdviseVoCustomerMapper.searchAdviseList(map);
    }
}
