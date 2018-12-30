package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyCare;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyCareMapper;
import com.heye.crm.server.dao.factory.MapperFactory;
import com.heye.crm.server.dao.factory.MyBatisUtil;
import com.heye.crm.server.service.HyCareService;
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
public class HyCareServiceImpl extends AbstractService<HyCare> implements HyCareService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MapperFactory.class);

    public List<HyCare> getSchedulerCareList() {
        List<HyCare> result = null;

        try {
            SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            result = sqlSession.getMapper(HyCareMapper.class).getSchedulerCareList();
        } catch (Exception e) {
            LOGGER.warn("getSchedulerCareList failed: {}", e.toString());
        }

        return result;
    }

    public void updateNotDefault(HyCare model) {
        try {
            SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            sqlSession.getMapper(HyCareMapper.class).updateSendState(model.getSendState(), model.getCareId());
            sqlSession.commit();
        } catch (Exception e) {
            LOGGER.warn("Save HyCare failed: {}", e.toString());
        }
    }
}
