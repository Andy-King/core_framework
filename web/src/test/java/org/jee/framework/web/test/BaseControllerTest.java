package org.jee.framework.web.test;


import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.mock.web.MockHttpServletRequest;
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
public class BaseControllerTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

    /**
     * Convenience methods to make tests simpler
     *
     * @param url the URL to post to
     * @return a MockHttpServletRequest with a POST to the specified URL
     */
    public MockHttpServletRequest newPost(String url) {
        return new MockHttpServletRequest("POST", url);
    }

    public MockHttpServletRequest newGet(String url) {
        return new MockHttpServletRequest("GET", url);
    }
}
