package org.jee.framework.core.dao.impl;

import java.io.Serializable;


/**
 * <pre>
 * 当用户
 * UserDao<User> extends IRepository{}
 * UserDaoImpl<User> extends RepositoryImpl<User> implements UserDao{}
 * 注: 
 * 
 * 1. 加入了泛型类User
 * 为了可以实现注入不同的实现:
 * hibernate/jdbc/mybatis
 * 
 * 2. 此类的存在是为了不让继承Dao实现类的类写实现类提供者的私有方法
 * </pre>
 */
public class RepositoryImpl<T, PK extends Serializable> extends AbstractRepository<T, PK> {


}

