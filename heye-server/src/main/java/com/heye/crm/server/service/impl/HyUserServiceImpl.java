package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyUser;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyUserMapper;
import com.heye.crm.server.service.HyUserService;
import com.heye.crm.server.vo.HyUserVoRight;
import com.heye.crm.server.vo.HyUserVoRole;
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
public class HyUserServiceImpl extends AbstractService<HyUser> implements HyUserService {
    @Resource
    private HyUserMapper hyUserMapper;

    @Override
    public List<HyUser> findAllWith() {
        return hyUserMapper.getUser();
    }

    @Override
    public List<HyUserVoRole> findAllWithCond(Map cond) {
        return hyUserMapper.getUserListWithCond(cond);
    }

    @Override
    public List<HyUserVoRole> findAllWithRole() {
        return hyUserMapper.getUserList();
    }

    @Override
    public List<HyUserVoRole> findWithRoleById(long userId) {
        return hyUserMapper.getUserDetailById(userId);
    }

    @Override
    public List<HyUserVoRight> findWithRightById(long userId) {
        return hyUserMapper.getUserRightById(userId);
    }

    @Override
    public List<HyUserVoRole> findAllWithRoleWithCond(Map cond) {
        return hyUserMapper.getUserListWithCond(cond);
    }
}