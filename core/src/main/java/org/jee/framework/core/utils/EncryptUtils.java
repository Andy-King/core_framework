package org.jee.framework.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 支持 SHA-1 / MD5 消息摘要的工具类, 支持 HEX 与 Base64 两种编码方式.
 * <p>
 * 在 Java 中, {@link MessageDigest} 中已经定义了 MD5 的计算, 所以只要简单地调用即可得到 MD5 的 128 位整数, 然后将此 128 位计 16
 * 个字节转换成 16 机制表示即可. 在 RFC 1321 中, 给出了测试用例用来检验其实现是否正确:
 * <ul>
 * 	<li>md5 ("") = d41d8cd98f00b204e9800998ecf8427e</li>
 * 	<li>md5 ("a") = 0cc175b9c0f1b6a831c399e269772661</li>
 * 	<li>md5 ("abc") = 900150983cd24fb0d6963f7d28e17f72</li>
 * 	<li>md5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0</li>
 * 	<li>md5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b</li>
 * </ul>
 * <p> Every implementation of the Java platform is required to support
 * the following standard <code>MessageDigest</code> algorithms:
 * <ul>
 * <li><tt>MD5</tt></li>
 * <li><tt>SHA-1</tt></li>
 * <li><tt>SHA-256</tt></li>
 * </ul>
 * These algorithms are described in the <a href=
 * "{@docRoot}/../technotes/guides/security/StandardNames.html#MessageDigest">
 * MessageDigest section</a> of the
 * Java Cryptography Architecture Standard Algorithm Name Documentation.
 * Consult the release documentation for your implementation to see if any
 * other algorithms are supported.
 * 
 * <p>
 * invoke demo:
 * <ul>
 *   <li>System.out.println(md5("abc"));</li>
 *   <li>System.out.println(digest2Hex("abc", DigestAlgorithm.md5));</li>
 *   <li>System.out.println(sha_1("abc"));</li>
 *   <li>System.out.println(digest2Hex("abc", DigestAlgorithm.sha_1));</li>
 *   <li>System.out.println(sha_256("abc"));</li>
 *   <li>System.out.println(digest2Hex("abc", DigestAlgorithm.sha_256));</li>
 * </ul>
 * 
 * @author AK
 * @see EncoderUtils
 */
public abstract class EncryptUtils {
	
	/**
	 * <pre>
	 * 支持 SHA1, SHA256和 MD5 算法.
	 * 
	 * eg:
	 * System.out.println(DigestAlgorithm.sha_1);
	 * System.out.println(DigestAlgorithm.valueOf("md5"));
	 * System.out.println(Enum.valueOf(DigestAlgorithm.class, "md5"));
	 * </pre>
	 * @author AK
	 *
	 */
	public enum DigestAlgorithm {
		 /**
		 * MD5 散列算法
		 */
		 md5("MD5"), 
		 /**
		 * SHA-1 散列算法
		 */
		 sha_1("SHA-1"), 
		 /**
		 * SHA-256 散列算法
		 */
		 sha_256("SHA-256");
		
		private String value;
		
		private DigestAlgorithm(String value){
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
		
		public String getValue() {
			return value;
		}
	}

	/**
	 * 默认加密字符编码
	 */
	private static final String DEFAULT_ALGORITHM_CHARSET = "UTF-8";

	/**
	 * 将输入的字符串生成 32 位的 MD5 值.
	 * 
	 * @param input
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static String md5(String input) {
		return md5(input, DEFAULT_ALGORITHM_CHARSET);
	}
	
	public static String md5(String input, String charset) {
		try {
			return digest2Hex(input, charset, DigestAlgorithm.md5);
		} catch (java.security.NoSuchAlgorithmException ne) {
			throw new IllegalStateException("System doesn't support your  Algorithm.");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System doesn't support chaset");
		}
	}
	
	public static String sha_1(String input){
		return sha_1(input, DEFAULT_ALGORITHM_CHARSET);
	}
	
	public static String sha_1(String input, String charset){
		try {
			return digest2Hex(input, charset, DigestAlgorithm.sha_1);
		} catch (java.security.NoSuchAlgorithmException ne) {
			throw new IllegalStateException("System doesn't support your  Algorithm.");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System doesn't support chaset");
		}
	}
	
	public static String sha_256(String input){
		return sha_1(input, DEFAULT_ALGORITHM_CHARSET);
	}
	
	public static String sha_256(String input, String charset){
		try {
			return digest2Hex(input, charset, DigestAlgorithm.sha_256);
		} catch (java.security.NoSuchAlgorithmException ne) {
			throw new IllegalStateException("System doesn't support your  Algorithm.");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("System doesn't support chaset");
		}
	}
	
	
	/**
	 * 对输入的字符串进行 SHA 散列, 返回其 Hex 编码的结果.
	 * 
	 * @param input
	 * @param algorithm 支持 SHA1, SHA256和 MD5 算法.
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public static String digest2Hex(String input, DigestAlgorithm algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] digestResult = digest(input, algorithm);
		return EncoderUtils.hexEncode(digestResult);
	}
	
	/**
	 * 对输入的字符串进行 SHA 散列, 返回其 Hex 编码的结果.
	 * 
	 * @param input
	 * @param charset 字符集, 默认UTF-8
	 * @param algorithm 支持 SHA1, SHA256和 MD5 算法.
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public static String digest2Hex(String input, String charset, DigestAlgorithm algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] digestResult = digest(input, charset, algorithm);
		return EncoderUtils.hexEncode(digestResult);
	}
	
	/**
	 * 对输入的字符串进行 SHA 散列, 返回其 Base64 编码的结果.
	 * 
	 * @param input
	 * @param algorithm 支持 SHA1, SHA256和 MD5 算法.
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public static String digest2Base64(String input, DigestAlgorithm algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] digestResult = digest(input, algorithm);
		return EncoderUtils.base64Encode(digestResult);
	}
	
	/**
	 * 对输入的字符串进行 SHA 散列, 返回其 Base64 编码的 URL 安全的结果.
	 * 
	 * @param input
	 * @param algorithm 支持 SHA1, SHA256和 MD5 算法.
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException 
	 */
	public static String digest2Base64UrlSafe(String input, DigestAlgorithm algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		byte[] digestResult = digest(input, algorithm);
		return EncoderUtils.base64UrlSafeEncode(digestResult);
	}
	
	/**
	 * 对文件进行散列支持 SHA1, SHA256和 MD5 算法., 返回其 Hex 编码的结果.
	 * 
	 * @param input
	 * @param algorithm 支持 SHA1, SHA256和 MD5 算法.
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public static String digest2Hex(InputStream input, String algorithm) throws NoSuchAlgorithmException, IOException {
		return digest(input, algorithm);
	}
	
	/**
	 * 对文件进行散列, 支持 SHA1, SHA256和 MD5 算法.
	 * 
	 * @param input
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws IOException 
	 */
	private static String digest(InputStream input, String algorithm) throws NoSuchAlgorithmException, IOException {

		final MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
		final int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int read = 0;
		while ((read = input.read(buffer, 0, bufferSize)) != -1) {
			messageDigest.update(buffer, 0, read);
		}
		return EncoderUtils.hexEncode(messageDigest.digest());
	}
	
	/**
	 * <pre>
	 * 对字符串进行散列, 支持 SHA1, SHA256和 MD5 算法.
	 * 注:
	 *    默认字符为UTF-8
	 * </pre>   
	 *    
	 * @param input
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	private static byte[] digest(String input, DigestAlgorithm algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		
		return digest(input, DEFAULT_ALGORITHM_CHARSET, algorithm);
	}
	
	/**
	 * 对字符串进行散列, 支持 SHA1, SHA256和 MD5 算法.
	 * 
	 * @param input
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	private static byte[] digest(String input, String charset, DigestAlgorithm algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return digest(input.getBytes(charset), algorithm);
	}
	
	/**
	 * 对字符串进行散列, 支持 SHA1, SHA256和 MD5 算法.
	 * 
	 * @param input
	 * @param algorithm
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	private static byte[] digest(byte[] bytes, DigestAlgorithm algorithm) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return MessageDigest.getInstance(algorithm.getValue()).digest(bytes);
	}
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		System.out.println(md5("").equals("d41d8cd98f00b204e9800998ecf8427e"));
		System.out.println(md5("abc"));
		System.out.println(digest2Hex("abc", DigestAlgorithm.md5));
		System.out.println(sha_1("abc"));
		System.out.println(digest2Hex("abc", DigestAlgorithm.sha_1));
		System.out.println(sha_256("abc"));
		System.out.println(digest2Hex("abc", DigestAlgorithm.sha_256));
	}
}