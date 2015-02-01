package org.jee.framework.core.utils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;


/**
 * <pre>
 * 异常的工具类.
	public static byte[] generateAesKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(keysize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			<b>throw ExceptionUtils.unchecked(e);</b>
		}
	}
 * <pre>
 * @author AK
 */
public abstract class ExceptionUtils {
	
	/**
	 * 将CheckedException转换为UncheckedException.
	 * @param message
	 * @return
	 */
	public static RuntimeException unchecked(String message){
		return new RuntimeException(message);
	}
	
	/**
	 * 将CheckedException转换为UncheckedException.
	 * @param message
	 * @return
	 */
	public static RuntimeException unchecked(Throwable e){
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}
	
	/**
	 * 将CheckedException转换为UncheckedException.
	 * Throw RuntimeException
	 * @param message
	 * @param cause
	 * @return
	 */
	public static RuntimeException unchecked(String message, Throwable e){
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(message, e);
		}
	}

	/**
	 * 将ErrorStack转化为String.
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex;
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
	
	/**
	 * 为基本信息和根异常绑定消息
	 * @param message
	 * @param cause
	 * @return message
	 */
	public static String buildMessage(String message, Throwable cause) {
		if (cause != null) {
			StringBuilder sb = new StringBuilder();
			if (message != null) {
				sb.append(message).append("; ");
			}
			sb.append("Cause is :").append(cause);
			return sb.toString();
		}
		else {
			return message;
		}
	}

	/**
	 * 判断是否为强制查检类型异常
	 * @param ex
	 * @return
	 */
	public static boolean isCheckedException(Throwable ex) {
		return !(ex instanceof RuntimeException || ex instanceof Error);
	}

	/**
	 * 检查是否给定的例外是Throwable兼容的异常类型
	 * @param ex the exception to checked
	 * @param declaredExceptions the exceptions declared in the throws clause
	 * @return whether the given exception is compatible
	 */
	public static boolean isCompatibleWithThrowsClause(Throwable ex, Class<?>[] declaredExceptions) {
		if (!isCheckedException(ex)) {
			return true;
		}
		if (declaredExceptions != null) {
			int i = 0;
			while (i < declaredExceptions.length) {
				if (declaredExceptions[i].isAssignableFrom(ex.getClass())) {
					return true;
				}
				i++;
			}
		}
		return false;
	}

	/**
	 * Throw RuntimeException
	 * @param message
	 * @return
	 */
	public static void throwRuntimeException(String message){
		throw new RuntimeException(message) {
			private static final long serialVersionUID = 5688687621368786756L;
		};
	}
	
	/**
	 * Throw RuntimeException
	 * @param message
	 * @return
	 */
	public static void throwRuntimeException(Throwable cause){
		throw new RuntimeException(cause) {
			private static final long serialVersionUID = 5688687621368786756L;
		};
	}
	
	/**
	 * Throw RuntimeException
	 * @param message
	 * @param cause
	 * @return
	 */
	public static void throwRuntimeException(String message, Throwable cause){
		throw new RuntimeException(message,cause) {
			private static final long serialVersionUID = 5688687621368786756L;
		};
	}
	
	/**
	 * <pre>
	 * Throw RuntimeException
	 * Notes: String.format(format, message)
	 * </pre>
	 * 
	 * @see java.lang.String#format
	 * @param format
	 * @param message
	 * @return
	 */
	public static void throwRuntimeException(String format, Object...message){
		throwRuntimeException(String.format(format, message));
	}

	public static Throwable getTargetException(Exception exception){
		Assert.notNull(exception);
		if(exception instanceof InvocationTargetException){
			Throwable target = ((InvocationTargetException)exception).getTargetException();
			if(target != null){
				return target;
			}
		}

		return exception;
	}

}
