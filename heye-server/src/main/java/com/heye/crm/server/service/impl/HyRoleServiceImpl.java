package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyRole;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyRoleMapper;
import com.heye.crm.server.service.HyRoleService;
import com.heye.crm.server.vo.HyRoleRightJoin;
import com.heye.crm.server.vo.HyRoleRightVoRoleVoRight;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : fanjinqian
 */
@Service
@Transactional
public class HyRoleServiceImpl extends AbstractService<HyRole> implements HyRoleService {
    @Resource
    private HyRoleMapper hyRoleMapper;

    @Override
    public List<HyRoleRightVoRoleVoRight> getRoleList() {
        return hyRoleMapper.getRoleList();
    }

    @Override
    public List<HyRoleRightVoRoleVoRight> getRoleListById(long rrId) {
        return hyRoleMapper.getRoleListById(rrId);
    }

    @Override
    public List<HyRoleRightJoin> getRoleRightList() {
        return hyRoleMapper.getRoleRightList();
    }

    @Override
    public List<HyRoleRightJoin> getRoleRightListById(long roleId) {
        return hyRoleMapper.getRoleRightListById(roleId);
    }
}
