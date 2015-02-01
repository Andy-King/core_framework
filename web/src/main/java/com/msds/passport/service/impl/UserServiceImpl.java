package com.msds.passport.service.impl;

import javax.annotation.Resource;

import org.jee.framework.core.dao.IBaseDao;
import org.jee.framework.core.service.impl.BaseServiceImpl;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
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

	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * </pre>
	 * 
	 * @param id
	 * @return
	 */
	public User getUserByParameterizedBeanPropertyRowMapper(Long id){
		return userDao.getUserByParameterizedBeanPropertyRowMapper(id);
	}
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * </pre>
	 * 
	 * @return
	 */
	public long getCount(){
		return userDao.getCount();
	}
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * </pre>
	 * 
	 * @return
	 */
	public long getCountByUserName(String userName){
		return userDao.getCountByUserName(userName);
	}
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * </pre>
	 * 
	 * @param id
	 * @return
	 */
	public User getUserByBeanPropertyRowMapper(Long id){
		return userDao.getUserByBeanPropertyRowMapper(id);
	}
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * </pre>
	 * 
	 * @param sql
	 * @param pss
	 * @return
	 */
	public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss){
		return userDao.batchUpdate(sql, pss);
	}
}
