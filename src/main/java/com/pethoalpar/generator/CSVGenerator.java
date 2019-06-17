package com.pethoalpar.generator;

import java.lang.reflect.Field;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.pethoalpar.entity.AbstractEntity;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public class CSVGenerator<T extends AbstractEntity> {

	private static final String ID = "id";
	private static final String NEW_LINE = "\n";
	private static final String SEPARATOR = ",";

	public String generateCSV(List<T> entities) throws IllegalAccessException {

		if (CollectionUtils.isEmpty(entities)) {
			return "";
		}
		Class<? extends AbstractEntity> clazz = entities.get(0).getClass();
		StringBuilder retSb = new StringBuilder();
		bildTitle(entities, retSb);
		buildHeader(clazz, retSb);
		buildBody(entities, retSb);

		return retSb.toString();
	}

	private void buildBody(List<T> entities, StringBuilder retSb) throws IllegalAccessException {
		for (AbstractEntity e : entities) {
			for (int i = 0; i < e.getClass().getDeclaredFields().length; ++i) {
				Field field = e.getClass().getDeclaredFields()[i];
				field.setAccessible(true);
				if (field.getName().equalsIgnoreCase(ID) || List.class.isAssignableFrom(field.getType())) {
					continue;
				}
				retSb.append(field.get(e));
				if (i < e.getClass().getDeclaredFields().length - 1) {
					retSb.append(SEPARATOR);
				}
			}
			retSb.append(NEW_LINE);
		}
	}

	private void bildTitle(List<T> entities, StringBuilder retSb) {
		retSb.append(entities.get(0).getClass().getSimpleName());
		retSb.append(NEW_LINE);
	}

	private void buildHeader(Class<? extends AbstractEntity> clazz, StringBuilder retSb) {
		for (int i = 0; i < clazz.getDeclaredFields().length; ++i) {
			Field field = clazz.getDeclaredFields()[i];
			field.setAccessible(true);
			if (field.getName().equalsIgnoreCase(ID) || List.class.isAssignableFrom(field.getType())) {
				continue;
			}
			retSb.append(field.getName());
			if (i < clazz.getDeclaredFields().length - 1) {
				retSb.append(SEPARATOR);
			}
		}
		retSb.append(NEW_LINE);
	}

}
