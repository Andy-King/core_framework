package org.jee.framework.core.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;

/**
 * 类型转换工具类
 * 
 * @author AK
 *
 */
public abstract class ConvertUtils extends org.apache.commons.beanutils.ConvertUtils{
	
	static {
		registerDateConverter("yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss");
	}
	
	/**
	 * 注册一个时间类型的转换器,当前默认的格式为：yyyy-MM-dd
	 * 
	 * @param patterns 日期格式
	 */
	public static void registerDateConverter(String... patterns) {
		final DateConverter dc = new DateConverter();
		dc.setUseLocaleFormat(true);
		dc.setPatterns(patterns);
		register(dc, Date.class);
	}
	
	/**
	 * 基于Apache BeanUtils转换字符串到相应类型.
	 * 
	 * @param value 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertStringToObject(String value, Class<?> toType) {
		try {
			return convert(value, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	
	/**
	 * 转换字符串数组到相应类型.
	 * 
	 * @param values 待转换的字符串.
	 * @param toType 转换目标类型.
	 */
	public static Object convertStringsToObject(String[] values,Class<?> toType) {
		try {
			return convert(values, toType);
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
	}
	
	/**
	 * 通过 getter 函数提取集合中的对象的属性, 并组合成 List.
	 * @param <T>
	 * 
	 * @param collection
	 *            来源集合
	 * @param propertyName
	 *            要提取的属性名
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> convertElementPropertyToList(final Collection<T> collection, final String propertyName) {
		
		if(CollectionUtils.isEmpty(collection)) { return Collections.emptyList();}
		
		final List<T> list = new ArrayList<T>(collection.size());
		try {
			for (Object obj : collection) {
				list.add((T)PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw ReflectionUtils.convertReflectionExceptionToUnchecked(e);
		}
		return list;
	}

	/**
	 * 通过 getter 函数提取集合中的对象的属性, 并组合成由分隔符分割的字符串.
	 * @param <T>
	 * 
	 * @param collection
	 *            来源集合
	 * @param propertyName
	 *            要提取的属性名
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static <T> String convertElementPropertyToString(final Collection<T> collection, final String propertyName,
			final String separator) {
		final List<T> list = convertElementPropertyToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

}

