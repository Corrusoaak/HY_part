package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyWeixin;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyWeixinMapper;
import com.heye.crm.server.service.HyWeixinService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : lishuming
 */
@Service
@Transactional
public class HyWeixinServiceImpl extends AbstractService<HyWeixin> implements HyWeixinService {
    @Resource
    private HyWeixinMapper hyWeixinMapper;
}
