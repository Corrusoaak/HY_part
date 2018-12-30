package com.heye.crm.server.dao;

import com.heye.crm.common.model.HyUser;
import com.heye.crm.server.core.MybatisMapper;
import com.heye.crm.server.vo.HyUserVoRight;
import com.heye.crm.server.vo.HyUserVoRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author : lishuming
 */
public interface HyUserMapper extends MybatisMapper<HyUser> {
    int getUserByIdOrNameOrPhone(@Param("userName") String userName,
                                 @Param("userTelephone") String userTelephone,
                                 @Param("userIdentity") String userIdentity);

    List<HyUser> getUser();

    List<HyUserVoRole> getUserListWithCond(Map cond);

    List<HyUserVoRole> getUserList();

    List<HyUserVoRole> getUserDetailById(long userId);

    List<HyUserVoRight> getUserRightById(long userId);
}
