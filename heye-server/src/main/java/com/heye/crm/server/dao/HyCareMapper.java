package com.heye.crm.server.dao;

import com.heye.crm.common.model.HyCare;
import com.heye.crm.server.core.MybatisMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author : lishuming
 */
@Mapper
public interface HyCareMapper extends MybatisMapper<HyCare> {
    List<HyCare> getSchedulerCareList();

    void updateSendState(int sendState, Long careId);
}
