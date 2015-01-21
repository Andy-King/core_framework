package org.jee.framework.core.dao;

import java.util.List;

public interface IBaseJDBC {

	// jdbc base 不推荐使用(用于过渡)
	public abstract int execUpdateSQL(String natvieSQL, Object... params);

	//createSQLQuery
	public abstract List<?> execQuerySQL(String natvieSQL, Object... params);

}