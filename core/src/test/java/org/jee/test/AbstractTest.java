package org.jee.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@ActiveProfiles("remote") 
@org.springframework.test.context.ContextConfiguration(locations={"classpath*:spring/*.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class AbstractTest{

	@Test
	public void test(){
		System.out.println("------");
	}
}

