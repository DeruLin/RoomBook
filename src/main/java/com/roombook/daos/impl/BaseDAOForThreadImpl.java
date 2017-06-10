package com.roombook.daos.impl;

import com.roombook.daos.BaseDAOForThread;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by deru on 2017/5/14.
 */
@Repository
@Lazy(false)
public class BaseDAOForThreadImpl implements BaseDAOForThread {

    @Autowired
    private SessionFactory sessionFactory;


    public Object save(Object transientInstance) {
        Session session = sessionFactory.openSession();
        Object result = session.save(transientInstance);
        session.flush();
        session.close();
        return result;
    }

    public void delete(Object persistentInstance) {
        Session session = sessionFactory.openSession();
        session.delete(persistentInstance);
        session.flush();
        session.close();
    }

    public void update(Object persistentInstance) {
        Session session = sessionFactory.openSession();
        session.update(persistentInstance);
        session.flush();
    }

    public <T> T findById(Object id, Class<T> cls) {
        Session session = sessionFactory.openSession();
        String tableName = cls.getName();
        T instance = (T) session.get(tableName, (Serializable) id);
        session.close();
        return instance;
    }

    public <T> List<T> findByStringIds(String idName, List<String> ids, Class<T> cls) {
        Session session = sessionFactory.openSession();
        if (ids.size() <= 0) return new ArrayList<>();
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where model." + idName + " in (:ids)";
        Query queryObject = session.createQuery(queryString);
        queryObject.setParameterList("ids", ids);
        List<T> result=queryObject.list();
        session.close();
        return result;
    }

    public <T> List<T> findByLongIds(String idName, List<Long> ids, Class<T> cls) {
        Session session = sessionFactory.openSession();
        if (ids.size() <= 0) return new ArrayList<>();
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where model." + idName + " in (:ids)";
        Query queryObject = session.createQuery(queryString);
        queryObject.setParameterList("ids", ids);
        List<T> result=queryObject.list();
        session.close();
        return result;
    }

    public <T> List<T> findAll(Class<T> cls) {
        Session session = sessionFactory.openSession();
        String tableName = cls.getName();
        String queryString = "from " + tableName;
        Query queryObject = session.createQuery(queryString);
        List<T> result=queryObject.list();
        session.close();
        return result;
    }

    public <T> List<T> findByProperty(String propertyName, Object value, Class<T> cls) {
        Session session = sessionFactory.openSession();
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where model." + propertyName + "= ?";
        Query queryObject = session.createQuery(queryString);
        queryObject.setParameter(0, value);
        List<T> result=queryObject.list();
        session.close();
        return result;
    }

    public <T> List<T> findByProperty(String propertyName, Object value, Class<T> cls, int currentPage, int pageSize) {
        Session session = sessionFactory.openSession();
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where model." + propertyName + "= ?";
        Query queryObject = session.createQuery(queryString);
        queryObject.setParameter(0, value);
        queryObject.setFirstResult(currentPage * pageSize).setMaxResults(pageSize);
        List<T> result=queryObject.list();
        session.close();
        return result;
    }

    public <T> List<T> findByProperties(Map<String, Object> propertyNameValueMap, Class<T> cls) {
        Session session = sessionFactory.openSession();
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where 1=1";
        for (Map.Entry<String, Object> entry : propertyNameValueMap.entrySet()) {
            queryString += " and model." + entry.getKey() + "= :" + entry.getKey();
        }
        Query queryObject = session.createQuery(queryString);
        for (Map.Entry<String, Object> entry : propertyNameValueMap.entrySet()) {
            queryObject.setParameter(entry.getKey(), entry.getValue());
        }
        List<T> result=queryObject.list();
        session.close();
        return result;
    }

    public <T> List<T> findByProperties(Map<String, Object> propertyNameValueMap, Class<T> cls, int currentPage, int pageSize) {
        Session session = sessionFactory.openSession();
        String tableName = cls.getName();
        String queryString = "from " + tableName + " as model where 1=1";
        for (Map.Entry<String, Object> entry : propertyNameValueMap.entrySet()) {
            queryString += " and model." + entry.getKey() + "= :" + entry.getKey();
        }
        Query queryObject = session.createQuery(queryString);
        for (Map.Entry<String, Object> entry : propertyNameValueMap.entrySet()) {
            queryObject.setParameter(entry.getKey(), entry.getValue());
        }
        queryObject.setFirstResult(currentPage * pageSize).setMaxResults(pageSize);
        List<T> result=queryObject.list();
        session.close();
        return result;
    }


    public List<Map<String, Object>> findBySQLForMap(final String sql, final Object[] values) {
        Session session = sessionFactory.openSession();
        List result = new ArrayList();
        Query queryObject = session.createSQLQuery(sql);
        queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (values != null) {
            // 为hql语句传入参数
            for (int i = 0; i < values.length; i++) {
                queryObject.setParameter(i, values[i]);
            }
        }
        List<java.util.Map<String, Object>> list = queryObject.list();
        session.close();
        return list;
    }

    @Override
    public List<Map<String, Object>> findBySQLForMap(String sql, Object[] values, int currentPage, int pageSize) {
        Session session = sessionFactory.openSession();
        List result = new ArrayList();
        Query queryObject = session.createSQLQuery(sql);
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
        session.close();
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
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(sql);
        if (values != null) {
            // 为sql语句传入参数
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        int result=query.executeUpdate();
        session.close();
        return result;
    }


    /**
     * Function Name: executeSQL
     *
     * @param sql description:执行不带参数SQL或存储过程
     *            Modification History:
     */
    public int executeSQL(final String sql) {
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(sql);
        int result=query.executeUpdate();
        session.close();
        return result;
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
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(sql);
        if (values != null) {
            // 为hql语句传入参数
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        List result=query.list();
        session.close();
        return result;
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
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(sql);
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
        List result=query.list();
        session.close();
        return result;
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
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(sql);
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
        List<T> result=query.list();
        session.close();
        return result;
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
        Session session = sessionFactory.openSession();
        SQLQuery query = session.createSQLQuery(sql);
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
        List<T> result=query.list();
        session.close();
        return result;
    }
}
