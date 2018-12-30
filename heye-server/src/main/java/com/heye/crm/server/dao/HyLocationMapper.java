package com.heye.crm.server.dao;

import com.heye.crm.common.model.HyLocation;
import com.heye.crm.server.core.MybatisMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author : lishuming
 */
public interface HyLocationMapper extends MybatisMapper<HyLocation> {
    List<String> getProvinceList();

    List<String> getCityList(@Param("locProvince") String locProvince);

    List<String> getLocationList(@Param("locProvince") String locProvince, @Param("locCity") String locCity);
}
