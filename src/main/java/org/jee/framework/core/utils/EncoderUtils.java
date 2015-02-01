package org.jee.framework.core.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.Validate;

/**
 * 封装各种格式的编码解码工具类.
 * 
 * @author AK
 */
public abstract class EncoderUtils {
	
	private static final String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	private static final String DEFAULT_URL_ENCODING = "UTF-8";


	/**
	 * Hex编码, byte[]->String.
	 */
	public static String hexEncode(byte[] input) {
		return Hex.encodeHexString(input);
	}

	/**
	 * Hex解码, String->byte[].
	 */
	public static byte[] hexDecode(String input) {
		try {
			return Hex.decodeHex(input.toCharArray());
		} catch (DecoderException e) {
			throw new IllegalStateException("Hex Decoder exception", e);
		}
	}

	/**
	 * Base64编码, byte[]->String.
	 */
	public static String base64Encode(byte[] input) {
		return Base64.encodeBase64String(input);
	}

	/**
	 * Base64编码, URL安全(将Base64中的URL非法字符'+'和'/'转为'-'和'_', 见RFC3548).
	 */
	public static String base64UrlSafeEncode(byte[] input) {
		return Base64.encodeBase64URLSafeString(input);
	}

	/**
	 * Base64解码, String->byte[].
	 */
	public static byte[] base64Decode(String input) {
		return Base64.decodeBase64(input);
	}

	/**
	 * Base62(0_9A_Za_z)编码数字, long->String.
	 */
	public static String base62Encode(long num) {
		return alphabetEncode(num, 62);
	}

	/**
	 * Base62(0_9A_Za_z)解码数字, String->long.
	 */
	public static long base62Decode(String str) {
		return alphabetDecode(str, 62);
	}

	private static String alphabetEncode(long num, int base) {
		num = Math.abs(num);
		StringBuilder sb = new StringBuilder();
		for (; num > 0; num /= base) {
			sb.append(ALPHABET.charAt((int) (num % base)));
		}

		return sb.toString();
	}

	private static long alphabetDecode(String str, int base) {
		Validate.notBlank(str);

		long result = 0;
		for (int i = 0; i < str.length(); i++) {
			result += ALPHABET.indexOf(str.charAt(i)) * Math.pow(base, i);
		}

		return result;
	}

	/**
	 * Html 转码.
	 */
	public static String escapeHtml(String html) {
		return StringEscapeUtils.escapeHtml4(html);
	}

	/**
	 * Html 解码.
	 */
	public static String unescapeHtml(String htmlEscaped) {
		return StringEscapeUtils.unescapeHtml4(htmlEscaped);
	}

	/**
	 * Xml 转码.
	 * @see org.apache.commons.lang3.StringEscapeUtils#escapeXml10
	 */
	public static String escapeXml10(String xml) {
		return StringEscapeUtils.escapeXml10(xml);
	}
	
	/**
	 * Xml 转码.
	 * @see org.apache.commons.lang3.StringEscapeUtils#escapeXml11
	 */
	public static String escapeXml11(String xml) {
		return StringEscapeUtils.escapeXml11(xml);
	}

	/**
	 * Xml 解码.
	 */
	public static String unescapeXml(String xmlEscaped) {
		return StringEscapeUtils.unescapeXml(xmlEscaped);
	}

	/**
	 * Csv 转码.
	 */
	public static String escapeCsv(String csv) {
		return StringEscapeUtils.escapeCsv(csv);
	}

	/**
	 * Csv 解码.
	 */
	public static String unescapeCsv(String csvEscaped) {
		return StringEscapeUtils.unescapeCsv(csvEscaped);
	}

	/**
	 * URL 编码, Encode默认为UTF-8. 
	 * @param part
	 * @param enc 编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlEncode(String part, String enc) throws UnsupportedEncodingException {
		return URLEncoder.encode(part, enc);
	}

	/**
	 * URL 解码, Encode默认为UTF-8. 
	 * @param part
	 * @param enc 编码
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String urlDecode(String part, String enc) throws UnsupportedEncodingException {
		return URLDecoder.decode(part, enc);
	}
	
	/**
	 * URL 编码, Encode默认为UTF-8. 
	 */
	public static String urlEncode(String part) {
		try {
			return URLEncoder.encode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			ExceptionUtils.throwRuntimeException("urlEncode error:", e);
		}
		return part;
	}
	
	/**
	 * URL 解码, Encode默认为UTF-8. 
	 */
	public static String urlDecode(String part) {
		
		try {
			return URLDecoder.decode(part, DEFAULT_URL_ENCODING);
		} catch (UnsupportedEncodingException e) {
			ExceptionUtils.throwRuntimeException("urlDecode error:", e);
		}
		return part;
	}
}
