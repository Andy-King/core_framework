package org.jee.framework.core.exception;

/**
 * AuthException
 * 
 * @author AK
 *
 */
public class AuthException extends AbstractException {

	private static final long serialVersionUID = -8387094853056141651L;

	public AuthException() {
		super("认证错误");
	}

	public AuthException(String message, Throwable cause) {
		super(message, cause);
	}

	public AuthException(String message) {
		super(message);
	}

	public AuthException(Throwable cause) {
		super(cause);
	}
}