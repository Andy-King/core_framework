package org.jee.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@org.springframework.test.context.ContextConfiguration(locations={"classpath:spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@ActiveProfiles("development")
public class AbstractTest{

	@Test
	public void test(){
		System.out.println("------");
	}
	
}

