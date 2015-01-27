package com.msds.passport.service.impl;

import javax.annotation.Resource;

import org.jee.framework.core.dao.IBaseDao;
import org.jee.framework.core.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import com.msds.passport.dao.UserDao;
import com.msds.passport.entity.User;
import com.msds.passport.service.UserService;

@Service(value="userService")
public class UserServiceImpl extends BaseServiceImpl<User, Long> implements UserService {
	
	private UserDao userDao;
	
	@Override
	public IBaseDao<User, Long> getBaseDao() {
		return userDao;
	}

	@Resource(name="userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
