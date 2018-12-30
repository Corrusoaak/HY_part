package com.heye.crm.server.service;

import com.heye.crm.common.model.HyUser;
import com.heye.crm.server.core.Service;
import com.heye.crm.server.vo.HyUserVoRight;
import com.heye.crm.server.vo.HyUserVoRole;

import java.util.List;
import java.util.Map;

/**
 * @author : lishuming
 */
public interface HyUserService extends Service<HyUser> {
    List<HyUser> findAllWith();

    List<HyUserVoRole> findAllWithRole();

    List<HyUserVoRole> findAllWithRoleWithCond(Map cond);

    List<HyUserVoRole> findAllWithCond(Map cond);

    List<HyUserVoRole> findWithRoleById(long userId);

    List<HyUserVoRight> findWithRightById(long userId);
}
