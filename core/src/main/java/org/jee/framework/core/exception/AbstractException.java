package org.jee.framework.core.exception;

import org.jee.framework.core.utils.ExceptionUtils;

public abstract class AbstractException extends RuntimeException{
	
	private static final long serialVersionUID = 5312356254673782470L;

	/**
	 * 避免OSGi环境中调用getMessage()方法出现死锁
	 */
	static{
		ExceptionUtils.class.getName();
	}
	
	public AbstractException() {
		super();
	}

	public AbstractException(String message, Throwable cause) {
		super(message, cause);
	}

	public AbstractException(String message) {
		super(message);
	}

	public AbstractException(Throwable cause) {
		super(cause);
	}
	
	
}
