package com.msds.passport.service;

import org.jee.framework.core.service.IBaseService;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.msds.passport.entity.User;

public interface UserService  extends IBaseService<User, Long>{

	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * </pre>
	 * @param id
	 * @return
	 */
	public User getUserByParameterizedBeanPropertyRowMapper(Long id);
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * </pre>
	 * @return
	 */
	public long getCount();
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * </pre>
	 * @return
	 */
	public long getCountByUserName(String userName);
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * </pre>
	 * @param id
	 * @return
	 */
	public User getUserByBeanPropertyRowMapper(Long id);
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * </pre>
	 * @param sql
	 * @param pss
	 * @return
	 */
	public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss);
}
