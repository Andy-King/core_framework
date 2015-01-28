package com.msds.passport.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.jee.framework.web.test.BaseControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

public class UserControllerTest extends BaseControllerTest{
    @Autowired
    private UserController c;
    
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testList() {
		Model model = new ExtendedModelMap();
		String result = c.list(model);
		assertEquals("user/list", result);
	}

	@Test
	public void testCreateForm() {
		fail("Not yet implemented");
	}


}
