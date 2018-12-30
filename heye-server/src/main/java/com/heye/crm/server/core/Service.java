package com.heye.crm.server.core;

import org.apache.ibatis.exceptions.TooManyResultsException;
import tk.mybatis.mapper.entity.Condition;

import java.util.List;

/**
 * Service interface.
 *
 * @param <T>
 */
public interface Service<T> {
    void save(T model);

    void save(List<T> models);

    void deleteById(Long id);

    void deleteByIds(String ids);

    void update(T model);

    T findById(Long id);

    T findBy(String fieldName, Object value) throws TooManyResultsException;

    List<T> findByIds(String ids);

    List<T> findByCondition(Condition condition);

    List<T> findAll();
}
