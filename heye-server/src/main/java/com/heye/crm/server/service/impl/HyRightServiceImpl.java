package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyRight;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyRightMapper;
import com.heye.crm.server.service.HyRightService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : fanjinqian
 */
@Service
@Transactional
public class HyRightServiceImpl extends AbstractService<HyRight> implements HyRightService {
    @Resource
    private HyRightMapper hyRightMapper;
}
