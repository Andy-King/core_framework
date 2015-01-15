package org.jee.framework.core.exception;

/**
 * AssertException
 * 
 * @author AK
 *
 */
public class AssertException extends AbstractException {

	private static final long serialVersionUID = -8387094853056141651L;

	public AssertException() {
		super();
	}

	public AssertException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssertException(String message) {
		super(message);
	}

	public AssertException(Throwable cause) {
		super(cause);
	}
}