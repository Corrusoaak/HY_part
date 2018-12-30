package com.heye.crm.server.dao;

import com.heye.crm.common.model.HyRevisitAnswer;
import com.heye.crm.server.core.MybatisMapper;
import com.heye.crm.server.vo.HyAnswerCount;
import com.heye.crm.server.vo.HyAnswerVoQuest;

import java.util.List;

/**
 * @author : Zhan Hanbing
 */
public interface HyRevisitAnswerMapper extends MybatisMapper<HyRevisitAnswer> {
    List<HyAnswerVoQuest> findByQuestId(long questId);

    List<HyAnswerCount> countAnswer(long questId);
}
