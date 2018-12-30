package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyProduct;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyProductMapper;
import com.heye.crm.server.service.HyProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : lishuming
 */
@Service
@Transactional
public class HyProductServiceImpl extends AbstractService<HyProduct> implements HyProductService {
    @Resource
    private HyProductMapper hyProductMapper;
}
