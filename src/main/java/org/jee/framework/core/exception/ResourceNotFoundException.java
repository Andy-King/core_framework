package org.jee.framework.core.exception;

/**
 * ResourceNotFoundException
 * 
 * @author AK
 *
 */
public class ResourceNotFoundException extends AbstractException {

	private static final long serialVersionUID = -8387094853056141651L;

	public ResourceNotFoundException() {
		super();
	}

	public ResourceNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public ResourceNotFoundException(String message) {
		super(message);
	}

	public ResourceNotFoundException(Throwable cause) {
		super(cause);
	}

}