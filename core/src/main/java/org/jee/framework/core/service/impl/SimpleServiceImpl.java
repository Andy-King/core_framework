package org.jee.framework.core.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.jee.framework.core.dao.IBaseDao;
import org.springframework.stereotype.Service;

/**
 * 此类用来没有自己写dao,想用简单DAO的情况请继承此类
 * @param <T>
 */
@Service(value="simpleService")
public class SimpleServiceImpl<T, PK extends Serializable>  extends BaseServiceImpl<T, PK>{

	private IBaseDao<T, PK> baseDao;
	
	@Override
	public IBaseDao<T, PK> getBaseDao() {
		return baseDao;
	}

	@Resource(name="userDao")
	public void setBaseDao(IBaseDao<T, PK> baseDao) {
		this.baseDao = baseDao;
	}

}
