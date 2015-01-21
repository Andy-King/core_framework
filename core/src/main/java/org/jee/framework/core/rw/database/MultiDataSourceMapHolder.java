package org.jee.framework.core.rw.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <pre>
 * @Class   role : 数据源持有者
 * 注: 
 *  可醒多个数据源, 读写分离,(Read, Write) 
 *  业务数据库分离, 如登陆数据库, 订单数据库...
 * 
 * @Package name : com.joysee.framework.core.datasource
 * @Project name : DataInterface
 * @author  name : Andy King
 * @email   addr : wkw11@163.com
 * @Create  time : 2012-5-22 : 下午04:25:19
 * @ver     curr : 1.0
 * </pre>
 */
public class MultiDataSourceMapHolder extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceSwitch.getDataSource();
	}

	
}
