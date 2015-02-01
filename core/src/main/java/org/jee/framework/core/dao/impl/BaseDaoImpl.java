package org.jee.framework.core.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.jee.framework.core.dao.IBaseDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;


/**
 * 
 * Clean deprecated method. as of Spring 3.2.7, in favor of using a regular execute call with a generic List type declared
 * 
 * <pre>
 * public <T> T execute(HibernateCallback<T> action) throws DataAccessException {
 *     return doExecute(action, false, false);
 * }
 * 
 * @Deprecated
 * public List executeFind(HibernateCallback<?> action) throws DataAccessException {
 *     Object result = doExecute(action, false, false);
 *     if (result != null && !(result instanceof List)) {
 * 	         throw new InvalidDataAccessApiUsageException(
 *                  "Result object returned from HibernateCallback isn't a List: [" + result + "]");
 *     }
 *     return (List) result;
 * }
 * </pre>
 * 
 * @author AK
 * @date Date : 2015-02-01
 * @param <T>
 * @param <PK>
 */
@Repository("baseDao")
public class BaseDaoImpl<T, PK extends Serializable> implements IBaseDao<T, PK>{
	
	// 使用HibernateTemplate
	/** The hibernate template. */
	private HibernateTemplate hibernateTemplate;
	// 泛型中的实例类
	/** The entity class. */
	private Class<T> entityClass;
	
	/** The jdbc template. */
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * 通过构造函数获取泛型中的实体类型.
	 */
	@SuppressWarnings({ "unchecked"})
	public BaseDaoImpl() {
		this.entityClass = null;
		Class<?> objClass = getClass();
		Type objType = objClass.getGenericSuperclass();
		if (objType instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) objType).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param id
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#get(java.io.Serializable)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(PK id) {
		Object object = hibernateTemplate.get(entityClass, id);
		return (T) object;
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param id
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#load(java.io.Serializable)
	 */
	@Override
	public T load(PK id) {
		return (T) hibernateTemplate.load(entityClass, id);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param ids
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#get(PK[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> get(PK[] ids) {
		StringBuilder objHqlBuffer = new StringBuilder();
		objHqlBuffer.append("from ").append(entityClass.getName()).append(" as model where model.id in(:ids)");
		hibernateTemplate.setMaxResults(-1);
		return hibernateTemplate.findByNamedParam(objHqlBuffer.toString(), "ids", ids);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#get(java.lang.String,
	 *      java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T get(String propertyName, Object value) {
		StringBuilder objHqlBuffer = new StringBuilder();
		objHqlBuffer.append("from ").append(entityClass.getName()).append(" as model where model.")
				.append(propertyName).append(" = ?");
		
		List<T> objEntityList = hibernateTemplate.find(objHqlBuffer.toString(), value);
		if (0 < objEntityList.size()) {
			return (T) objEntityList.get(0);
		} else {
			return null;
		}
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#getEntities(java.lang.String,
	 *      java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntities(String propertyName, Object value) {
		StringBuilder objHqlBuffer = new StringBuilder();
		objHqlBuffer.append("from ").append(entityClass.getName()).append(" as model where model.")
				.append(propertyName).append(" = ?");
		
		return hibernateTemplate.find(objHqlBuffer.toString(), value);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param page
	 * @param recordes
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#getEntities(int, int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntities(final int page, final int recordes) {
		final StringBuilder objHqlBuffer = new StringBuilder();
		objHqlBuffer.append("from ").append(entityClass.getName()).append(" as model ");
		List<T> list = hibernateTemplate.execute(new HibernateCallback<List<T>>() {
			
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(objHqlBuffer.toString());
				query.setFirstResult((page - 1) * recordes);
				query.setMaxResults(recordes);
				return query.list();
			}
		});
		return list;
		
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#loadAll()
	 */
	@Override
	public List<T> loadAll() {
		return hibernateTemplate.loadAll(entityClass);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#getTotalCount()
	 */
	@Override
	public Long getTotalCount() {
		StringBuilder objHqlBuffer = new StringBuilder();
		objHqlBuffer.append("select count(*) from ").append(entityClass.getName());
		return (Long) hibernateTemplate.find(objHqlBuffer.toString()).get(0);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param hql
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#getTotalByCause(java.lang.String)
	 */
	@Override
	public long getTotalByCause(String hql) {
		return (Long) hibernateTemplate.find(hql).get(0);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param propertyName
	 * @param oldValue
	 * @param newValue
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#isUnique(java.lang.String,
	 *      java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		if (newValue == oldValue || newValue.equals(oldValue)) {
			return true;
		}
		if (newValue instanceof String) {
			if (oldValue != null && ((String) oldValue).equalsIgnoreCase((String) newValue)) {
				return true;
			}
		}
		T object = get(propertyName, newValue);
		return (object == null);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#isExist(java.lang.String,
	 *      java.lang.Object)
	 */
	@Override
	public boolean isExist(String propertyName, Object value) {
		T object = get(propertyName, value);
		return (object != null);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param entity
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#save(java.lang.Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public PK save(T entity) {
		return (PK) hibernateTemplate.save(entity);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param entity
	 * @see org.jee.framework.core.dao.IBaseDao#update(java.lang.Object)
	 */
	@Override
	public void update(T entity) {
		hibernateTemplate.update(entity);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param entity
	 * @see org.jee.framework.core.dao.IBaseDao#delete(java.lang.Object)
	 */
	@Override
	public void delete(T entity) {
		hibernateTemplate.delete(entity);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param id
	 * @see org.jee.framework.core.dao.IBaseDao#delete(java.io.Serializable)
	 */
	@Override
	public void delete(PK id) {
		T objEntity = load(id);
		delete(objEntity);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param arrayIds
	 * @see org.jee.framework.core.dao.IBaseDao#delete(PK[])
	 */
	@Override
	public void delete(PK[] arrayIds) {
		for (PK id : arrayIds) {
			T objEntity = load(id);
			delete(objEntity);
		}
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param cls
	 * @see org.jee.framework.core.dao.IBaseDao#deleteAll(java.lang.Class)
	 */
	@Override
	public void deleteAll(Class<T> cls) {
		final StringBuilder hql = new StringBuilder();
		hql.append("delete from ").append(cls.getName());
		hibernateTemplate.execute(new HibernateCallback<Integer>() {
			
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createQuery(hql.toString()).executeUpdate();
			}
		});
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param entities
	 * @see org.jee.framework.core.dao.IBaseDao#deleteAll(java.util.Collection)
	 */
	@Override
	public void deleteAll(Collection<T> entities) {
		hibernateTemplate.deleteAll(entities);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @see org.jee.framework.core.dao.IBaseDao#flush()
	 */
	@Override
	public void flush() {
		hibernateTemplate.flush();
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @see org.jee.framework.core.dao.IBaseDao#clear()
	 */
	@Override
	public void clear() {
		hibernateTemplate.clear();
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param object
	 * @see org.jee.framework.core.dao.IBaseDao#evict(java.lang.Object)
	 */
	@Override
	public void evict(Object object) {
		hibernateTemplate.evict(object);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param entity
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#merge(java.lang.Object)
	 */
	@Override
	public T merge(T entity) {
		return hibernateTemplate.merge(entity);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param entity
	 * @see org.jee.framework.core.dao.IBaseDao#persist(java.lang.Object)
	 */
	@Override
	public void persist(T entity) {
		hibernateTemplate.persist(entity);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param entities
	 * @see org.jee.framework.core.dao.IBaseDao#saveOrUpdateAll(java.util.Collection)
	 */
	@Override
	public void saveOrUpdateAll(Collection<T> entities) {
		hibernateTemplate.saveOrUpdateAll(entities);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param page
	 * @param recordes
	 * @param hql
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#findByCause(int, int,
	 *      java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCause(final int page, final int recordes, final String hql) {
		List<T> list = hibernateTemplate.execute(new HibernateCallback<List<T>>() {
			
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(hql);
				query.setFirstResult((page - 1) * recordes);
				query.setMaxResults(recordes);
				return query.list();
			}
		});
		return list;
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param page
	 * @param recordes
	 * @param queryString
	 * @param value
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#find(int, int,
	 *      java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(final int page, final int recordes, final String queryString, final Object... value) {
		List<T> list = hibernateTemplate.execute(new HibernateCallback<List<T>>() {
			
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				query.setFirstResult((page - 1) * recordes);
				query.setMaxResults(recordes);
				for (int i = 0; i < value.length; i++) {
					query.setParameter(i, value[i]);
				}
				return query.list();
			}
		});
		return list;
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param queryString
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#find(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String queryString) {
		// 清楚获取记录数设置
		hibernateTemplate.setMaxResults(-1);
		return hibernateTemplate.find(queryString);
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param queryString
	 * @param value
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#find(java.lang.String,
	 *      java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> find(String queryString, Object... value) {
		hibernateTemplate.setMaxResults(-1);
		return hibernateTemplate.find(queryString, value);
	}

	/** 
	 * <P>
	 * Date : 2013-12-25
	 * </P>
	 * 
	 * @param queryString
	 * @param names
	 * @param values
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<T> find(final String queryString, final String[] names, final Object[] values) {
		return hibernateTemplate.execute(new HibernateCallback<List<T>>() {			
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(queryString);
				query.setMaxResults(-1);
				if (names != null && values != null && names.length == values.length) {
					for (int i = 0; i < values.length; i++) {
						if (values[i] instanceof Collection) {
							query.setParameterList(names[i], (Collection) values[i]);
						} else {
							query.setParameter(names[i], values[i]);
						}
					}
				}
				return query.list();
			}

		});		
	}

	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param queryString
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#bulkUpdate(java.lang.String)
	 */
	@Override
	public int bulkUpdate(String queryString) {
		return hibernateTemplate.bulkUpdate(queryString);
	}
	
	/**

	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param queryString
	 * @param value
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#bulkUpdate(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public int bulkUpdate(String queryString, Object... value) {
		return hibernateTemplate.bulkUpdate(queryString, value);
	}
	
	/**

	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param entity
	 * @see org.jee.framework.core.dao.IBaseDao#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(T entity) {
		hibernateTemplate.saveOrUpdate(entity);
	}
	
	/**

	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param hql
	 * @param values
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#getTotalByCause(java.lang.String,
	 *      java.lang.Object[])
	 */
	@Override
	public long getTotalByCause(String hql, Object... values) {
		return (Long) hibernateTemplate.find(hql, values).get(0);
	}
	
	/**
	 * <pre>
	 * Sets the HibernateTemplate.
	 * </pre>
	 * 
	 * @param hibernateTemplate the new HibernateTemplate
	 * 
	 */
	@Resource(name = "hibernateTemplate")
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	/**
	 * <pre>
	 * Gets the HibernateTemplate.
	 * </pre>
	 * 
	 * @return the HibernateTemplate
	 * 
	 */
	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}
	
	/**
	 * <pre>
	 * Gets the jdbc template.
	 * </pre>
	 * 
	 * @return the jdbc template
	 * 
	 */
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	/**
	 * <pre>
	 * Sets the jdbc template.
	 * </pre>
	 * 
	 * @param jdbcTemplate the jdbc template
	 * 
	 */
	@Resource(name = "jdbcTemplate")
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param sql
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#findBySql(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findBySql(final String sql) {
		return hibernateTemplate.execute(new HibernateCallback<List<Object>>() {
			
			@Override
			public List<Object> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setMaxResults(-1);
				return query.list();
			}
			
		});
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param sql
	 * @param objects
	 * @param types
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#findBySql(java.lang.String,
	 *      java.lang.Object[], org.hibernate.type.Type[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findBySql(final String sql, final Object[] objects, final org.hibernate.type.Type[] types) {
		return hibernateTemplate.execute(new HibernateCallback<List<Object>>() {
			
			@Override
			public List<Object> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setMaxResults(-1);
				query.setParameters(objects, types);
				return query.list();
			}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findBySql(final String sql, final Object[] objects, final org.hibernate.type.Type[] types,
			final Class<T> cls) {
		return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
			
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setMaxResults(-1);
				query.setParameters(objects, types);
				query.addEntity(cls);
				return query.list();
			}
			
		});
	}
	
	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param sql
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#executeSql(java.lang.String)
	 */
	@Override
	public Integer executeSql(final String sql) {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				return query.executeUpdate();
			}
			
		});
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Integer executeUpdate(final String sql, final String[] names, final Object[] values) {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {			
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				Query query = session.createQuery(sql);
				if (names != null && values != null && names.length == values.length) {
					for (int i = 0; i < values.length; i++) {
						if (values[i] instanceof Collection) {
							query.setParameterList(names[i], (Collection) values[i]);
						} else {
							query.setParameter(names[i], values[i]);
						}
					}
				}
				return query.executeUpdate();
			}

		});
	}

	/**
	 * <P>
	 * Date : 2013-3-25
	 * </P>
	 * 
	 * @param sql
	 * @param objects
	 * @param types
	 * @return
	 * @see org.jee.framework.core.dao.IBaseDao#executeSql(java.lang.String,
	 *      java.lang.Object[], org.hibernate.type.Type[])
	 */
	@Override
	public Integer executeSql(final String sql, final Object[] objects, final org.hibernate.type.Type[] types) {
		return hibernateTemplate.execute(new HibernateCallback<Integer>() {
			
			@Override
			public Integer doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameters(objects, types);
				return query.executeUpdate();
			}
			
		});
	}
	
	@Override
	public Object execute(final String sql, final Object[] objects, final org.hibernate.type.Type[] types) {
		return hibernateTemplate.execute(new HibernateCallback<Object>() {
			
			@Override
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setParameters(objects, types);
				return query.list().get(0);
			}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findBySql(final int page, final int recordes, final String sql, final Object[] objects,
			final org.hibernate.type.Type[] types) {
		return hibernateTemplate.execute(new HibernateCallback<List<Object>>() {
			
			@Override
			public List<Object> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setFirstResult((page - 1) * recordes);
				query.setMaxResults(recordes);
				query.setParameters(objects, types);
				return query.list();
			}
			
		});
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findBySql(final int page, final int recordes, final String sql, final Object[] objects,
			final org.hibernate.type.Type[] types, final Class<T> cls) {
		return hibernateTemplate.execute(new HibernateCallback<List<T>>() {
			
			@Override
			public List<T> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setFirstResult((page - 1) * recordes);
				query.setMaxResults(recordes);
				query.setParameters(objects, types);
				query.addEntity(cls);
				return query.list();
			}
			
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object> findBySql(final String sql, final String[] names, final Object[] values) {
		return hibernateTemplate.execute(new HibernateCallback<List<Object>>() {

			@Override
			public List<Object> doInHibernate(Session session) throws HibernateException, SQLException {
				SQLQuery query = session.createSQLQuery(sql);
				query.setMaxResults(-1);
				if (names != null && values != null && names.length == values.length) {
					for (int i = 0; i < values.length; i++) {
						if (values[i] instanceof Collection) {
							query.setParameterList(names[i], (Collection<Object>) values[i]);
						} else {
							query.setParameter(names[i], values[i]);
						}
					}
				}
				return query.list();
			}			
		});
	}
}
