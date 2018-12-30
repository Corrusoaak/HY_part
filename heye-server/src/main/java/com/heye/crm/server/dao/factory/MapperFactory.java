package com.heye.crm.server.dao.factory;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author : lishuming, refer: http://www.programering.com/a/MDM3QDNwATM.html
 */
public class MapperFactory {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(MapperFactory.class);

    public static <T> T createMapper(Class<? extends Mapper> clazz, DataSourceEnvironment environment) {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory(environment);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        Mapper mapper = sqlSession.getMapper(clazz);
        return (T) MapperProxy.bind(mapper, sqlSession);
    }

    //Get a SqlSessionFactory of environment
    private static SqlSessionFactory getSqlSessionFactory(DataSourceEnvironment environment) {
        return DataSourceSqlSessionFactory.getSqlSessionFactory(environment);
    }

    /**
     * @autho: lishuming
     */
    private static class MapperProxy implements InvocationHandler {
        private Mapper mapper;
        private SqlSession sqlSession;

        private MapperProxy(Mapper mapper, SqlSession sqlSession) {
            this.mapper = mapper;
            this.sqlSession = sqlSession;
        }

        @SuppressWarnings("unchecked")
        private static Mapper bind(Mapper mapper, SqlSession sqlSession) {
            return (Mapper) Proxy.newProxyInstance(mapper.getClass().getClassLoader(),
                    mapper.getClass().getInterfaces(), new MapperProxy(mapper, sqlSession));
        }

        /**
         * execute mapper method and finally close sqlSession
         */
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object object = null;
            try {
                object = method.invoke(mapper, args);
                sqlSession.commit();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            } finally {
                sqlSession.close();
            }

            return object;
        }
    }
}

