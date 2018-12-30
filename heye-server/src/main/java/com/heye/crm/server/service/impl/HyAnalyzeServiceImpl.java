package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyAnalyze;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyAnalyzeMapper;
import com.heye.crm.server.dao.factory.MyBatisUtil;
import com.heye.crm.server.service.HyAnalyzeService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author : lishuming
 */
@Service
@Transactional
public class HyAnalyzeServiceImpl extends AbstractService<HyAnalyze> implements HyAnalyzeService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(HyAnalyzeServiceImpl.class);

    @Override
    public void saveList(List<HyAnalyze> models) {
        try {
            SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            HyAnalyzeMapper hyAnalyzeMapper = sqlSession.getMapper(HyAnalyzeMapper.class);
            for (HyAnalyze hyAnalyze : models) {
                hyAnalyzeMapper.save(hyAnalyze.getAnaName(), hyAnalyze.getAggLevel(), hyAnalyze.getAnaType(),
                        hyAnalyze.getLabelName(), hyAnalyze.getLabelValue(), hyAnalyze.getCreatedAt());
                sqlSession.commit();
            }
        } catch (Exception e) {
            LOGGER.warn("save failed: {}", e.toString());
        }
    }
}
