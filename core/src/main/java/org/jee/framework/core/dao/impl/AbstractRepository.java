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
import org.jee.framework.core.dao.IRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository("abstractRepository")
public abstract class AbstractRepository<T, PK extends Serializable> {

	
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
	 * 
	 * @author 刘航 2011-3-9
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AbstractRepository() {
		this.entityClass = null;
		Class objClass = getClass();
		Type objType = objClass.getGenericSuperclass();
		if (objType instanceof ParameterizedType) {
			Type[] parameterizedType = ((ParameterizedType) objType).getActualTypeArguments();
			this.entityClass = (Class<T>) parameterizedType[0];
		}
	}

	
	public int batch(String hql, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	
	public void delete(T... entitys) {
		// TODO Auto-generated method stub
		
	}

	
	public void deleteAll(Class<T> cls) {
		// TODO Auto-generated method stub
		
	}

	
	public void deleteByIds(PK... ids) {
		// TODO Auto-generated method stub
		
	}

	
	public void evict(Object object) {
		// TODO Auto-generated method stub
		
	}

	
	public int execUpdateSQL(String natvieSQL, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public boolean exists(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void flush() {
		// TODO Auto-generated method stub
		
	}

	
	public T get(PK id) {
		return hibernateTemplate.get(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	
	public T get(String propertyName, Object value) {
		final StringBuilder objHqlBuffer = new StringBuilder();
		objHqlBuffer.append("from ").append(entityClass.getName()).append(" as model where model.")
				.append(propertyName).append(" = ?");
		
		List<T> objEntityList = hibernateTemplate.find(objHqlBuffer.toString(), value);
		if (0 < objEntityList.size()) {
			return (T) objEntityList.get(0);
		} else {
			return null;
		}
	}

	
	public List<T> getAll() {
		return hibernateTemplate.loadAll(entityClass);
	}

	

	
	public List<?> getDataByNatvieSQL(String natvieSQL, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<T> getLimitList(String queryStatement, int pageNo,
			int pageSize, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<T> getList(PK... ids) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<T> getList(String hql, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<T> getList(String propertyName, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public int getRowCount(String queryStatement, Object... params) {
		// TODO Auto-generated method stub
		return 0;
	}

	
	public Object getUniqueResult(String hql, Object... params) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public T merge(T entitys) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public void save(T... entitys) {
		// TODO Auto-generated method stub
		
	}

	
	public void saveOrUpdate(T... entitys) {
		// TODO Auto-generated method stub
		
	}

	
	public void update(T... entitys) {
		// TODO Auto-generated method stub
		
	}

}	