package org.jee.framework.core.rw.database;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * <pre>
 * @Class   role : 数据源持有者
 * 注: 
 *  可醒多个数据源, 读写分离,(Read, Write) 
 *  业务数据库分离, 如登陆数据库, 订单数据库...
 * 
    	&lt;!-- 读数据库 --&gt;	
    	&lt;bean id="Read_DataSource" parent="dataSource" p:jdbcUrl="${jdbc.slave.url}" p:user="${jdbc.slave.username}" p:password="${jdbc.slave.password}"/&gt;
    	&lt;!-- 写数据库 --&gt;
    	&lt;bean id="Write_DataSource" parent="dataSource"/&gt;
        &lt;!-- 多数据库动态切换 --&gt;
        &lt;bean id="multiDataSourceMapHolder" class="com.joysee.framework.core.datasource.MultiDataSourceMapHolder" &gt;
         	&lt;!-- 通过key-value的形式来关联数据源 --&gt;
    		&lt;property name="targetDataSources"&gt;
    			&lt;map key-type="java.lang.String"&gt;
    				&lt;entry key="read_db" value-ref="Read_DataSource"/&gt;&lt;/entry&gt;
    				&lt;entry key="write_db" value-ref="Write_DataSource"/&gt;&lt;/entry&gt;
    			&lt;/map&gt;
    		&lt;/property&gt;
    	&lt;/bean&gt;
 * 	</pre>
 * @Package name : org.jee.framework.core.rw.database.MultiDataSourceMapHolder
 * @Project name : DataInterface
 * @author  name : Andy King
 * @email   addr : wkw11@163.com
 * @Create  time : 2012-5-22 : 下午04:25:19
 * @ver     curr : 1.0
 */
public class MultiDataSourceMapHolder extends AbstractRoutingDataSource{

	@Override
	protected Object determineCurrentLookupKey() {
		return DynamicDataSourceSwitch.getDataSource();
	}

	
}
