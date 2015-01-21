package org.jee.framework.core.dao;

import java.io.Serializable;

/**
 * 给实体公用的接口类,所有实体实现此接口
 * 注:
 * 以后用注解代替
 */
public interface IBaseEntity extends Serializable{


	
	/**
	 * 返回映射数据库中的表名
	 * 注: 如果实体名称跟数据库中表名不一样,请重写此方法!
	 * 默认返回实体getSimpleName()
	 * @return getClass().getSimpleName();
	 */
	public abstract String getEntityName();
}