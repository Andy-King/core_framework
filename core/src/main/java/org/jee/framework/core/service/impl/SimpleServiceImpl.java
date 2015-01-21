package org.jee.framework.core.service.impl;

import javax.annotation.Resource;

import org.jee.framework.core.dao.IRepository;
import org.jee.framework.core.service.IBaseService;
import org.springframework.stereotype.Service;

/**
 * 此类用来没有自己写dao,想用简单DAO的情况请继承此类
 * @param <T>
 */
@Service(value="service")
public abstract class SimpleServiceImpl  {

}
