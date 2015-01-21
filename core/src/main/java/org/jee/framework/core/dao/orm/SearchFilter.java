package org.jee.framework.core.dao.orm;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.jee.framework.core.utils.CollectionUtils;
import org.jee.framework.core.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class SearchFilter {

	public enum Operator {
		EQ, LIKE, GT, LT, GTE, LTE
	}

	public String fieldName;
	public Object value;
	public Operator operator;

	public SearchFilter(String fieldName, Operator operator, Object value) {
		this.fieldName = fieldName;
		this.value = value;
		this.operator = operator;
	}

	/**
	 * searchParams中key的格式为OPERATOR_FIELDNAME
	 */
	public static Map<String, SearchFilter> parse(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = Maps.newHashMap();

		for (Entry<String, Object> entry : searchParams.entrySet()) {
			// 过滤掉空值
			String key = entry.getKey();
			Object value = entry.getValue();
			if (StringUtils.isBlank((String) value)) {
				continue;
			}

			// 拆分operator与filedAttribute
			String[] names = StringUtils.split(key, "_");
			if (names.length != 2) {
				throw new IllegalArgumentException(key + " is not a valid search filter name");
			}
			String filedName = names[1];
			Operator operator = Operator.valueOf(names[0]);

			// 创建searchFilter
			SearchFilter filter = new SearchFilter(filedName, operator, value);
			filters.put(key, filter);
		}

		return filters;
	}
	
	/**
	 * <pre>
	 *     For example:
	 *
	 *     CriteriaQuery&#060;Person&#062; q = cb.createQuery(Person.class);
	 *     Root&#060;Person&#062; p = q.from(Person.class);
	 *     q.select(p)
	 *      .where(cb.isMember("joe",
	 *                         p.&#060;Set&#060;String&#062;&#062;get("nicknames")));
	 *
	 *     rather than:
	 *
	 *     CriteriaQuery&#060;Person&#062; q = cb.createQuery(Person.class);
	 *     Root&#060;Person&#062; p = q.from(Person.class);
	 *     Path&#060;Set&#060;String&#062;&#062; nicknames = p.get("nicknames");
	 *     q.select(p)
	 *      .where(cb.isMember("joe", nicknames));
	 *  </pre>
	 * @param <T>
	 * @param searchFilters
	 * @param root
	 * @param query
	 * @param builder
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> Predicate toPredicate(final Class<T> entityClazz, final Collection<SearchFilter> searchFilters, Root<T> root, CriteriaQuery<T> query, CriteriaBuilder builder) {
		if (CollectionUtils.isNotEmpty(searchFilters)) {

			final List<Predicate> predicates = Lists.newArrayList();
			for (SearchFilter filter : searchFilters) {
				// nested path translate, 如Task的名为"user.name"的filedName, 转换为Task.user.name属性
				String[] names = StringUtils.split(filter.fieldName, ".");
				Path<?> expression = root.get(names[0]);
				for (int i = 1; i < names.length; i++) {
					expression = expression.get(names[i]);
				}

				// logic operator
				switch (filter.operator) {
				case EQ:
					predicates.add(builder.equal(expression, filter.value));
					break;
				case LIKE:
					predicates.add(builder.like((Expression<String>) expression, "%" + filter.value + "%"));
					break;
				case GT:
					predicates.add(builder.greaterThan((Expression<? extends Comparable>)expression, (Comparable) filter.value));
					break;
				case LT:
					predicates.add(builder.lessThan((Expression<? extends Comparable>)expression, (Comparable) filter.value));
					break;
				case GTE:
					predicates.add(builder.greaterThanOrEqualTo((Expression<? extends Comparable>)expression, (Comparable) filter.value));
					break;
				case LTE:
					predicates.add(builder.lessThanOrEqualTo((Expression<? extends Comparable>)expression, (Comparable) filter.value));
					break;
				}
			}

			// 将所有条件用 and 联合起来
			if (!predicates.isEmpty()) {
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		}

		return builder.conjunction();
	}
}
