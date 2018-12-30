package com.heye.crm.server.service;

import com.heye.crm.server.core.Service;
import com.heye.crm.server.vo.HyNaireVoQuestion;
import java.util.List;
import java.util.Map;

/**
 * @author : Zhanhanbing
 */
public interface HyNaireVoQuestionService extends Service<HyNaireVoQuestion> {
    List<HyNaireVoQuestion> getNaireDetail(Map map);
}
