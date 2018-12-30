package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyAdvise;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyAdviseMapper;
import com.heye.crm.server.service.HyAdviseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : lishuming
 */
@Service
@Transactional
public class HyAdviseServiceImpl extends AbstractService<HyAdvise> implements HyAdviseService {
    @Resource
    private HyAdviseMapper hyAdviseMapper;
}
