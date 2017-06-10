package com.roombook.daos.impl;

import com.roombook.daos.BaseDAO;
import org.hibernate.*;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.InstantiationException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by bill on 2016/12/7.
 */
@Repository
@Lazy(false)
public class BaseDAOImpl implements BaseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    public Object save(Object transientInstance) {
        Object result = getSession().save(transientInstance);
        getSession().flush();
        return result;
    }

    public void delete(Object persistentInstance) {
        getSession().delete(persistentInstance);
        getSession().flush();
    }

    public void update(Object persistentInstance) {
        getSession().update(persistentInstance);
        getSession().flush();
    }

    public <T> T findById(Object id, Class<T> cls) {
        String tableName = cls.getName();
        T instance = (T) this.getSession().get(tableName, (Serializable) id);
        return instance;
    }

    public <T> List<T> findByStringIds(String idName, List<String> ids, Class<T> cls) {
        if (ids.size() <= 0) return new ArrayList<>();
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where model." + idName + " in (:ids)";
        Query queryObject = this.getSession().createQuery(queryString);
        queryObject.setParameterList("ids", ids);
        return queryObject.list();
    }

    public <T> List<T> findByLongIds(String idName, List<Long> ids, Class<T> cls) {
        if (ids.size() <= 0) return new ArrayList<>();
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where model." + idName + " in (:ids)";
        Query queryObject = this.getSession().createQuery(queryString);
        queryObject.setParameterList("ids", ids);
        return queryObject.list();
    }

    public <T> List<T> findAll(Class<T> cls) {
        String tableName = cls.getName();
        String queryString = "from " + tableName;
        Query queryObject = this.getSession().createQuery(queryString);
        return queryObject.list();
    }

    public <T> List<T> findByProperty(String propertyName, Object value, Class<T> cls) {
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where model." + propertyName + "= ?";
        Query queryObject = this.getSession().createQuery(queryString);
        queryObject.setParameter(0, value);
        return queryObject.list();
    }

    public <T> List<T> findByProperty(String propertyName, Object value, Class<T> cls, int currentPage, int pageSize) {
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where model." + propertyName + "= ?";
        Query queryObject = this.getSession().createQuery(queryString);
        queryObject.setParameter(0, value);
        queryObject.setFirstResult(currentPage * pageSize).setMaxResults(pageSize);
        return queryObject.list();
    }

    public <T> List<T> findByProperties(Map<String, Object> propertyNameValueMap, Class<T> cls) {
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where 1=1";
        for (Map.Entry<String, Object> entry : propertyNameValueMap.entrySet()) {
            queryString += " and model." + entry.getKey() + "= :" + entry.getKey();
        }
        Query queryObject = this.getSession().createQuery(queryString);
        for (Map.Entry<String, Object> entry : propertyNameValueMap.entrySet()) {
            queryObject.setParameter(entry.getKey(), entry.getValue());
        }
        return queryObject.list();
    }

    public <T> List<T> findByProperties(Map<String, Object> propertyNameValueMap, Class<T> cls, int currentPage, int pageSize) {
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where 1=1";
        for (Map.Entry<String, Object> entry : propertyNameValueMap.entrySet()) {
            queryString += " and model." + entry.getKey() + "= :" + entry.getKey();
        }
        Query queryObject = this.getSession().createQuery(queryString);
        for (Map.Entry<String, Object> entry : propertyNameValueMap.entrySet()) {
            queryObject.setParameter(entry.getKey(), entry.getValue());
        }
        queryObject.setFirstResult(currentPage * pageSize).setMaxResults(pageSize);
        return queryObject.list();
    }


    public List<Map<String, Object>> findBySQLForMap(final String sql, final Object[] values) {
        List result = new ArrayList();
        Query queryObject = this.getSession().createSQLQuery(sql);
        queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (values != null) {
            // 为hql语句传入参数
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        List<java.util.Map<String, Object>> list = queryObject.list();
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySQLForMap(String sql, Object[] values, int currentPage, int pageSize) {
        List result = new ArrayList();
        Query queryObject = this.getSession().createSQLQuery(sql);
        queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (values != null) {
            // 为hql语句传入参数
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        if (currentPage >= 0) {
            queryObject.setFirstResult(currentPage * pageSize).setMaxResults(pageSize);
        }
        List<java.util.Map<String, Object>> list = queryObject.list();
        return list;
    }

    /**
     * Function Name: executeSQL
     *
     * @param sql
     * @param values
     * @return description:执行带参数的SQL语句
     * Modification History:
     */
    public int executeSQL(final String sql, final Object[] values) {
        SQLQuery query = this.getSession().createSQLQuery(sql);
        if (values != null) {
            // 为sql语句传入参数
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.executeUpdate();
    }


    /**
     * Function Name: executeSQL
     *
     * @param sql description:执行不带参数SQL或存储过程
     *            Modification History:
     */
    public int executeSQL(final String sql) {
        SQLQuery query = this.getSession().createSQLQuery(sql);
        return query.executeUpdate();
    }


    /**
     * Function Name: findBySQL
     *
     * @param sql
     * @param values
     * @return description:带参数不分页的SQL
     * Modification History:
     */
    public List findBySQL(final String sql, final Object[] values) {
        SQLQuery query = this.getSession().createSQLQuery(sql);
        if (values != null) {
            // 为hql语句传入参数
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.list();
    }

    /**
     * Function Name: findByPageForSQL
     *
     * @param sql
     * @param values   参数值
     * @param offset   起始位置，若查询全部则传入-1
     * @param pageSize 每页记录数
     * @return description: 执行带参数带分页的SQL
     * Modification History:
     */
    public List findByPageForSQL(final String sql, final Object[] values,
                                 final int offset, final int pageSize) {
        SQLQuery query = this.getSession().createSQLQuery(sql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Date)
                    query.setDate(i, (Date) values[i]);
                else
                    query.setParameter(i, values[i]);
            }
        }
        if (offset >= 0) {
            query.setFirstResult(offset).setMaxResults(pageSize);
        }
        return query.list();
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

    /**
     * 通过sql方式获取信息后，封装为指定的VO对象集合
     * Function Name: findBySQLForVO
     *
     * @param sql
     * @param classes 指定VO
     * @param values  传入参数值
     * @return 返回指定VO集合
     */
    public <T> List<T> findBySQLForVO(final String sql, final Class<T> classes, final Object[] values) {
        SQLQuery query = getSession().createSQLQuery(sql);
        if (values != null) {
            // 为hql语句传入参数
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        for (Field f : classes.getDeclaredFields()) {
            if (Pattern.compile("as\\s+" + f.getName() + "[\\s|,]+").matcher(sql).find()) {
                AbstractSingleColumnStandardBasicType type = null;
                try {
                    type = (AbstractSingleColumnStandardBasicType) Class.forName("org.hibernate.type." + f.getType().getSimpleName() + "Type").newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                query.addScalar(f.getName(), type);
            }
        }
        query.setResultTransformer(new AliasToBeanResultTransformer(classes));
        return query.list();
    }

    /**
     * Function Name: findBySQLForVO
     *
     * @param sql
     * @param values   参数值
     * @param offset   起始位置，若查询全部则传入-1
     * @param pageSize 每页记录数
     * @return description: 执行带参数带分页的SQL
     * Modification History:
     */
    public <T> List<T> findBySQLForVO(final String sql, final Class<T> classes, final Object[] values, final int offset, final int pageSize) {
        SQLQuery query = getSession().createSQLQuery(sql);
        if (values != null) {
            // 为sql语句传入参数
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }

        for (Field f : classes.getSuperclass().getDeclaredFields()) {
            if (Pattern.compile("as\\s+" + f.getName() + "[\\s|,]+").matcher(sql).find()) {
                AbstractSingleColumnStandardBasicType type = null;
                try {
                    type = (AbstractSingleColumnStandardBasicType) Class.forName("org.hibernate.type." + f.getType().getSimpleName() + "Type").newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                query.addScalar(f.getName(), type);
            }
        }

        for (Field f : classes.getDeclaredFields()) {
            if (Pattern.compile("as\\s+" + f.getName() + "[\\s|,]+").matcher(sql).find()) {
                AbstractSingleColumnStandardBasicType type = null;
                try {
                    type = (AbstractSingleColumnStandardBasicType) Class.forName("org.hibernate.type." + f.getType().getSimpleName() + "Type").newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                query.addScalar(f.getName(), type);
            }
        }
        query.setResultTransformer(new AliasToBeanResultTransformer(classes));
        if (offset >= 0) {
            query.setFirstResult(offset).setMaxResults(pageSize);
        }
        return query.list();
    }

}
