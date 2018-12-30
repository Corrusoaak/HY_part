package com.heye.crm.server.dao;

import com.heye.crm.server.core.MybatisMapper;
import com.heye.crm.server.vo.HyNaireVoQuestion;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author : Zhanhanbing
 */
@Mapper
public interface HyNaireVoQuestionMapper extends MybatisMapper<HyNaireVoQuestion> {
    List<HyNaireVoQuestion> getNaireDetail(Map map);
}
