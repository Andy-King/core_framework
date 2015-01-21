package org.jee.framework.core.service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.hibernate.type.Type;

/**
 * <pre>
 * The Interface IBaseService.
 * 
 * Description:
 * Service 基础接口，含有基本的CRUD操作，所有业务Service接口都应该继承此接口
 * 
 * 
 * Revision History:
 * <who>   <when>   <what>
 * 
 * </pre>
 * 
 * @param <T> the entity
 * @param <PK> the entity's primary key
 */
public interface IBaseService<T, PK extends Serializable> {
	
	/**
	 * <pre>
	 * 根据ID获取实体对象.
	 * </pre>
	 * 
	 * @param id 记录ID
	 * @return 实体对象
	 * 
	 */
	public T getEntity(PK id);
	
	/**
	 * <pre>
	 * 根据ID获取实体对象.
	 * </pre>
	 * 
	 * @param id 记录ID
	 * @return 实体对象
	 * 
	 */
	public T loadEntity(PK id);
	
	/**
	 * <pre>
	 * 根据ID数组获取实体对象集合.
	 * </pre>
	 * 
	 * @param ids ID对象数组
	 * @return ids 实体对象集合
	 * 
	 */
	public List<T> getEntities(PK[] ids);
	
	/**
	 * <pre>
	 * 根据属性名和属性值获取实体对象.
	 * </pre>
	 * 
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return 实体对象
	 * 
	 */
	public T getEntity(String propertyName, Object value);
	
	/**
	 * <pre>
	 * 根据属性名和属性值获取实体对象集合.
	 * </pre>
	 * 
	 * @param propertyName 属性值
	 * @param value 属性值
	 * @return 实体对象集合
	 * 
	 */
	public List<T> getEntities(String propertyName, Object value);
	
	/**
	 * <pre>
	 * 分页查询实体集合.
	 * </pre>
	 * 
	 * @param page 当前页
	 * @param recordes 每页显示记录
	 * @return the entities
	 * 
	 */
	public List<T> getEntities(int page, int recordes);
	
	/**
	 * <pre>
	 * 获取所有实体对象集合.
	 * </pre>
	 * 
	 * @return 实体对象集合
	 * 
	 */
	public List<T> loadAll();
	
	/**
	 * <pre>
	 * 获取所有实体对象总数.
	 * </pre>
	 * 
	 * @return 实体对象总数
	 * 
	 */
	public Long getTotalCount();
	
	/**
	 * <pre>
	 * 根据属性名、修改前后属性值判断在数据库中是否唯一(若新修改的值与原来值相等则直接返回true).
	 * </pre>
	 * 
	 * @param propertyName 属性名称
	 * @param oldValue 修改前的属性值
	 * @param newValue 修改后的属性值
	 * @return boolean
	 * 
	 */
	public boolean isUnique(String propertyName, Object oldValue, Object newValue);
	
	/**
	 * <pre>
	 * 根据属性名判断数据是否已存在.
	 * </pre>
	 * 
	 * @param propertyName 属性名称
	 * @param value 值
	 * @return true, if is exist
	 * 
	 */
	public boolean isExist(String propertyName, Object value);
	
	/**
	 * <pre>
	 * 保存实体对象.
	 * </pre>
	 * 
	 * @param entity 对象
	 * @return ID
	 * 
	 */
	public PK save(T entity);
	
	/**
	 * <pre>
	 * 更新实体对象.
	 * </pre>
	 * 
	 * @param entity 对象
	 * 
	 */
	public void update(T entity);
	
	/**
	 * <pre>
	 * 删除实体对象.
	 * </pre>
	 * 
	 * @param entity 对象
	 * 
	 */
	public void delete(T entity);
	
	/**
	 * <pre>
	 * 根据ID删除实体对象.
	 * </pre>
	 * 
	 * @param id 记录ID
	 * 
	 */
	public void delete(PK id);
	
	/**
	 * <pre>
	 * 根据ID数组删除实体对象.
	 * </pre>
	 * 
	 * @param arrayIds ID数组
	 * 
	 */
	public void delete(PK[] arrayIds);
	
	/**
	 * <pre>
	 * 刷新session.
	 * </pre>
	 * 
	 * 
	 */
	public void flush();
	
	/**
	 * <pre>
	 * 清除Session.
	 * </pre>
	 * 
	 * 
	 */
	public void clear();
	
	/**
	 * <pre>
	 * 清除某一对象.
	 * </pre>
	 * 
	 * @param object 清除某一对象.
	 * 
	 */
	public void evict(Object object);
	
	/**
	 * <pre>
	 * 增加JPA中定义的合并方法，
	 * 此方法当实体之间的关联关系cascade类型设置为CascadeType.MERGE时候适用.
	 * </pre>
	 * 
	 * @param entity 实体类
	 * @return the 实体
	 * 
	 */
	public T merge(T entity);
	
	/**
	 * <pre>
	 * 增加JPA中定义的持久化方法，
	 * 此方法当实体之间的关联关系cascade类型设置为CascadeType.PERSIST时候适用.
	 * </pre>
	 * 
	 * @param entity 实体类
	 * 
	 */
	public void persist(T entity);
	
	/**
	 * <pre>
	 * Save or update 批处理方法.
	 * </pre>
	 * 
	 * @param entities 实体类集合
	 * 
	 */
	public void saveOrUpdateAll(Collection<T> entities);
	
	/**
	 * <pre>
	 * 按指定HQL语句查询并分页.
	 * </pre>
	 * 
	 * @param page the page 当前页
	 * @param recordes the recordes 每页显示记录
	 * @param hql the hql 需要执行的HQL
	 * @return the list
	 * 
	 */
	public List<T> findByCause(int page, int recordes, final String hql);
	
	/**
	 * <pre>
	 * Gets the total by cause.
	 * </pre>
	 * 
	 * @param hql the hql
	 * @return the total by cause
	 * 
	 */
	public long getTotalByCause(final String hql);
	
	/**
	 * <pre>
	 * 执行HQL语句.
	 * </pre>
	 * 
	 * @param queryString the hql query string
	 * @return the list
	 * 
	 */
	public List<T> find(String queryString);
	
	/**
	 * <pre>
	 * 根据条件执行HQL语句.
	 * </pre>
	 * 
	 * @param queryString the hql query string
	 * @param value the value
	 * @return the list
	 * 
	 */
	public List<T> find(String queryString, Object... value);
	
	/**
	 * <pre>
	 * 查询分页.
	 * </pre>
	 * 
	 * @param page the page
	 * @param recordes the recordes
	 * @param queryString the query string
	 * @param value the value
	 * @return the list
	 * 
	 */
	public List<T> find(int page, int recordes, String queryString, Object... value);
	
	/**
	 * <pre>
	 * 批量update/delete.
	 * </pre>
	 * 
	 * @param queryString the query string
	 * @return the int
	 * 
	 */
	public int bulkUpdate(String queryString);
	
	/**
	 * <pre>
	 * 批量update/delete.
	 * </pre>
	 * 
	 * @param queryString the query string
	 * @param value the value
	 * @return the int
	 * 
	 */
	public int bulkUpdate(String queryString, Object... value);
	
	/**
	 * <pre>
	 * Save or update.
	 * </pre>
	 * 
	 * @param entity the entity
	 * 
	 */
	public void saveOrUpdate(T entity);
	
	/**
	 * <pre>
	 * Gets the total by cause.
	 * </pre>
	 * 
	 * @param hql the hql
	 * @param values the values
	 * @return the total by cause
	 * 
	 */
	public long getTotalByCause(final String hql, Object... values);
	
	/**
	 * <pre>
	 * Delete all.
	 * </pre>
	 * 
	 * 
	 */
	public void deleteAll(Class<T> cls);
	
	/**
	 * <pre>
	 * Delete all.
	 * </pre>
	 * 
	 * 
	 */
	public void deleteAll(Collection<T> entities);
	
	/**
	 * <pre>
	 * Find by sql.
	 * </pre>
	 * 
	 * @param sql the sql
	 * @return the list
	 * 
	 */
	public List<Object> findBySql(String sql);
	
	/**
	 * <pre>
	 * Find by sql.
	 * </pre>
	 * 
	 * @param sql the sql
	 * @param objects the objects
	 * @param types the types
	 * @return the list
	 * 
	 */
	public List<Object> findBySql(String sql, Object[] objects, Type[] types);
	
	/**
	 * <pre>
	 * Execute sql.
	 * </pre>
	 * 
	 * @param sql the sql
	 * @return the integer
	 * 
	 */
	public Integer executeSql(String sql);
	
	/**
	 * <pre>
	 * Execute sql.
	 * </pre>
	 * 
	 * @param sql the sql
	 * @param objects the objects
	 * @param types the types
	 * @return the integer
	 * 
	 */
	public Integer executeSql(String sql, Object[] objects, Type[] types);
	
	/**
	 * <pre>
	 * Find by sql.
	 * </pre>
	 * 
	 * @param sql the sql
	 * @param objects the objects
	 * @param types the types
	 * @param cls the cls
	 * @return the list
	 * 
	 */
	public List<T> findBySql(final String sql, final Object[] objects, final org.hibernate.type.Type[] types,
			final Class<T> cls);
	
	/**
	 * <pre>
	 * Find by sql.
	 * </pre>
	 * 
	 * @param page the page
	 * @param recordes the recordes
	 * @param sql the sql
	 * @param objects the objects
	 * @param types the types
	 * @return the list
	 * 
	 */
	public List<Object> findBySql(int page, int recordes, String sql, Object[] objects, Type[] types);
	
	/**
	 * <pre>
	 * Find by sql.
	 * </pre>
	 * 
	 * @param page the page
	 * @param recordes the recordes
	 * @param sql the sql
	 * @param objects the objects
	 * @param types the types
	 * @param cls the cls
	 * @return the list
	 * 
	 */
	public List<T> findBySql(int page, int recordes, String sql, Object[] objects, Type[] types, Class<T> cls);
}