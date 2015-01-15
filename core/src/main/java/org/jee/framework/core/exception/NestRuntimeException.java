package org.jee.framework.core.exception;

/**
 * NestRuntimeException
 * 
 * @author AK
 *
 */
public class NestRuntimeException extends AbstractException {

	private static final long serialVersionUID = -8387094853056141651L;

	public NestRuntimeException() {
		super();
	}

	public NestRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NestRuntimeException(String message) {
		super(message);
	}

	public NestRuntimeException(Throwable cause) {
		super(cause);
	}
}