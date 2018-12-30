package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyRevisitAnswer;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyRevisitAnswerMapper;
import com.heye.crm.server.service.HyRevisitAnswerService;
import com.heye.crm.server.vo.HyAnswerCount;
import com.heye.crm.server.vo.HyAnswerVoQuest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : lishuming
 */
@Service
@Transactional
public class HyRevisitAnswerServiceImpl extends AbstractService<HyRevisitAnswer> implements HyRevisitAnswerService {
    @Resource
    private HyRevisitAnswerMapper hyRevisitAnswerMapper;

    @Override
    public List<HyAnswerVoQuest> findByQuestId(long questId) {
        return hyRevisitAnswerMapper.findByQuestId(questId);
    }

    @Override
    public List<HyAnswerCount> countAnswer(long questId) {
        return hyRevisitAnswerMapper.countAnswer(questId);
    }
}
