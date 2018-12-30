package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyRevisitQuestion;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.factory.MapperFactory;
import com.heye.crm.server.service.HyRevisitQuestionService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author : Zhanhanbing
 */
@Service
@Transactional
public class HyRevisitQuestionServiceImpl extends AbstractService<HyRevisitQuestion> implements HyRevisitQuestionService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MapperFactory.class);
}
