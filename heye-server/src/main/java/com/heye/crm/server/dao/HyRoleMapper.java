package com.heye.crm.server.dao;

import com.heye.crm.common.model.HyRole;
import com.heye.crm.server.core.MybatisMapper;
import com.heye.crm.server.vo.HyRoleRightJoin;
import com.heye.crm.server.vo.HyRoleRightVoRoleVoRight;

import java.util.List;

/**
 * @author : Zhan Hanbing
 */
public interface HyRoleMapper extends MybatisMapper<HyRole> {
    List<HyRoleRightVoRoleVoRight> getRoleList();

    List<HyRoleRightVoRoleVoRight> getRoleListById(long rrId);

    List<HyRoleRightJoin> getRoleRightList();

    List<HyRoleRightJoin> getRoleRightListById(long roleId);
}
