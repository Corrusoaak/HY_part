package com.heye.crm.server.service;

import com.heye.crm.common.model.HyRole;
import com.heye.crm.server.core.Service;
import com.heye.crm.server.vo.HyRoleRightJoin;
import com.heye.crm.server.vo.HyRoleRightVoRoleVoRight;

import java.util.List;

/**
 * @author : fanjinqian
 */
public interface HyRoleService extends Service<HyRole> {
    List<HyRoleRightVoRoleVoRight> getRoleList();

    List<HyRoleRightVoRoleVoRight> getRoleListById(long rrId);

    List<HyRoleRightJoin> getRoleRightList();

    List<HyRoleRightJoin> getRoleRightListById(long roleId);
}
