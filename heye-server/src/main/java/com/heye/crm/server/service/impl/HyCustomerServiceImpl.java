package com.heye.crm.server.service.impl;

import com.heye.crm.common.model.HyCustomer;
import com.heye.crm.server.core.AbstractService;
import com.heye.crm.server.dao.HyCustomerMapper;
import com.heye.crm.server.dao.factory.MapperFactory;
import com.heye.crm.server.dao.factory.MyBatisUtil;
import com.heye.crm.server.service.HyCustomerService;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author : Zhan Hanbing
 */
@Service
@Transactional
public class HyCustomerServiceImpl extends AbstractService<HyCustomer> implements HyCustomerService {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MapperFactory.class);

    @Override
    public List<HyCustomer> findCustomerByBirth(Date ctmBirthDay) {
        List<HyCustomer> result = null;
        try {
            SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            result = sqlSession.getMapper(HyCustomerMapper.class).getCustomerByBirth(ctmBirthDay);
        } catch (Exception e) {
            LOGGER.warn("getSchedulerCareList failed: {}", e.toString());
        }
        return result;
    }

    @Override
    public List<HyCustomer> findAll() {
        List<HyCustomer> result = null;
        try {
            SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            result = sqlSession.getMapper(HyCustomerMapper.class).getCustomerList();
        } catch (Exception e) {
            LOGGER.warn("getCustomerList failed: {}", e.toString());
        }
        return result;
    }

    @Override
    public List<HyCustomer> findCustomerByRange(int ctmSex, String ageRange, int lowerAge, int upperAge, Date ctmBirthDay) {
        List<HyCustomer> result = null;
        try {
            SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            result = sqlSession.getMapper(HyCustomerMapper.class).getCustomerByCondition(ctmSex, ageRange, lowerAge, upperAge, ctmBirthDay);
        } catch (Exception e) {
            LOGGER.warn("getCustomerList failed: {}", e.toString());
        }
        return result;
    }

    @Override
    public List<HyCustomer> findCustomerByDate(Timestamp startDate, Timestamp endDate) {
        List<HyCustomer> result = null;
        try {
            SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            result = sqlSession.getMapper(HyCustomerMapper.class).getCustomerByDate(startDate, endDate);
        } catch (Exception e) {
            LOGGER.warn("getCustomerList failed: {}", e.toString());
        }
        return result;
    }

    @Override
    public void insertIntoCustomer(HyCustomer hyCustomer) {
        List<HyCustomer> result = null;
        try {
            SqlSession sqlSession = MyBatisUtil.getSqlSessionFactory().openSession();
            sqlSession.getMapper(HyCustomerMapper.class).insertIntoCustomer(hyCustomer);
            sqlSession.commit();
        } catch (Exception e) {
            LOGGER.warn("getCustomerList failed: {}", e.toString());
        }
    }
}
