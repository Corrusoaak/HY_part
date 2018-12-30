package com.heye.crm.server.service.impl;

import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyNaireVoQuestionMapper;
import com.heye.crm.server.service.HyNaireVoQuestionService;
import com.heye.crm.server.vo.HyNaireVoQuestion;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author : Zhanhanbing
 */
@Service
@Transactional
public class HyNaireVoQuestionServiceImpl extends AbstractService<HyNaireVoQuestion> implements HyNaireVoQuestionService {
    @Resource
    HyNaireVoQuestionMapper hyNaireVoQuestionMapper;

    public List<HyNaireVoQuestion> getNaireDetail(Map map) {
        return hyNaireVoQuestionMapper.getNaireDetail(map);
    }
}
