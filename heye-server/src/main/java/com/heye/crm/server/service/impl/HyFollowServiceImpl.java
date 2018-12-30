package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyFollow;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyFollowMapper;
import com.heye.crm.server.service.HyFollowService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : lishuming
 */
@Service
@Transactional
public class HyFollowServiceImpl extends AbstractService<HyFollow> implements HyFollowService  {
    @Resource
    private HyFollowMapper hyFollowMapper;
}
