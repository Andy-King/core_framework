package org.jee.framework.core.exception;
/**
 * Controller层公用的Exception.
 * 
 * 继承自RuntimeException
 *
 */
public class ControllerException extends AbstractException{

	private static final long serialVersionUID = -3098503784462416005L;

	public ControllerException() {
		super();
	}

	public ControllerException(String message, Throwable cause) {
		super(message, cause);
	}

	public ControllerException(String message) {
		super(message);
	}

	public ControllerException(Throwable cause) {
		super(cause);
	}

}
