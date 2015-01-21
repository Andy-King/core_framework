package org.springframework.core.io.support;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;

/**
 * <pre>
    新的属性管理API
    PropertySource：属性源，key-value属性对抽象，比如用于配置数据
    PropertyResolver：属性解析器，用于解析相应key的value
    Environment：环境，本身是一个PropertyResolver，但是提供了Profile特性，即可以根据环境得到相应数据（即激活不同的Profile，可以得到不同的属性数据，比如用于多环境场景的配置（正式机、测试机、开发机DataSource配置））
    Profile：剖面，只有激活的剖面的组件/配置才会注册到Spring容器，类似于maven中profile
     
    也就是说，新的API主要从配置属性、解析属性、不同环境解析不同的属性、激活哪些组件/配置进行注册这几个方面进行了重新设计，使得API的目的更加清晰，而且功能更加强大。
     
    PropertySource
    key-value对，API如下所示：
    public String getName()  //属性源的名字
    public T getSource()        //属性源（比如来自Map，那就是一个Map对象）
    public boolean containsProperty(String name)  //是否包含某个属性
    public abstract Object getProperty(String name)   //得到属性名对应的属性值
 * </pre>
 * 
 * @author AK
 *
 */
public class ResourcePropertySourceTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testResourcePropertySourceStringEncodedResource() {
		fail("Not yet implemented");
	}

	@Test
	public void testResourcePropertySourceEncodedResource() {
		fail("Not yet implemented");
	}

	@Test
	public void testResourcePropertySourceStringResource() {
		Map<String, Object> map = new HashMap<String, Object>();  
	    map.put("encoding", "gbk");  
	    PropertySource<?> propertySource1 = new MapPropertySource("map", map);  
	    System.out.println(propertySource1.getProperty("encoding")); 
	}

	@Test
	public void testResourcePropertySourceResource() throws IOException {
		ResourcePropertySource propertySource = new ResourcePropertySource("resource", "classpath:conf/profile/dev.properties"); //name, location  
	    System.out.println(propertySource.getProperty("hibernate.batch_size")); //15
	    
	    ResourcePropertySource propertySource2 = new ResourcePropertySource("profile", "classpath:conf/system-profile.properties"); //name, location  
	    System.out.println(propertySource2.getProperty("spring.profiles.active")); //dev
	}

	@Test
	public void testResourcePropertySourceStringStringClassLoader() {
		fail("Not yet implemented");
	}

	@Test
	public void testResourcePropertySourceStringClassLoader() {
		fail("Not yet implemented");
	}

	@Test
	public void testResourcePropertySourceStringString() {
		fail("Not yet implemented");
	}

	@Test
	public void testResourcePropertySourceString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetProperty() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetPropertyNames() {
		fail("Not yet implemented");
	}

	@Test
	public void testMapPropertySource() {
		fail("Not yet implemented");
	}

	@Test
	public void testHashCode() {
		fail("Not yet implemented");
	}

	@Test
	public void testEquals() {
		fail("Not yet implemented");
	}

	@Test
	public void testToString() {
		fail("Not yet implemented");
	}

	@Test
	public void testPropertySourceStringT() {
		fail("Not yet implemented");
	}

	@Test
	public void testPropertySourceString() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSource() {
		fail("Not yet implemented");
	}

	@Test
	public void testNamed() {
		fail("Not yet implemented");
	}

}
