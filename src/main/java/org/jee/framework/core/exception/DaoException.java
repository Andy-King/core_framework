package org.jee.framework.core.exception;

/**
 * Dao层公用的Exception.
 * 
 * 继承自RuntimeException, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 *
 */
public class DaoException extends AbstractException{

	private static final long serialVersionUID = -8011045960612608567L;

	public DaoException() {
		super("数据库访问异常!");
	}

	public DaoException(String message, Throwable cause) {
		super(message, cause);
	}

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

}
