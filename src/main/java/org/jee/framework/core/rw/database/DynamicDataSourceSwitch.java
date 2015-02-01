package org.jee.framework.core.rw.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>
 * @Class   role : 动态数据源切换
 * 
 * @Package name : com.joysee.framework.core.datasource
 * @Project name : DataInterface
 * @author  name : Andy King
 * @email   addr : wkw11@163.com
 * @Create  time : 2012-5-22 : 下午04:55:03
 * @ver     curr : 1.0
 * </pre>
 */
public class DynamicDataSourceSwitch{

	private final static Logger log = LoggerFactory.getLogger(DynamicDataSourceSwitch.class);
	private final static ThreadLocal<String> local = new ThreadLocal<String>();
	
	public static void setDataSource(String key) {
		// TODO Auto-generated method stub
		local.set(key);
		log.debug("Switch to DataSource<><>:{}", key);
	}
	
	public static String getDataSource(){
		String key = local.get();
		log.debug("Return DataSource<><>:{}", key);
		return key;
	}
	
	public static void removeDataSource(){
		local.remove();
	}
}

