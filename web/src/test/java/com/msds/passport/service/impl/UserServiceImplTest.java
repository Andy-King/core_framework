package com.msds.passport.service.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jee.framework.core.utils.DigestUtils;
import org.jee.test.AbstractTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.msds.passport.entity.User;
import com.msds.passport.service.UserService;

public class UserServiceImplTest extends AbstractTest{

	private static final String userName = "test03";
	@Resource(name="userService")
	private UserService userService;
	
	private final static Logger LOG = LoggerFactory.getLogger(UserServiceImplTest.class);
	
	@Test
	public void testSave() {
		User user = new User();
		user.setUserName(userName);
		user.setPassword(DigestUtils.md5("123456"));
		user.setEmail("test@163.com");
		Long id = userService.save(user);
		Assert.assertNotNull(id);
	}

	@Test
	public void testQuery(){
		User user = userService.getEntity("userName", userName);
		LOG.info("user:{}", ToStringBuilder.reflectionToString(user));
		Assert.assertEquals(user.getUserName(), userName);
	}
	
	@Test
	public void testUpdate() {
		User user = userService.getEntity("userName", userName);
		user.setEmail("test01@163.com");
		Long id =userService.save(user);
		Assert.assertNotNull(id);
	}

	@Test
	public void testGetUserByParameterizedBeanPropertyRowMapper() {
		User user = userService.getEntity("userName", userName);
		User userx = userService.getUserByParameterizedBeanPropertyRowMapper(user.getId());
		Assert.assertTrue(userx.equals(user));
	}

	@Test
	public void testGetCount() {
		Long count = userService.getCount();
		LOG.info("testGetCountByParameterizedBeanPropertyRowMapper.COUNT:{}", count);
	}

	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * </pre>
	 * @return
	 */
	@Test
	public void  getCountByUserName(){
		long count = userService.getCountByUserName(userName);
		Assert.assertTrue(count > 0);
	}
	
	@Test
	public void testGetUserByBeanPropertyRowMapper() {
		User user = userService.getEntity("userName", userName);
		User userx = userService.getUserByBeanPropertyRowMapper(user.getId());
		Assert.assertTrue(userx.equals(user));
	}

	@Test
	public void testBatchUpdate() {
		// init data
		final User user = userService.getEntity("userName", userName);
		final List<User> users = new ArrayList<User>();
		users.add(user);
		
		// batch execute data
		int[] result = userService.batchUpdate("UPDATE p_user SET version=version+1 WHERE user_name = ? and id = ?", new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, users.get(i).getUserName());
				ps.setLong(2, users.get(i).getId());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return users.size();
			}
		});
		
		
		Assert.assertTrue(result != null);
	}

	@Test
	public void testDeleteT() {
		User user = userService.getEntity("userName", userName);
		userService.delete(user);
		
		user = userService.getEntity("userName", userName);
		Assert.assertTrue(user == null);
	}
}
