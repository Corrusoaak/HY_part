package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyLocation;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyLocationMapper;
import com.heye.crm.server.service.HyLocationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : lishuming
 */
@Service
@Transactional
public class HyLocationServiceImpl extends AbstractService<HyLocation> implements HyLocationService {
    @Resource
    private HyLocationMapper hyLocationMapper;
}
