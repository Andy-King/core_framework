package com.msds.passport.dao.query.sql;

/**
 * <pre>
 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
 * 注：
 *   1. 类名建议用接口名+SqlMapper, 标识final
 *   eg: public final class UserDaoSqlMapper{}
 *   
 *   2. 建议跟当前Dao实现类放在一起
 *   eg: xx.dao.UserDao
 *       xx.dao.impl.UserDao
 *       xx.dao.query.sql.UserDaoSqlMapper
 *       
 *   3. 常量写法，建议用方法名大写(单词之间用下划线隔开)，
 *   eg: public final static String GET_COUNT = "SELECT COUNT(1) FROM p_user";
 * </pre>
 *   
 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
 * @author AK
 *
 */
public final class UserDaoSqlMapper {

	/**
	 * @see com.msds.passport.dao.impl.UserDaoImpl#getUserByParameterizedBeanPropertyRowMapper(Long id)
	 */
	public final static String GET_USER_BY_PARAMETERIZED_BEAN_PROPERTY_ROW_MAPPER = "SELECT * FROM p_user WHERE id = ?";
	
	/**
	 * @see com.msds.passport.dao.impl.UserDaoImpl#getUserByBeanPropertyRowMapper(Long id)
	 */
	public final static String GET_USER_BY_BEAN_PROPERTY_ROW_MAPPER = GET_USER_BY_PARAMETERIZED_BEAN_PROPERTY_ROW_MAPPER;
	
	
	/**
	 * @see com.msds.passport.dao.impl.UserDaoImpl#getCount
	 */
	public final static String GET_COUNT = "SELECT COUNT(1) FROM p_user";
}
