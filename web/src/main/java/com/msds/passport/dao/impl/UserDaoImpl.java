package com.msds.passport.dao.impl;

import org.jee.framework.core.dao.impl.BaseDaoImpl;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.msds.passport.dao.UserDao;
import com.msds.passport.dao.query.sql.UserDaoSqlMapper;
import com.msds.passport.entity.User;

/**
 * <pre>
 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
 * </pre>
 * 
 * @author AK
 *
 */
@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements UserDao{

	
	/**
	 * @see com.msds.passport.dao.impl.UserDaoImpl#getUserByParameterizedBeanPropertyRowMapper(Long id)
	 */
	public final static String GET_USER_BY_PARAMETERIZED_BEAN_PROPERTY_ROW_MAPPER = "SELECT * FROM p_user WHERE id = ?";
	
	public User getUserByParameterizedBeanPropertyRowMapper(Long id) {
		User user = getJdbcTemplate().queryForObject(/*Sql mapper style 1*/UserDaoSqlMapper.GET_USER_BY_PARAMETERIZED_BEAN_PROPERTY_ROW_MAPPER , new Object[]{id},  ParameterizedBeanPropertyRowMapper.newInstance(User.class));
		return user;
	}
	
	/**
	 * @see com.msds.passport.dao.impl.UserDaoImpl#getUserByBeanPropertyRowMapper(Long id)
	 */
	public final static String GET_USER_BY_BEAN_PROPERTY_ROW_MAPPER = GET_USER_BY_PARAMETERIZED_BEAN_PROPERTY_ROW_MAPPER;
	
	/*
	 * Spring 2.5 提供了一个便利的RowMapper实现-----BeanPropertyRowMapper
	 * 它可自动将一行数据映射到指定类的实例中 它首先将这个类实例化，然后通过名称匹配的方式，映射到属性中去。
	 * 例如：属性名称（vehicleNo）匹配到同名列或带下划线的同名列（VEHICLE_NO）。
	 * 如果某个属性不匹配则返回属性值为Null；
	 *  (non-Javadoc)
	 * @see com.msds.passport.dao.UserDao#getUserByBeanPropertyRowMapper(java.lang.Long)
	 */
	public User getUserByBeanPropertyRowMapper(Long id) {
		User user = (User)getJdbcTemplate().queryForObject(/*Sql mapper style 2*/GET_USER_BY_BEAN_PROPERTY_ROW_MAPPER , new Object[]{id}, ParameterizedBeanPropertyRowMapper.newInstance(User.class));
		return user;//user;
	}
	
	/**
	 * @see com.msds.passport.dao.impl.UserDaoImpl#getCountByParameterizedBeanPropertyRowMapper
	 */
	public final static String GET_COUNT = "SELECT COUNT(1) FROM p_user";
	
	public long getCount(){
		long count = getJdbcTemplate().queryForObject(/*Sql mapper style 2*/GET_COUNT, Long.class);
		return count;
	}
	
	/**
	 * @see com.msds.passport.dao.impl.UserDaoImpl#getCountByParameterizedBeanPropertyRowMapper
	 */
	public final static String GET_COUNT_BY_USER_NAME = "SELECT COUNT(1) FROM p_user WHERE user_name = :user_name";
	public long getCountByUserName(String userName){
		/* JdbcTemplate、NamedParameterJdbcTemplate、SimpleJdbcTemplate
		 * SqlParameterSource有两个实现，一个是MapSqlParameterSource，另一个是BeanPropertySqlParameterSource。
		 */
		NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(getJdbcTemplate().getDataSource());
		SqlParameterSource namedParameters = new MapSqlParameterSource("user_name", userName);
		long count = namedParameterJdbcTemplate.queryForObject(/*Sql mapper style 2*/GET_COUNT_BY_USER_NAME, namedParameters, Long.class);
		return count;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see com.msds.passport.dao.UserDao#batchUpdate(java.lang.String, org.springframework.jdbc.core.BatchPreparedStatementSetter)
	 */
	public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss){
		return getJdbcTemplate().batchUpdate(sql, pss );
	}
}
