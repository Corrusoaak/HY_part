package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyRoleRight;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyRoleRightMapper;
import com.heye.crm.server.service.HyRoleRightService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : fanjinqian
 */
@Service
@Transactional
public class HyRoleRightServiceImpl extends AbstractService<HyRoleRight> implements HyRoleRightService {
    @Resource
    private HyRoleRightMapper hyRoleRightMapper;
}
