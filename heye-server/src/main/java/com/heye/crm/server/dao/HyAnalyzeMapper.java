package com.heye.crm.server.dao;

import com.heye.crm.common.model.HyAnalyze;
import com.heye.crm.server.core.MybatisMapper;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;

/**
 * @author : lishuming
 */
@Mapper
public interface HyAnalyzeMapper extends MybatisMapper<HyAnalyze> {
    void save(String anaName, Integer aggLevel, Integer anaType,
              String labelName, Integer labelValue, Timestamp createdAt);
}
