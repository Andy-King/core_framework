package org.jee.framework.core.utils;

import java.io.UnsupportedEncodingException;
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
        调用说明:
        // ---- DES
        String encode = EncoderUtils.hexEncode(encrypt("abc".getBytes(), CryptoUtils.KEY_DES_BYTES, CryptoUtils.DES));
        System.out.println("DES encode:" + encode);
        String decode = decrypt(EncoderUtils.hexDecode(encode), CryptoUtils.KEY_DES_BYTES, CryptoUtils.DES);
        System.out.println("DES dncode:" + decode);
        
        // ---- RC4
        encode = EncoderUtils.hexEncode(encrypt("abc".getBytes(), CryptoUtils.KEY_RC4_BYTES, CryptoUtils.RC4));
        System.out.println("RC4 encode:" + encode);
        decode = decrypt(EncoderUtils.hexDecode(encode), CryptoUtils.KEY_RC4_BYTES, RC4);
        System.out.println("RC4 dncode:" + decode);
        
        // ---- 3DES
        byte[] keyDesBytes = "abcdefghabcdefghabcdefgh".getBytes();
        encode = encrypt4Des("abc", keyDesBytes);
        System.out.println("3DES encode:" + encode);
        decode = decrypt4Des(encode, keyDesBytes);
        System.out.println("3DES dncode:" + decode);
        
        // ---- AES
        byte[] aesKey = CryptoUtils.KEY_AES_BYTES ;
        encode = encrypt4Aes("abc", aesKey);
        System.out.println("AES encode:" + encode);
        decode = decrypt4AES(encode, aesKey);
        System.out.println("AES dncode:" + decode);
  
        输出结果:
        DES encode:59dc29da43b9636e
        DES dncode:abc
        RC4 encode:5b1abf
        RC4 dncode:abc
        3DES encode:d9cfaa0560f3f29f
        3DES dncode:abc
        AES encode:30cf4f17ada17b41efcb0e6f2bb55999
        AES dncode:abc
 * 
 * </pre>
 * @author AK
 */
public abstract class CryptoUtils {
	//DES、3DES、AES、IDEA、RC4、RC5
	/**
	 * key长度为8的倍数bytes
	 */
	public static final String AES = "AES";
	
	/**
	* AES 加密参数
	* byte[] key = "abcdefghabcdefgh".getBytes();
	* System.out.println(aesDecrypt(aesEncrypt("abc", key), key));
	*/
	public final static byte[] KEY_AES_BYTES = "Q!Om01@*Wei$3vp%".getBytes();
	
	/**
	 * key长度为16bytes
	 */
	public static final String RC4 = "RC4"; //定义 加密算法,可用 DES,DESede,Blowfish
	
	/**
	* <pre>
	* RC4 加密参数
	* byte[] key = "abcdefgh".getBytes();
	* System.out.println(decrypt(encrypt("abc".getBytes(), KEY_RC4_BYTES, RC4), KEY_RC4_BYTES, RC4));
	* </pre>
	*/
	public final static byte[] KEY_RC4_BYTES = "&*jw@jv-Q!Om01@*".getBytes();
	
	/**
	 * key长度为8bytes,不能超
	 */
	public static final String DES = "DES"; //定义 加密算法,可用 DES,DESede,Blowfish
	
	/**
	* <pre>
	* DES 加密参数
	* byte[] key = "abcdefgh".getBytes();
	* System.out.println(decrypt4Des(encrypt4Des("abc", key), key));
	* </pre>
	*/
	public final static byte[] KEY_DES_BYTES = "Q!Om01@*".getBytes();
	
	/**
	 * key长度为24bytes
	 */
	public static final String THREE_DES = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish
	
	/**
	* <pre>
	* 3DES 加密参数
	* byte[] key = "abcdefghabcdefghabcdefgh".getBytes();
	* System.out.println(decrypt(encrypt("abc".getBytes(), KEY_3DES_BYTES, THREE_DES), KEY_3DES_BYTES, THREE_DES));
	* </pre>
	*/
	public final static byte[] KEY_3DES_BYTES = "&j^_^oQ!Om01@*&*jw@jv-Q!".getBytes();
	
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
	 * <pre>
	 * 使用AES加密原始字符串.
	 * 注:AES的key的长度需要是8的倍数,eg:byte[] key = "abcdefghabcdefgh".getBytes();
	 * </pre>
	 * 
	 * @see org.jee.framework.core.utils.CryptoUtils#AES
	 * @see org.jee.framework.core.utils.CryptoUtils#KEY_AES_BYTES
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 */
	public static byte[] encrypt4Aes(byte[] input, byte[] key) {
		return encrypt(input, key, AES);
	}
	
	/**
	 * <pre>
	 * 使用AES加密原始字符串.
	 * 注:AES的key的长度需要是8的倍数,eg:byte[] key = "abcdefghabcdefgh".getBytes();
	 * <pre>
	 * 
	 * @see org.jee.framework.core.utils.CryptoUtils#AES
	 * @see org.jee.framework.core.utils.CryptoUtils#KEY_AES_BYTES
	 * @param input 原始输入字符数组
	 * @param key 符合AES要求的密钥
	 */
	public static String encrypt4Aes(String input, byte[] key) {
		return EncoderUtils.hexEncode(encrypt4Aes(input.getBytes(), key));
	}
	
	/**
	 * <pre>
	 * 使用AES解密字符串, 返回原始字符串.
	 * 注:AES的key的长度需要是8的倍数,eg:byte[] key = "abcdefghabcdefgh".getBytes();
	 * </pre>
	 * 
	 * @see org.jee.framework.core.utils.CryptoUtils#AES
	 * @see org.jee.framework.core.utils.CryptoUtils#KEY_AES_BYTES
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static String decrypt4Aes(byte[] input, byte[] key) {
		return decrypt(input, key, AES);
	}
	
	/**
	 * <pre>
	 * 使用AES解密字符串, 返回原始字符串.
	 * 注:AES的key的长度需要是8的倍数,eg:byte[] key = "abcdefghabcdefgh".getBytes();
	 * </pre>
	 * 
	 * @see org.jee.framework.core.utils.CryptoUtils#AES
	 * @see org.jee.framework.core.utils.CryptoUtils#KEY_AES_BYTES
	 * @param input Hex编码的加密字符串
	 * @param key 符合AES要求的密钥
	 */
	public static String decrypt4AES(String input, byte[] key) {
		return decrypt4Aes(EncoderUtils.hexDecode(input), key);
	}
	
	
	// -- DES funciton --//
	/**
	 * <pre>
	 * DES 加密
	 * byte[] key = "abcdefgh".getBytes();
	 * System.out.println(decrypt4Des(encrypt4Des("abc", key), key));
	 * </pre>
	 *
	 * @see org.jee.framework.core.utils.CryptoUtils#THREE_DES
	 * @see org.jee.framework.core.utils.CryptoUtils#KEY_3DES_BYTES
	 * @param data
	 * @param key
	 * @return
	 */
	public static byte[] encrypt4Des(byte[] data, byte[] key){
		return encrypt(data, key, THREE_DES);
	}
	
	/**
	* <pre>
	* DES 加密
	* byte[] key = "abcdefgh".getBytes();
	* System.out.println(decrypt4Des(encrypt4Des("abc", key), key));
	* </pre>
	*
	* @see org.jee.framework.core.utils.CryptoUtils#THREE_DES
	* @see org.jee.framework.core.utils.CryptoUtils#KEY_3DES_BYTES
	* @param data
	* @param key
	* @return
	*/
	public static String encrypt4Des(String data, byte[] key){
		return EncoderUtils.hexEncode(encrypt4Des(data.getBytes(), key));
	}
	
	/**
	 * <pre>
	 * DES 解密
	 * byte[] key = "abcdefgh".getBytes();
	 * System.out.println(decrypt4Des(encrypt4Des("abc", key), key));
	 * </pre>
	 * 
	 * @see org.jee.framework.core.utils.CryptoUtils#THREE_DES
	 * @see org.jee.framework.core.utils.CryptoUtils#KEY_3DES_BYTES
	 * @param data
	 * @param key
	 * @return
	 */
	public static String decrypt4Des(byte[] data, byte[] key){
		return decrypt(data, key, THREE_DES);
	}
	
	/**
	 * <pre>
	 * DES 解密
	 * byte[] key = "abcdefgh".getBytes();
	 * System.out.println(decrypt4Des(encrypt4Des("abc", key), key));
	 * </pre>
	 * 
	 * @see org.jee.framework.core.utils.CryptoUtils#THREE_DES
	 * @see org.jee.framework.core.utils.CryptoUtils#KEY_3DES_BYTES
	 * @param data
	 * @param key
	 * @return
	 */
	public static String decrypt4Des(String data, byte[] key){
		return decrypt4Des(EncoderUtils.hexDecode(data), key);
	}
	
	/**
	 * 使用AES/DES/RC4加密原始字符串.
	 * 
	 * @param input 原始输入字符数组
	 * @param key 符合AES/DES要求的密钥
	 */
	public static byte[] encrypt(byte[] input, byte[] key, String algorithm) {
		return code(input, key, Cipher.ENCRYPT_MODE, algorithm);
	}
	
	/**
	 * 使用AES/DES/RC4解密字符串, 返回原始字符串.
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
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		//DES、3DES、AES、IDEA、RC4、RC5
		//Security.addProvider(new com.sun.crypto.provider.SunJCE());
		// ---- DES
		String encode = EncoderUtils.hexEncode(encrypt("abc".getBytes(), CryptoUtils.KEY_DES_BYTES, CryptoUtils.DES));
		System.out.println("DES encode:" + encode);
		String decode = decrypt(EncoderUtils.hexDecode(encode), CryptoUtils.KEY_DES_BYTES, CryptoUtils.DES);
		System.out.println("DES dncode:" + decode);
		
		// ---- RC4
		encode = EncoderUtils.hexEncode(encrypt("abc".getBytes(), CryptoUtils.KEY_RC4_BYTES, CryptoUtils.RC4));
		System.out.println("RC4 encode:" + encode);
		decode = decrypt(EncoderUtils.hexDecode(encode), CryptoUtils.KEY_RC4_BYTES, RC4);
		System.out.println("RC4 dncode:" + decode);
		
		// ---- 3DES
		byte[] keyDesBytes = "abcdefghabcdefghabcdefgh".getBytes();
		encode = encrypt4Des("abc", keyDesBytes);
		System.out.println("3DES encode:" + encode);
		decode = decrypt4Des(encode, keyDesBytes);
		System.out.println("3DES dncode:" + decode);
		
		// ---- AES
		byte[] aesKey = CryptoUtils.KEY_AES_BYTES ;
		encode = encrypt4Aes("abc", aesKey);
		System.out.println("AES encode:" + encode);
		decode = decrypt4AES(encode, aesKey);
		System.out.println("AES dncode:" + decode);
	}
}
