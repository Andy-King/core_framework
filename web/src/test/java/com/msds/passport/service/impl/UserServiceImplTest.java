package com.msds.passport.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.jee.framework.core.utils.DigestUtils;
import org.jee.test.AbstractTest;
import org.junit.Assert;
import org.junit.Test;

import com.msds.passport.entity.User;
import com.msds.passport.service.UserService;

public class UserServiceImplTest extends AbstractTest{

	private static final String userName = "test02";
	@Resource(name="userService")
	private UserService userService;
	
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
		ToStringBuilder.reflectionToString(user);
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
	public void testDeleteT() {
		User user = userService.getEntity("userName", userName);
		userService.delete(user);
		
		user = userService.getEntity("userName", userName);
		Assert.assertTrue(user == null);
	}


}
