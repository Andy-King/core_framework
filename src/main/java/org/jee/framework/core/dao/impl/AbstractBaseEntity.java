package org.jee.framework.core.dao.impl;

import java.io.Serializable;

import org.jee.framework.core.dao.IBaseEntity;


/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 */
public abstract class AbstractBaseEntity implements IBaseEntity{
	private static final long serialVersionUID = 1L;
	
	protected Serializable id;

	public Serializable getId() {
		return id;
	}

	public void setId(Serializable id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.joysee.framework.core.dao.IBaseEntity#getEntityName()
	 */
	public String getEntityName() {
		return getClass().getSimpleName();
	}
	
}
