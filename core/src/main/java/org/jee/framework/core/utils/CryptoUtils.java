package org.jee.framework.core.utils;

import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * <pre>
 * 支持HMAC-SHA1消息签名 及 DES/AES对称加密的工具类.
 * 
 * 支持Hex与Base64两种编码方式.
 * 注:AES的key的长度需要是8的倍数,eg:byte[] key = "abcdefghabcdefgh".getBytes();
 * 
 * 调用说明:
 * byte[] key = KEY_DES_BYTES;
 * String algorithm = "DES";
 * String encode = EncoderUtils.hexEncode(encrypt("abc".getBytes(), key, algorithm));
 * System.out.println(encode);
 * 
 * String decode = decrypt(EncoderUtils.hexDecode(encode), key, algorithm);
 * System.out.println(decode);
 * 
 * System.out.println(desDecrypt(desEncrypt("abc", key), key));
 * 
 * byte[] aesKey = KEY_AES_BYTES;
 * String aesAlgorithm = "AES";
 * String aesEncode = EncoderUtils.hexEncode(encrypt("abc".getBytes(), aesKey, aesAlgorithm));
 * System.out.println(aesEncode);
 * 
 * String aesDecode = decrypt(EncoderUtils.hexDecode(aesEncode), aesKey, aesAlgorithm);
 * System.out.println(aesDecode);
 * 
 * 输出结果:
 * 59dc29da43b9636e
 * abc
 * abc
 * 30cf4f17ada17b41efcb0e6f2bb55999
 * abc
 * 
 * </pre>
 * @author AK
 */
public abstract class CryptoUtils {
	
	public static final String AES = "AES";
	public static final String DES = "DES"; //定义 加密算法,可用 DES,DESede,Blowfish
	
	private static final String HMACSHA1 = "HmacSHA1";

	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160; // RFC2401

	// -- HMAC-SHA1 funciton --//
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * 
	 * @param input 原始输入字符数组
	 * @param key HMAC-SHA1密钥
	 */
	public static byte[] hmacSha1(byte[] input, byte[] key) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	/**
	 * 校验HMAC-SHA1签名是否正确.
	 * 
	 * @param expected 已存在的签名
	 * @param input 原始输入字符串
	 * @param key 密钥
	 */
	public static boolean isMacValid(byte[] expected, byte[] input, byte[] key) {
		byte[] actual = hmacSha1(input, key);
		return Arrays.equals(expected, actual);
	}

	/**
	 * 生成HMAC-SHA1密钥,返回字节数组,长度为160位(20字节).
	 * HMAC-SHA1算法对密钥无特殊要求, RFC2401建议最少长度为160位(20字节).
	 */
	public static byte[] generateHmacSha1Key() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
			keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (GeneralSecurityException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	// -- AES funciton --//
	/**
	* AES 加密参数
	* byte[] key = "abcdefghabcdefgh".getBytes();
	* System.out.println(aesDecrypt(aesEncrypt("abc", key), key));
	*/
	public final static byte[] KEY_AES_BYTES = "Q!Om01@*Wei$3vp%".getBytes();
	/**
	 * <pre>
	 * 使用AES加密原始字符串.
	 * 注:AES的key的长度需要是8的倍数,eg:byte[] key = "abcdefghabcdefgh".getBytes();
	 * </pre>
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key) {
		return encrypt(input, key, AES);
	}
	
	/**
	 * <pre>
	 * 使用AES加密原始字符串.
	 * 注:AES的key的长度需要是8的倍数,eg:byte[] key = "abcdefghabcdefgh".getBytes();
	 * <pre>
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 */
	public static String aesEncrypt(String input, byte[] key) {
		return EncoderUtils.hexEncode(aesEncrypt(input.getBytes(), key));
	}
	
	/**
	 * <pre>
	 * 使用AES解密字符串, 返回原始字符串.
	 * 注:AES的key的长度需要是8的倍数,eg:byte[] key = "abcdefghabcdefgh".getBytes();
	 * </pre>
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static String aesDecrypt(byte[] input, byte[] key) {
		return decrypt(input, key, AES);
	}
	
	/**
	 * <pre>
	 * 使用AES解密字符串, 返回原始字符串.
	 * 注:AES的key的长度需要是8的倍数,eg:byte[] key = "abcdefghabcdefgh".getBytes();
	 * </pre>
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static String aesDecrypt(String input, byte[] key) {
		return aesDecrypt(EncoderUtils.hexDecode(input), key);
	}
	
	
	// -- DES funciton --//
	/**
	* <pre>
	* DES 加密参数
	* byte[] key = "abcdefgh".getBytes();
	* System.out.println(desDecrypt(desEncrypt("abc", key), key));
	* </pre>
	*/
	public final static byte[] KEY_DES_BYTES = "Q!Om01@*".getBytes();
	
	/**
	 * <pre>
	 * DES 加密
	 * byte[] key = "abcdefgh".getBytes();
	 * System.out.println(desDecrypt(desEncrypt("abc", key), key));
	 * </pre>
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] desEncrypt(byte[] data, byte[] key){
		return encrypt(data, key, DES);
	}
	
	/**
	* <pre>
	* DES 加密
	* byte[] key = "abcdefgh".getBytes();
	* System.out.println(desDecrypt(desEncrypt("abc", key), key));
	* </pre>
	* 
	* @param data
	* @param key
	* @return
	*/
	public static String desEncrypt(String data, byte[] key){
		return EncoderUtils.hexEncode(desEncrypt(data.getBytes(), key));
	}
	
	/**
	 * <pre>
	 * DES 解密
	 * byte[] key = "abcdefgh".getBytes();
	 * System.out.println(desDecrypt(desEncrypt("abc", key), key));
	 * </pre>
	 *
	 * @param data
	 * @param key
	 * @return
	 */
	public static String desDecrypt(byte[] data, byte[] key){
		return decrypt(data, key, DES);
	}
	
	/**
	 * <pre>
	 * DES 解密
	 * byte[] key = "abcdefgh".getBytes();
	 * System.out.println(desDecrypt(desEncrypt("abc", key), key));
	 * </pre>
	 * @param data
	 * @param key
	 * @return
	 */
	public static String desDecrypt(String data, byte[] key){
		return desDecrypt(EncoderUtils.hexDecode(data), key);
	}
	
	/**
	 * 使用AES/DES加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 * @param key 符合AES/DES要求的密钥
	 */
	public static byte[] encrypt(byte[] input, byte[] key, String algorithm) {
		return code(input, key, Cipher.ENCRYPT_MODE, algorithm);
	}
	
	/**
	 * 使用AES/DES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static String decrypt(byte[] input, byte[] key, String algorithm) {
		final byte[] decryptResult = decrypt2Bytes(input, key, algorithm);
		return new String(decryptResult);
	}
	
	/**
	 * 使用AES/DES解密字符串, 返回原始字符串.
	 * 
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static byte[] decrypt2Bytes(byte[] input, byte[] key, String algorithm) {
		return code(input, key, Cipher.DECRYPT_MODE, algorithm);
	}
	
	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param input 原始字节数组
	 * @param key 符合AES要求的密钥
	 * @param mode Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] code(byte[] input, byte[] key, int mode, String algorithm) {
		try {
			SecretKey secretKey = new SecretKeySpec(key, algorithm);
			Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (GeneralSecurityException e) {
			throw ExceptionUtils.unchecked(e);
		}
	}

	public static void main(String[] args) {
		byte[] key = KEY_DES_BYTES;
		String algorithm = "DES";
		String encode = EncoderUtils.hexEncode(encrypt("abc".getBytes(), key, algorithm));
		System.out.println(encode);
		
		String decode = decrypt(EncoderUtils.hexDecode(encode), key, algorithm);
		System.out.println(decode);
		
		System.out.println(desDecrypt(desEncrypt("abc", key), key));
		
		byte[] aesKey = KEY_AES_BYTES;
		String aesAlgorithm = "AES";
		String aesEncode = EncoderUtils.hexEncode(encrypt("abc".getBytes(), aesKey, aesAlgorithm));
		System.out.println(aesEncode);
		
		String aesDecode = decrypt(EncoderUtils.hexDecode(aesEncode), aesKey, aesAlgorithm);
		System.out.println(aesDecode);
	}
}
