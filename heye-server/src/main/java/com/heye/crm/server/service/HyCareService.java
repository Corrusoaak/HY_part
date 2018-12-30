package com.heye.crm.server.service;

import com.heye.crm.common.model.HyCare;
import com.heye.crm.server.core.Service;

import java.util.List;

/**
 * @author : fanjinqian
 */
public interface HyCareService extends Service<HyCare> {
    List<HyCare> getSchedulerCareList();

    void updateNotDefault(HyCare model);
}
