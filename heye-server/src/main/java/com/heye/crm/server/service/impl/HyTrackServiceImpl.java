package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyTrack;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyTrackMapper;
import com.heye.crm.server.service.HyTrackService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author : lishuming
 */
@Service
@Transactional
public class HyTrackServiceImpl extends AbstractService<HyTrack> implements HyTrackService  {
    @Resource
    private HyTrackMapper hyTrackMapper;
}
