package com.heye.crm.server.dao.factory;

import org.apache.commons.io.IOUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : lishuming
 */
public class DataSourceSqlSessionFactory {
    private static final Logger LOG = LoggerFactory.getLogger(DataSourceSqlSessionFactory.class);

    private static final String CONFIGURATION_PATH = "mybatis-config.xml";
    private static final Map<DataSourceEnvironment, SqlSessionFactory> SQLSESSIONFACTORYS
            = new HashMap<DataSourceEnvironment, SqlSessionFactory>();

    public static SqlSessionFactory getSqlSessionFactory(DataSourceEnvironment environment) {
        String env = environment.getEnv();
        SqlSessionFactory sqlSessionFactory = SQLSESSIONFACTORYS.get(environment);
        if (sqlSessionFactory != null) {
            return sqlSessionFactory;
        } else {
            InputStream inputStream = null;
            try {
                inputStream = Resources.getResourceAsStream(CONFIGURATION_PATH);
                sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream, env);

                LOG.info("Get {} SqlSessionFactory successfully.", env);
            } catch (IOException e) {
                LOG.warn("Get {} SqlSessionFactory error.", env);
                LOG.error(e.getMessage(), e);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }

            SQLSESSIONFACTORYS.put(environment, sqlSessionFactory);
            return sqlSessionFactory;
        }
    }
}
