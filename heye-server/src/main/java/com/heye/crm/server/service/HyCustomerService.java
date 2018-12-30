package com.heye.crm.server.service;

import com.heye.crm.common.model.HyCustomer;
import com.heye.crm.server.core.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author : lishuming
 */
public interface HyCustomerService extends Service<HyCustomer> {
    List<HyCustomer> findCustomerByBirth(Date ctmBirthDay);

    List<HyCustomer> findAll();

    List<HyCustomer> findCustomerByRange(int ctmSex, String ageRange, int lowerAge, int upperAge, Date ctmBirthDay);

    List<HyCustomer> findCustomerByDate(Timestamp startDate, Timestamp endDate);

    void insertIntoCustomer(HyCustomer hyCustomer);
}
