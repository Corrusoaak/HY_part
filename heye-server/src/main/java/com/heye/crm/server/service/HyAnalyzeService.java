package com.heye.crm.server.service;

import com.heye.crm.common.model.HyAnalyze;
import com.heye.crm.server.core.Service;

import java.util.List;

/**
 * @author : lishuming
 */
public interface HyAnalyzeService extends Service<HyAnalyze> {
    void saveList(List<HyAnalyze> models);
}
