package org.jee.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <pre>
    /framework-core/src/main/resources/spring/applicationContext.xml
    {@code

    	<!-- 定义受环境影响易变的变量 -->
    	<!-- style 1: -->
        <bean class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
            <property name="systemPropertiesModeName" value="SYSTEM_PROPERTIES_MODE_OVERRIDE" />
            <property name="ignoreResourceNotFound" value="true" />
            <property name="fileEncoding" value="UTF-8" />
            <property name="locations">
                <list>
                    <!-- 
                    /profile/development.properties -- 开发环境
                    /profile/test.properties        -- 测式环境
                    /profile/production.properties  -- 生活环境
                     -->
                    <value>classpath:/profile/development.properties</value>
                </list>
            </property>
        </bean>
        
        <!-- style 2: -->
        <context:property-placeholder location="classpath*:/profile/development.properties" order="0"/>
     }
 * </pre>
 * 
 * @author AK
 *
 */
@org.springframework.test.context.ContextConfiguration(locations={"classpath:spring/applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
//@ActiveProfiles("development")
//@see /framework-core/src/main/resources/spring/applicationContext.xml
public class AbstractTest{

	@Test
	public void test(){
		System.out.println("------");
	}
	
}

