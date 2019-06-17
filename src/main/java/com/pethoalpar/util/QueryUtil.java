package com.pethoalpar.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;

/**
 * @author pethoalpar
 *
 */
public class QueryUtil {

	private static Logger logger = LoggerFactory.getLogger(QueryUtil.class);

	private QueryUtil() {

	}

	public static <T> Order orderBuilder(Sort sort, CriteriaBuilder criteriaBuilder, Root<T> root) {
		logger.info("Building order.");
		if (sort == null || sort.isEmpty()) {
			return criteriaBuilder.asc(root.get("id"));
		}

		org.springframework.data.domain.Sort.Order order = sort.iterator().next();
		if (order.isAscending()) {
			return criteriaBuilder.asc(root.get(order.getProperty()));
		} else {
			return criteriaBuilder.desc(root.get(order.getProperty()));
		}

	}

	public static final <T> List<Predicate> buildPredicatesFromEntity(T entity, String dateFieldName,
			CriteriaBuilder criteriaBuilder, Root<T> root, Class<T> clazz) {
		logger.info("Building criterias from entity:" + clazz.getSimpleName());
		List<Predicate> filterPredicates = new ArrayList<>();

		if (entity == null) {
			return filterPredicates;
		}
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; ++i) {
			Field field = fields[i];
			field.setAccessible(true);
			if (dateFieldName.equalsIgnoreCase(field.getName()) || "id".equalsIgnoreCase(field.getName())) {
				continue;
			}

			try {
				Object value = field.get(entity);
				if (value != null) {
					logger.debug("Adding field to criteria. Field name:" + field.getName());
					Predicate p = criteriaBuilder.equal(root.get(field.getName()), value);
					filterPredicates.add(p);
				}
			} catch (Exception e) {
				logger.warn("", e);
			}
		}
		return filterPredicates;
	}

}
