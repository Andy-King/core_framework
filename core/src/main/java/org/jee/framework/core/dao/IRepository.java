package org.jee.framework.core.dao;

import java.io.Serializable;
import java.util.List;

import org.jee.framework.core.page.Page;

/**
 * <pre>
 * @Class   role :单对象CRUD操作公用接口
 * 注:
 * 1. 此类没有用泛型参数, 给所有Dao用.
 * 2. 表名与实体名一样
 * 
 * PROPAGATION_REQUIRED：支持当前事务，如果当前没有事务，就新建一个事务。这是最常见的选择。<br>
 * PROPAGATION_SUPPORTS：支持当前事务，如果当前没有事务，就以非事务方式执行。<br>
 * PROPAGATION_MANDATORY：支持当前事务，如果当前没有事务，就抛出异常。<br>
 * PROPAGATION_REQUIRES_NEW：新建事务，如果当前存在事务，把当前事务挂起。<br>
 * PROPAGATION_NOT_SUPPORTED：以非事务方式执行操作，如果当前存在事务，就把当前事务挂起。<br>
 * PROPAGATION_NEVER：以非事务方式执行，如果当前存在事务，则抛出异常。<br>
 * PROPAGATION_NESTED：支持当前事务，如果当前事务存在，则执行一个嵌套事务，如果当前没有事务，就新建一个事务。
 * 嵌套事务实现了隔离机制，例如B事务嵌套在A事务中，B失败不会影响A提交。而PROPAGATION_REQUIRED则全部回滚<br>
 *
 * @Package name : com.joysee.framework.core.dao
 * @Project name : DateService
 * @author  name : Andy King
 * @email   addr : wkw11@163.com
 * @Create  time : 2012-5-8 : 上午09:58:38
 * @ver     curr : 1.0
 * </pre>
 */
public interface IRepository<T, PK extends Serializable> {

	// inser/update/delete
	/**
	 * <pre>
	 * @Methods role : 保存多个或一个对象.
	 * 注: 对保存的对象必须实现Serializable接口
	 * 保存save(list.toArray())或save(new Entity())
	 * 
	 * @param <T>
	 * @param entitys
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:58:11
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public void save(T ...entitys);
	
	/**
	 * <pre>
	 * @Methods role : 修改多个或一个对象.
	 * 注: 对保存的对象必须实现Serializable接口
	 * 保存update(list.toArray())或update(new Entity())
	 * 
	 * @param <T>
	 * @param entitys
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:57:39
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public void update(T ...entitys);
	
	/**
	 * <pre>
	 * @Methods role : 合并一个对象.
	 * 注: 对保存的对象必须实现Serializable接口
	 * 保存update(list.toArray())或update(new Entity())
	 * 
	 * @param <T> 实体类
	 * @return the 实体
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:57:39
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public T merge(T entitys);
	
	/**
	 * <pre>
	 * @Methods role : Save or update
	 * 
	 * @param <T> 实体类
	 * @return the 实体
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:57:39
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public void saveOrUpdate(T ...entitys);
	
	/**
	 * <pre>
	 * @Methods role : 批量update/delete.
	 * 
	 * @param queryString the query string
	 * @param value the value
	 * @return the int
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:57:39
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public int batch(String hql, Object... params);	
	
	/**
	 * <pre>
	 * @Methods role : 删除多个或一个对象.
	 * 注: 对删除的对象必须实现Serializable接口
	 * 保存delete(list.toArray())或delete(new Entity())
	 * 
	 * @param <T>
	 * @param entitys
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:57:16
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public void delete(T ...entitys);
	
	/**
	 * <pre>
	 * @Methods role : 删除多个或一个对象.
	 * 注: 对删除的对象必须实现Serializable接口
	 * 
	 * @param <E>
	 * @param ids
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @param <E>
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:37
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public void deleteByIds(PK ...ids);
	
	/**
	 * <pre>
	 * @Methods role : 删除当前实体所有对象.
	 * 
	 * @param Class<T>
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:37
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public void deleteAll(Class<T> cls);
	
	/**
	 * <pre>
	 * @Methods role : 刷新session.
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:37
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public void flush();
	
	/**
	 * <pre>
	 * @Methods role : 清除Session.
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:37
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public void clear();
	
	/**
	 * <pre>
	 * @Methods role : 清除某一对象..
	 * 
	 * @param object 清除某一对象
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:37
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public void evict(Object object);

	
	// query
	/**
	 * <pre>
	 * @Methods role : 根据条件查询一个实体对象
	 * 
	 * @param <T>
	 * @param clazz
	 * @param id
	 * @return
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:09
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public T get(PK id);
	
	/**
	 * <pre>
	 * @Methods role : 根据条件查询一个实体对象
	 * 
	 * @param <T>
	 * @param clazz
	 * @param id
	 * @return
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:09
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public List<T> getList(PK ... ids);	
	
	/**
	 * <pre>
	 * @Methods role : 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return 实体对象
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:09
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public T get(String propertyName, Object value);
	
	/**
	 * <pre>
	 * @Methods role : 根据属性名和属性值获取实体对象.
	 * 
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return 实体对象
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:09
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public List<T> getList(String propertyName, Object value);	
	
	/**
	 * <pre>
	 * @Methods role : 获取所有实体对象集合.
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:56:09
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public List<T> getAll();	
	
	/**
	 * <pre>
	 * @Methods role : 根据条件查询多个实体对象
	 * 
	 * @param <T>
	 * @param hql
	 * @param params
	 * @return
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:55:44
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public List<T> getList(String hql, Object...params);

	/**
	 * <pre>
	 * @Methods role : 根据条件查询多个实体对象
	 * 
	 * 注: 
	 * 	1.由于用hibernate.hql不支持 FROM E LIMIT 1,10这种形式;
	 *	2.由于都用了可参数而引起的冲突所有方法名改成getLimitList
	 * @param <T>
	 * @param queryStatement
	 * @param pageNo
	 * @param pageSize
	 * @param params
	 * @return
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:54:38
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public List<T> getLimitList(String queryStatement, int pageNo, int pageSize, Object...params);

	/**
	 * <pre>
	 * @Methods role : 根据条件查询实体行数
	 * 
	 * @param queryStatement
	 * @param params
	 * @return
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:53:53
	 * @ver     curr : 1.0
	 * </pre>
	 */
	int getRowCount(String queryStatement, Object...params);
	
	/**
	 * <pre>
	 * @Methods role : 根据条件查询唯一的对象
	 * 
	 * @param hql
	 * @param params
	 * @return
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:53:26
	 * @ver     curr : 1.0
	 * </pre>
	 */
	Object getUniqueResult(String hql, Object...params);
	
	/**
	 * <pre>
	 * @Methods role : 根据属性名判断数据是否已存在
	 * 
	 * @param propertyName 属性名称
	 * @param value 值
	 * @return true, if is exist
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:53:26
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public boolean exists(String propertyName, Object value);
	

	/**
	 * <pre>
	 * @Methods role : 根据条件查询分页数据
	 * 
	 * @param <T>
	 * @param page
	 * @param clazz
	 * @param hql
	 * @param params
	 * @return
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:52:20
	 * @ver     curr : 1.0
	 * </pre>
	 */
	public Page<T> getByPage(Page<T> page, String hql, Object...params);
	
	// jdbc base 不推荐使用(用于过渡)//
	/**
	 * <pre>
	 * @Methods role : 根据原生SQL执行INSERT/UPDATE/DELTETE操作(基于JDBC.executeUpdate())
	 * 
	 * 返回影响数据库的行数
	 * 注: 0 < 影响行数 ? true : false
	 * 	1. 如果为0则没有影响的行数,而不会抛出异常
	 * 
	 * @param natvieSQL
	 * @param params
	 * @return
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:49:20
	 * @ver     curr : 1.0
	 * </pre>
	 */
	int execUpdateSQL(String natvieSQL, Object... params);

	//createSQLQuery | execQuerySQL | getDataByQuerySQL
	/**
	 * <pre>
	 * @Methods role : 根据原生SQL和条件查询返回的数据(基于JDBC.executeQuery())
	 * 
	 * @param natvieSQL
	 * @param params
	 * @return
	 * 
	 * @Package name : com.joysee.framework.core.dao
	 * @author  name : Andy King
	 * @email   addr : wkw11@163.com
	 * @Create  time : 2012-5-8 : 上午09:47:30
	 * @ver     curr : 1.0
	 * </pre>
	 */
	List<?> getDataByNatvieSQL(String natvieSQL, Object... params);

	//<T> List<T> execQuerySQL(String natvieSQL, Object... params);
}