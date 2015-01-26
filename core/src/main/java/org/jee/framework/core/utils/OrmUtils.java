package org.jee.framework.core.utils;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.Oracle10gDialect;
import org.hibernate.dialect.PostgresPlusDialect;
import org.hibernate.dialect.SQLServer2008Dialect;

public class OrmUtils {
	/**
	 * Initialize the lazy property value.
	 * <p>
	 * e.g. Hibernates.initLazyProperty(user.getGroups());
	 * <p>
	 * Force initialization of a proxy or persistent collection.
	 * <p/>
	 * Note: This only ensures intialization of a proxy object or collection;
	 * it is not guaranteed that the elements INSIDE the collection will be initialized/materialized.
	 *
	 * @param proxy a persistable object, proxy, persistent collection or <tt>null</tt>
	 * @throws HibernateException if we can't initialize the proxy at this time, eg. the <tt>Session</tt> was closed
	 */
	public static void initLazyProperty(Object proxyedPropertyValue) {
		Hibernate.initialize(proxyedPropertyValue);
	}

	/**
	 * 从DataSoure中取出connection, 根据connection的metadata中的jdbcUrl判断Dialect类型.
	 * 仅支持Oracle, H2, MySql, PostgreSql, SQLServer，如需更多数据库类型，请仿照此类自行编写。
	 */
	public static String getDialect(DataSource dataSource) {
		String jdbcUrl = getJdbcUrlFromDataSource(dataSource);

		// 根据jdbc url判断dialect
		if (StringUtils.contains(jdbcUrl, ":h2:")) {
			return H2Dialect.class.getName();
		} else if (StringUtils.contains(jdbcUrl, ":mysql:")) {
			return MySQL5InnoDBDialect.class.getName();
		} else if (StringUtils.contains(jdbcUrl, ":oracle:")) {
			return Oracle10gDialect.class.getName();
		} else if (StringUtils.contains(jdbcUrl, ":postgresql:")) {
			return PostgresPlusDialect.class.getName();
		} else if (StringUtils.contains(jdbcUrl, ":sqlserver:")) {
			return SQLServer2008Dialect.class.getName();
		} else {
			throw new IllegalArgumentException("Unknown Database of " + jdbcUrl);
		}
	}

	public static String getJdbcUrlFromDataSource(DataSource dataSource) {
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if (connection == null) {
				throw new IllegalStateException("Connection returned by DataSource [" + dataSource + "] was null");
			}
			return connection.getMetaData().getURL();
		} catch (SQLException e) {
			throw new RuntimeException("Could not get database url", e);
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
				}
			}
		}
	}
	
}