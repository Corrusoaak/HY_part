package com.heye.crm.server.dao;

import com.heye.crm.common.model.HyCustomer;
import com.heye.crm.server.core.MybatisMapper;
import org.apache.ibatis.annotations.Mapper;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author : Zhan Hanbing
 */
@Mapper
public interface HyCustomerMapper extends MybatisMapper<HyCustomer> {
    List<HyCustomer> getCustomerByBirth(Date ctmBirthDay);

    List<HyCustomer> getCustomerList();

    List<HyCustomer> getCustomerByCondition(int ctmSex, String ageRange, int lowerAge, int upperAge, Date ctmBirthDay);

    List<HyCustomer> getCustomerByDate(Timestamp startDate, Timestamp endDate);

    void insertIntoCustomer(HyCustomer hyCustomer);
}