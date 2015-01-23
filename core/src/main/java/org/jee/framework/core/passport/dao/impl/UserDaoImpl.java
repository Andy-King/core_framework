package org.jee.framework.core.passport.dao.impl;

import org.jee.framework.core.dao.IBaseDao;
import org.jee.framework.core.dao.impl.BaseDaoImpl;
import org.jee.framework.core.passport.entity.User;
import org.springframework.stereotype.Repository;

@Repository("userDao")
public class UserDaoImpl extends BaseDaoImpl<User, Long> implements IBaseDao<User, Long>{

}
