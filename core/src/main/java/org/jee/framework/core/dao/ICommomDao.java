package org.jee.framework.core.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.jee.framework.core.exception.DaoException;

public interface ICommomDao<T> {

	public void delete(Object entity, LockMode lockMode)throws DaoException;
	
	public void delete(String entityName, Object entity, LockMode lockMode) throws DaoException;
	
	public void delete(String entityName, Object entity) throws DaoException;
	
	public void deleteAll(Collection<T> entities) throws DaoException;
	
	public <E> T get(Class<E> entityClass, Serializable id, LockMode lockMode)throws DaoException;
	
	public <E> T load(Class<E> entityClass, Serializable id, LockMode lockMode)throws DaoException;
	
	public Serializable save(String entityName, Object entity)throws DaoException;
	
	public void saveAll(Collection<T> objects)throws DaoException;
	
	public void saveOrUpdate(String entityName, Object entity)throws DaoException;
	
	public void update(Object entity, LockMode lockMode)throws DaoException;
	
	public void update(String entityName, Object entity, LockMode lockMode)throws DaoException;
	
	public List<T> getByProperty(final String propertyName, final Object value)throws DaoException;
	
	public List<T> getByStatement(final String queryStatement, final Object...params) throws DaoException;

}