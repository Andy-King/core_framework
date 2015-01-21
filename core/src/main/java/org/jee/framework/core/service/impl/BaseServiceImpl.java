package org.jee.framework.core.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.hibernate.type.Type;
import org.jee.framework.core.dao.IBaseDao;
import org.jee.framework.core.service.IBaseService;
import org.jee.framework.core.utils.StringUtils;
import org.springframework.stereotype.Component;


/**
 * <pre>
 * The Class BaseServiceImpl.
 * 
 * Description:
 * 基础服务类，默认实现了基础服务接口IBaseService，所有的业务服务类都需要继承此类
 * 
 * Revision History:
 * <who>   <when>   <what>
 * 
 * </pre>
 * 
 * @param <T> the entity
 * @param <PK> the entity's primary key
 * @see IBaseService
 */
@Component
public abstract class BaseServiceImpl<T, PK extends Serializable> implements IBaseService<T, PK> {
	
	/**
	 * <pre>
	 * 获取实际操作的DAO实现类.
	 * </pre>
	 * 
	 * @return the base dao
	 * 
	 */
	public abstract IBaseDao<T, PK> getBaseDao();
	
	@Override
	public T getEntity(PK id) {
		return getBaseDao().get(id);
	}
	
	@Override
	public T loadEntity(PK id) {
		return getBaseDao().load(id);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getEntities(PK[] ids) {
		if (0 == ids.length) {
			return Collections.EMPTY_LIST;
		}
		return getBaseDao().get(ids);
	}
	
	@Override
	public T getEntity(String propertyName, Object value) {
		if (StringUtils.isBlank(propertyName)) {
			return null;
		}
		return getBaseDao().get(propertyName, value);
	}
	
	@Override
	public List<T> getEntities(String propertyName, Object value) {
		if (StringUtils.isBlank(propertyName)) {
			return null;
		}
		return getBaseDao().getEntities(propertyName, value);
	}
	
	@Override
	public List<T> getEntities(int page, int recordes) {
		return getBaseDao().getEntities(page, recordes);
	}
	
	@Override
	public List<T> loadAll() {
		return getBaseDao().loadAll();
	}
	
	@Override
	public Long getTotalCount() {
		return getBaseDao().getTotalCount();
	}
	
	@Override
	public boolean isUnique(String propertyName, Object oldValue, Object newValue) {
		if (StringUtils.isBlank(propertyName)) {
			return false;
		}
		return getBaseDao().isUnique(propertyName, oldValue, newValue);
	}
	
	@Override
	public boolean isExist(String propertyName, Object value) {
		if (StringUtils.isBlank(propertyName)) {
			return false;
		}
		return getBaseDao().isExist(propertyName, value);
	}
	
	@Override
	public PK save(T entity) {
		return (PK) getBaseDao().save(entity);
	}
	
	@Override
	public void update(T entity) {
		getBaseDao().update(entity);
	}
	
	@Override
	public void delete(T entity) {
		getBaseDao().delete(entity);
	}
	
	@Override
	public void delete(PK id) {
		getBaseDao().delete(id);
	}
	
	@Override
	public void delete(PK[] arrayIds) {
		if (0 != arrayIds.length) {
			getBaseDao().delete(arrayIds);
		}
	}
	
	@Override
	public void flush() {
		getBaseDao().flush();
	}
	
	@Override
	public void clear() {
		getBaseDao().clear();
	}
	
	@Override
	public void evict(Object object) {
		getBaseDao().evict(object);
	}
	
	@Override
	public T merge(T entity) {
		return getBaseDao().merge(entity);
	}
	
	@Override
	public void persist(T entity) {
		getBaseDao().persist(entity);
	}
	
	@Override
	public void saveOrUpdateAll(Collection<T> entities) {
		if (null != entities && 0 != entities.size()) {
			getBaseDao().saveOrUpdateAll(entities);
		}
	}
	
	@Override
	public List<T> findByCause(int page, int recordes, String hql) {
		return getBaseDao().findByCause(page, recordes, hql);
	}
	
	@Override
	public long getTotalByCause(String hql) {
		return getBaseDao().getTotalByCause(hql);
	}
	
	@Override
	public List<T> find(String queryString) {
		return getBaseDao().find(queryString);
	}
	
	@Override
	public List<T> find(String queryString, Object... value) {
		return getBaseDao().find(queryString, value);
	}
	
	@Override
	public List<T> find(int page, int recordes, String queryString, Object... value) {
		return getBaseDao().find(page, recordes, queryString, value);
	}
	
	@Override
	public int bulkUpdate(String queryString) {
		return getBaseDao().bulkUpdate(queryString);
	}
	
	@Override
	public int bulkUpdate(String queryString, Object... value) {
		return getBaseDao().bulkUpdate(queryString, value);
	}
	
	@Override
	public void saveOrUpdate(T entity) {
		getBaseDao().saveOrUpdate(entity);
	}
	
	@Override
	public long getTotalByCause(String hql, Object... values) {
		return getBaseDao().getTotalByCause(hql, values);
	}
	
	@Override
	public void deleteAll(Class<T> cls) {
		getBaseDao().deleteAll(cls);
	}
	
	@Override
	public void deleteAll(Collection<T> entities) {
		getBaseDao().deleteAll(entities);
	}
	
	@Override
	public List<Object> findBySql(String sql) {
		return getBaseDao().findBySql(sql);
	}
	
	@Override
	public List<Object> findBySql(String sql, Object[] objects, Type[] types) {
		return getBaseDao().findBySql(sql, objects, types);
	}
	
	@Override
	public Integer executeSql(String sql) {
		return getBaseDao().executeSql(sql);
	}
	
	@Override
	public Integer executeSql(String sql, Object[] objects, Type[] types) {
		return getBaseDao().executeSql(sql, objects, types);
	}
	
	@Override
	public List<T> findBySql(String sql, Object[] objects, Type[] types, Class<T> cls) {
		return getBaseDao().findBySql(sql, objects, types, cls);
	}
	
	@Override
	public List<Object> findBySql(int page, int recordes, String sql, Object[] objects, Type[] types) {
		return getBaseDao().findBySql(page, recordes, sql, objects, types);
	}
	
	@Override
	public List<T> findBySql(int page, int recordes, String sql, Object[] objects, Type[] types, Class<T> cls) {
		return getBaseDao().findBySql(page, recordes, sql, objects, types, cls);
	}
	
}
