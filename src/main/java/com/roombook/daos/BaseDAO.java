package com.roombook.daos;

import org.hibernate.Session;

import java.util.List;
import java.util.Map;

/**
 * Created by bill on 2016/12/7.
 */
public interface BaseDAO {

    public Object save(Object transientInstance);

    public void delete(Object persistentInstance);

    public void update(Object persistentInstance);

    public <T> T findById(Object id, Class<T> cls);

    public <T> List<T> findByLongIds(String idName, List<Long> id, Class<T> cls);

    public <T> List<T> findByStringIds(String idName, List<String> id, Class<T> cls);

    public <T> List<T> findAll(Class<T> cls);

    public <T> List<T> findByProperty(String propertyName, Object value, Class<T> cls);

    public <T> List<T> findByProperty(String propertyName, Object value, Class<T> cls, int currentPage, int pageSize);

    public <T> List<T> findByProperties(Map<String, Object> propertyNameValueMap, Class<T> cls);

    public <T> List<T> findByProperties(Map<String, Object> propertyNameValueMap, Class<T> cls, int currentPage, int pageSize);

    public List<Map<String, Object>> findBySQLForMap(final String sql, final Object[] values);

    public List<Map<String, Object>> findBySQLForMap(String sql, Object[] values, int currentPage, int pageSize);

    public int executeSQL(final String sql, final Object[] values);

    public int executeSQL(final String sql);

    public List findBySQL(final String sql, final Object[] values);

    public List findByPageForSQL(final String sql, final Object[] values, final int offset, final int pageSize);

    public <T> List<T> findBySQLForVO(final String sql, final Class<T> classes, final Object[] values);

    public <T> List<T> findBySQLForVO(final String sql, final Class<T> classes, final Object[] values, final int offset, final int pageSize);

    public Session getSession();

    public void flush();

    public void clear();

}
