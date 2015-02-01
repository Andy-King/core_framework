package org.jee.framework.core.dao.interceptor;

import java.io.Serializable;

/**
 * <pre>
 * orm 删除拦截器
   {@code

    public class StateDeleteInterceptor<E,ID extends Serializable> implements OrmDeleteInterceptor<E, BasicHibernateDao<E,ID>>{
    
    
    	@Override
    	public boolean onDelete(Serializable id, E entity,BasicHibernateDao<E, ID> persistentContext) {
    		
    		Class<?> entityClass = ReflectionUtils.getTargetClass(entity);
    		StateDelete stateDelete = ReflectionUtils.getAnnotation(entityClass,StateDelete.class);
    		if (stateDelete == null) {
    			return Boolean.TRUE;
    		}
    		
    		Object value = ConvertUtils.convertToObject(stateDelete.value(), stateDelete.type().getValue());
    		ReflectionUtils.invokeSetterMethod(entity, stateDelete.propertyName(), value);
    		persistentContext.update(entity);
    		
    		return Boolean.FALSE;
    	}
    
    
    	@Override
    	public void onPostDelete(Serializable id, E entity,BasicHibernateDao<E, ID> persistentContext) {
    		
    	}
    
    }
    
	public void insert(T entity) {
		
		for (OrmInsertInterceptor<T,BasicHibernateDao<T, ID>> interceptor : insertInterceptors) {
			if (!interceptor.onInsert(entity, this)) {
				return ;
			}
		}
		
		getSession().save(entity);
		
		Serializable id = ReflectionUtils.invokeGetterMethod(entity, getIdName());
		
		for (OrmInsertInterceptor<T,BasicHibernateDao<T, ID>> interceptor : insertInterceptors) {
			interceptor.onPostInsert(entity, this, id);
		}
	}   
   }
 * </pre>
 * 
 * @param <E> 持久化对象类型
 * @param <P> 持久化上下文，如SessionFactory等对象
 * @author Ak
 */
public interface OrmDeleteInterceptor<E,P> {

	/**
	 * 当持久化框架执行删除之前，会触发该方法，当该方法返回false时，将中断执行删除
	 * 
	 * @param id 删除的对象id
	 * @param entity 持久化对象
	 * @param persistentContex 持久化上下文
	 * 
	 * @return boolean
	 */
	public boolean onDelete(Serializable id, E entity,P persistentContext);
	
	/**
	 * 当持久化框架执行删除之后，会触发该方法
	 * 
	 * @param id 删除的对象id
	 * @param entity 持久化对象
	 * @param persistentContex 持久化上下文
	 * 
	 */
	public void onPostDelete(Serializable id, E entity,P persistentContext);
	
}