package org.jee.framework.core.dao.orm;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Restrictions;
import org.jee.framework.core.utils.ConvertUtils;


/**
 * orm属性过滤器，可以通过{@link PropertyFilters#build(String, String)}创建，使用他创建可以直接
 * 写入表达式即可，相关表达式样式查看{@link com.github.dactiv.orm.core.hibernate.CriterionBuilder}的实现类,如果直接创建查看{@link com.github.dactiv.orm.core.hibernate.CriterionBuilder}
 * 实现类的实际restrictionName值和{@link FieldType}枚举值,如果一个属性对比多个值可以使用逗号(,)分割
 *
 * <p>
 * 	例子:
 * </p>
 * <code>PropertyFilter filter = new PropertyFilter("EQ",FieldType.S.getValue(),new String[]{"propertyName"},"a,b,c");<code>
 * <p>当使用以上filter到{@link com.github.dactiv.orm.core.hibernate.support.HibernateSupportDao#findUniqueByPropertyFilter(java.util.List)}是会产生以下sql</p>
 * <p>sql:selete * from table where propertyName = 'a' and propertyName = 'b' and propertyName = 'c'</p>
 *
 * <code>PropertyFilter filter = new PropertyFilter("EQ",FieldType.S.getValue(),new String[]{"propertyName1","propertyName2"},"a");<code>
 * <p>当使用以上filter到{@link  com.github.dactiv.orm.core.hibernate.support.HibernateSupportDao#findUniqueByPropertyFilter(java.util.List)}是会产生以下sql</p>
 * <p>sql:selete * from table where propertyName1 = 'a' or propertyName2 = 'a'</p>
 *
 * <code>PropertyFilter filter = new PropertyFilter("EQ",FieldType.S.getValue(),new String[]{"propertyName1","propertyName2"},"a,b");<code>
 * <p>当使用以上filter到{@link  com.github.dactiv.orm.core.hibernate.support.HibernateSupportDao#findUniqueByPropertyFilter(java.util.List)}是会产生以下sql</p>
 * <p>sql:selete * from table where (propertyName1 = 'a' or propertyName1 = 'b') and (propertyName2 = 'a' or propertyName2 = 'b')</p>
 * 
 * @see PropertyFilters#build(String, String)
 * @see com.github.dactiv.orm.core.hibernate.CriterionBuilder
 * 
 */
public class PropertyFilter {

	/**
	 * 多个属性间 OR 关系的分隔符
	 */
	public static final String OR_SEPARATOR = "_OR_";
	
	/**
	 * 基本属性比较类型, 主要考虑到 {@link Restrictions} 的比较类型支持.
	 */
	public enum MatchType {
		EQ,		// 等于
		NE,		// 不等于
		GT,		// 大于
		GE,		// 大于或等于
		LT,		// 小于
		LE,		// 小于或等于
		LIKE;	// 字符串模式匹配
	}
	
	/**
	 * 基本属性数据类型, 主要考虑到当前数据库的支持类型.
	 */
	public enum PropertyType {
		
		B(Boolean.class),	// Boolean 类型
		I(Integer.class),	// Integer 类型
		F(Float.class),		// Float 类型
		N(Double.class),	// Double 类型
		L(Long.class),		// Long 类型
		S(String.class),	// String 类型
		D(Date.class);		// Date 类型

		private Class<?> clazz;

		private PropertyType(Class<?> clazz) {
			this.clazz = clazz;
		}

		public Class<?> getValue() {
			return clazz;
		}
	}
	//约束名称
	private String restrictionName;
	
	//属性类型
	private Class<?> propertyType;
	/**
	 * 比较类型
	 */
	private MatchType matchType;
	
	/**
	 * 比较值
	 */
	private Object matchValue;
	
	/**
	 * 属性过滤类
	 */
	private Class<?> propertyClass;
	
	/**
	 * 比较属性过滤名列表
	 */
	private String[] propertyNames;
	
	/**
	 * 构造方法.
	 * 
	 * @param filterName
	 *            比较属性字符串, 含待比较的比较类型、属性值类型及属性列表. 比如: LIKE_S_NAME_OR_LOGIN_NAME
	 * @param value
	 *            待比较的值, 比如如果是 Boolean 类型可以为 "true", Date 类型可以为 "2011-07-28"
	 */
	public PropertyFilter(String filterName, String value) {
		/*
		 * 假设当前的 filterName 是 LIKE_S_NAME_OR_LOGIN_NAME, 则:
		 * 1) matchTypeStr		= LIKE
		 * 2) propertyTypeStr	= S
		 * 3) propertyNameStr	= NAME_OR_LOGIN_NAME
		 * 4) propertyNames[]	= [NAME, LOGIN_NAME]
		 */
		String matchTypeStr = StringUtils.substringBefore(filterName, "_");
		try {
			this.matchType = Enum.valueOf(MatchType.class, matchTypeStr);
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("PropertyFilter - " + filterName
					+ " does not write as rule, so can not get the property match type." + e);
		}
		String excludeMatchTypeStr = StringUtils.substringAfter(filterName, "_");
		String propertyTypeStr = StringUtils.substringBefore(excludeMatchTypeStr, "_");
		try {
			this.propertyClass = Enum.valueOf(PropertyType.class, propertyTypeStr).getValue();
		} catch (RuntimeException e) {
			throw new IllegalArgumentException("PropertyFilter - " + filterName
					+ " does not write as rule, so can not get the property value type." + e);
		}
		String propertyNameStr = StringUtils.substringAfter(excludeMatchTypeStr, "_");
		this.propertyNames = StringUtils.splitByWholeSeparator(propertyNameStr, PropertyFilter.OR_SEPARATOR);
		this.matchValue = ConvertUtils.convertStringToObject(value, this.propertyClass);
	}
	

	/**
	 * 构造方法，可以通过{@link PropertyFilters#build(String, String)}创建，使用他创建可以直接
	 * 写入表达式即可，相关表达式样式查看{@link com.github.dactiv.orm.core.hibernate.CriterionBuilder}的实现类,如果直接创建查看{@link com.github.dactiv.orm.core.hibernate.CriterionBuilder}
	 * 实现类的实际restrictionName值和{@link FieldType}枚举值,如果一个属性对比多个值可以使用逗号(,)分割
	 * 
	 * <p>
	 * 	例子:
	 * </p>
	 * <code>PropertyFilter filter = new PropertyFilter("EQ",FieldType.S.getValue(),new String[]{"propertyName"},"a,b,c");<code> 
	 * <p>当使用以上filter到{@link com.github.dactiv.orm.core.hibernate.support.HibernateSupportDao#findUniqueByPropertyFilter(java.util.List)}是会产生以下sql</p>
	 * <p>sql:selete * from table where propertyName = 'a' and propertyName = 'b' and propertyName = 'c'</p>
	 * 
	 * <code>PropertyFilter filter = new PropertyFilter("EQ",FieldType.S.getValue(),new String[]{"propertyName1","propertyName2"},"a");<code> 
	 * <p>当使用以上filter到{@link  com.github.dactiv.orm.core.hibernate.support.HibernateSupportDao#findUniqueByPropertyFilter(java.util.List)}是会产生以下sql</p>
	 * <p>sql:selete * from table where propertyName1 = 'a' or propertyName2 = 'a'</p>
	 * 
	 * <code>PropertyFilter filter = new PropertyFilter("EQ",FieldType.S.getValue(),new String[]{"propertyName1","propertyName2"},"a,b");<code> 
	 * <p>当使用以上filter到{@link  com.github.dactiv.orm.core.hibernate.support.HibernateSupportDao#findUniqueByPropertyFilter(java.util.List)}是会产生以下sql</p>
	 * <p>sql:selete * from table where (propertyName1 = 'a' or propertyName1 = 'b') and (propertyName2 = 'a' or propertyName2 = 'b')</p>
	 * 
	 * @param restrictionName 约束名称
	 * @param FieldType 属性类型
	 * @param propertyNames 属性名称
	 * @param matchValue 对比值
	 */
	public PropertyFilter(String restrictionsName, PropertyType fieldType,
			String[] propertyNames, String matchValue) {
		this.setRestrictionName(restrictionsName);
		this.propertyType = fieldType.getValue();
		this.propertyNames = propertyNames;
		this.matchValue = matchValue;
	}



	/**
	 * 获取唯一的比较属性名称.
	 * 
	 * @return
	 */
	public String getPropertyName() {
		// 确保属性的长度为 1, 这样才是唯一比较属性
		if (propertyNames.length != 1) {
			throw new RuntimeException("there are not only one property in this property filter");
		}
		return propertyNames[0];
	}
	
	/**
	 * 是否比较多个属性.
	 * 
	 * @return
	 */
	public boolean hasMultiProperties() {
		return (propertyNames.length > 1);
	}

	public MatchType getMatchType() {
		return matchType;
	}

	public Object getMatchValue() {
		return matchValue;
	}

	public Class<?> getPropertyClass() {
		return propertyClass;
	}

	public String[] getPropertyNames() {
		return propertyNames;
	}



	public void setRestrictionName(String restrictionName) {
		this.restrictionName = restrictionName;
	}



	public String getRestrictionName() {
		return restrictionName;
	}



	public void setPropertyType(Class<?> propertyType) {
		this.propertyType = propertyType;
	}



	public Class<?> getPropertyType() {
		return propertyType;
	}
}