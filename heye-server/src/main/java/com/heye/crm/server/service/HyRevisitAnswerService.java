package com.heye.crm.server.service;

import com.heye.crm.common.model.HyRevisitAnswer;
import com.heye.crm.server.core.Service;
import com.heye.crm.server.vo.HyAnswerCount;
import com.heye.crm.server.vo.HyAnswerVoQuest;

import java.util.List;

/**
 * @author : fanjinqian
 */
public interface HyRevisitAnswerService extends Service<HyRevisitAnswer> {
    List<HyAnswerVoQuest> findByQuestId(long questId);

    List<HyAnswerCount> countAnswer(long questId);
}
