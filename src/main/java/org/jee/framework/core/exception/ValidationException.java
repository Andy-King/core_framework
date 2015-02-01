package org.jee.framework.core.exception;

/**
 * ValidationException
 * 
 * @author AK
 *
 */
public class ValidationException extends AbstractException {

	private static final long serialVersionUID = -8387094853056141651L;

	public ValidationException() {
		super("数据验证错误!");
	}

	public ValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public ValidationException(String message) {
		super(message);
	}

	public ValidationException(Throwable cause) {
		super(cause);
	}
}