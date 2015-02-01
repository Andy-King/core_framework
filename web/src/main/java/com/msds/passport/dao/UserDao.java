package com.msds.passport.dao;

import org.jee.framework.core.dao.IBaseDao;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;

import com.msds.passport.entity.User;

public interface UserDao extends IBaseDao<User, Long>{

	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * </pre>
	 * @param id
	 * @return
	 */
	public User getUserByParameterizedBeanPropertyRowMapper(Long id);
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * </pre>
	 * @return
	 */
	public long getCount();
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * </pre>
	 * @return
	 */
	public long getCountByUserName(String userName);
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * </pre>
	 * @param id
	 * @return
	 */
	public User getUserByBeanPropertyRowMapper(Long id);
	
	/**
	 * <pre>
	 * spring JDBC主要的应用基本上都简单罗列一番，所有代码均为举例，不是很严谨，仅为演示每一种用法，抛砖引玉，有问题的请指明问题所在,谢谢
	 * @see <a href="http://www.cnblogs.com/QQParadise/articles/1651825.html">http://www.cnblogs.com/QQParadise/articles/1651825.html</a>
	 * 注意:
	 * <b>1.此类是非线程安全的，必须为每个使用者创建一个实例，或者在同一个线程中使用前调用reset。</b>
	 * 
	 * </pre>
	 * 
	 * <pre>
		// init data
		final User user = userService.getEntity("userName", userName);
		final List<User> users = new ArrayList<User>();
		users.add(user);
		
		// batch execute data
		int[] result = userService.batchUpdate("UPDATE p_user SET version=version+1 WHERE user_name = ? and id = ?", new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int i) throws SQLException {
				// TODO Auto-generated method stub
				ps.setString(1, users.get(i).getUserName());
				ps.setLong(2, users.get(i).getId());
			}
			
			@Override
			public int getBatchSize() {
				// TODO Auto-generated method stub
				return users.size();
			}
		});
	 * </pre>
	 * @param sql
	 * @param pss
	 * @return
	 */
	public int[] batchUpdate(String sql, final BatchPreparedStatementSetter pss);
}
