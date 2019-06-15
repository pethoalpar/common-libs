package com.pethoalpar.controller;

import org.springframework.core.GenericTypeResolver;

import com.pethoalpar.entity.AbstractEntity;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public abstract class AbstractController<T extends AbstractEntity> {

	@SuppressWarnings("unchecked")
	protected Class<T> getGenericType() {
		return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractController.class);
	}

}
