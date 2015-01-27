package com.msds.passport.dao.impl;

import org.jee.framework.core.dao.impl.BaseDaoImpl;
import org.springframework.stereotype.Repository;

import com.msds.passport.dao.UserDao;
import com.msds.passport.entity.User;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao{

}
