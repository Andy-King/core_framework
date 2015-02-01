package org.jee.framework.core.dao.orm.cfg;

import org.hibernate.AssertionFailure;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.util.StringHelper;

/**
 * <pre>
 * 仿造DefaultNamingStrategy实现一个自己的table和column的整体命名策略。
 * 设置是否转换className与propertyName为分词后加下划线的形式。如：AaaBbb->aaa_bbb;
 *  aaaBbb->aaa_bbb。
 *  {@code
 *  private boolean _addUnderScores = false;
 *  <!-- 配置SessionFactory,整合Hibernate -->
 *  <bean id="sessionFactory" class="org.springframework.orm.hibernate3.annotation.AnnotationSessionFactoryBean">
 *      <property name="dataSource" ref="dataSourceProxy" />
 *      <property name="namingStrategy">
 *          <bean class="org.jee.framework.core.dao.orm.cfg.LocalNamingStrategy">
 *              <property name="addUnderScores" value="true"/>
 *          </bean>
 *      </property>
 *      ...
 *  </bean>    
 *  }
 * </pre>
 * 
 * @author AK
 */
public class LocalNamingStrategy implements NamingStrategy {
	/**
	 * 设置表名前缀。
	 */
	private String _tablePrefix = "";
	/**
	 * 设置列名前缀。
	 */
	private String _columnPrefix = "";
	/**
	 * 设置是否转换className与propertyName为分词后加下划线的形式。如：AaaBbb->aaa_bbb;
	 * aaaBbb->aaa_bbb。
	 */
	private boolean _addUnderScores = false;

	public void setTablePrefix(String tablePrefix) {
		if (StringHelper.isNotEmpty(tablePrefix))
			_tablePrefix = tablePrefix;
	}

	public void setColumnPrefix(String columnPrefix) {
		if (StringHelper.isNotEmpty(columnPrefix))
			_columnPrefix = columnPrefix;
	}

	public void setAddUnderScores(boolean addUnderScores) {
		_addUnderScores = addUnderScores;
	}

	/**
	 * A convenient singleton instance
	 */
	public static final NamingStrategy INSTANCE = new LocalNamingStrategy();

	/**
	 * Return the unqualified class name, mixed case converted to underscores
	 */
	public String classToTableName(String className) {
		return addPrefixes(StringHelper.unqualify(className), _tablePrefix);
	}

	/**
	 * Return the full property path with underscore seperators, mixed case
	 * converted to underscores
	 */
	public String propertyToColumnName(String propertyName) {
		return addPrefixes(StringHelper.unqualify(propertyName), _columnPrefix);
	}

	/**
	 * Convert mixed case to underscores
	 */
	public String tableName(String tableName) {
		return addPrefixes(tableName, "");
	}

	/**
	 * Convert mixed case to underscores
	 */
	public String columnName(String columnName) {
		return addPrefixes(columnName, "");
	}

	private String addPrefixes(String name, String prefix) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(prefix);
		if (_addUnderScores) {
			buffer.append(addUnderscores(name));
		} else {
			buffer.append(name);
		}
		return buffer.toString();
	}

	private String addUnderscores(String name) {
		StringBuffer buf = new StringBuffer(name.replace('.', '_'));
		// for (int i = 1; i < buf.length() - 1; i++) {
		// if (Character.isLowerCase(buf.charAt(i - 1))
		// && Character.isUpperCase(buf.charAt(i))
		// && Character.isLowerCase(buf.charAt(i + 1))) {
		// buf.insert(i++, '_');
		// }
		// }
		for (int i = 1; i < buf.length() - 1; i++) {
			if ((Character.isUpperCase(buf.charAt(i)))
					&& ((Character.isLowerCase(buf.charAt(i - 1))) || (Character
							.isLowerCase(buf.charAt(i + 1))))) {

				{
					buf.insert(i++, '_');
				}
			}
		}
		return buf.toString().toLowerCase();
	}

	public String collectionTableName(String ownerEntityTable,
			String associatedEntityTable, String propertyName) {
		return tableName(ownerEntityTable + '_' + propertyName);
	}

	/**
	 * Return the argument
	 */
	public String joinKeyColumnName(String joinedColumn, String joinedTable) {
		return columnName(joinedColumn);
	}

	/**
	 * Return the property name or propertyTableName
	 */
	public String foreignKeyColumnName(String propertyName,
			String propertyTableName, String referencedColumnName) {
		String header = propertyName != null ? StringHelper
				.unqualify(propertyName) : propertyTableName;
		if (header == null)
			throw new AssertionFailure("NammingStrategy not properly filled");
		return columnName(header + "_" + referencedColumnName); // + "_" +
		// referencedColumnName
		// not used for
		// backward compatibility
	}

	/**
	 * Return the column name or the unqualified property name
	 */
	public String logicalColumnName(String columnName, String propertyName) {
		return StringHelper.isNotEmpty(columnName) ? columnName : StringHelper
				.unqualify(propertyName);
	}

	/**
	 * Returns either the table name if explicit or if there is an associated
	 * table, the concatenation of owner entity table and associated table
	 * otherwise the concatenation of owner entity table and the unqualified
	 * property name
	 */
	public String logicalCollectionTableName(String tableName,
			String ownerEntityTable, String associatedEntityTable,
			String propertyName) {
		if (tableName != null) {
			return tableName;
		} else {
			// use of a stringbuffer to workaround a JDK bug
			return new StringBuffer(ownerEntityTable).append("_").append(
					associatedEntityTable != null ? associatedEntityTable
							: StringHelper.unqualify(propertyName)).toString();
		}
	}

	/**
	 * Return the column name if explicit or the concatenation of the property
	 * name and the referenced column
	 */
	public String logicalCollectionColumnName(String columnName,
			String propertyName, String referencedColumn) {
		return StringHelper.isNotEmpty(columnName) ? columnName : StringHelper
				.unqualify(propertyName)
				+ "_" + referencedColumn;
	}

	public String collectionTableName(String arg0, String arg1, String arg2,
			String arg3, String arg4) {
		return null;
	}

	public String foreignKeyColumnName(String arg0, String arg1, String arg2,
			String arg3) {
		return null;
	}
}