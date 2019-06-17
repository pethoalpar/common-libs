package com.pethoalpar.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.GenericTypeResolver;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.exception.AbstractBusinessException;
import com.pethoalpar.exception.FileNotFoundException;
import com.pethoalpar.util.FileUtil;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public abstract class AbstractService<T extends AbstractEntity> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationContext context;

	protected ApplicationContext getApplicationContext() {
		return context;
	}

	@SuppressWarnings("unchecked")
	protected Class<T> getGenericType() {
		return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractService.class);
	}

	public List<T> getContent(Class<T> clazz) throws AbstractBusinessException {
		logger.info("Converting json to entity.");
		String fileName = "idl/" + clazz.getSimpleName() + ".json";
		InputStream is;
		try {
			ClassPathResource resource = new ClassPathResource(fileName);
			is = resource.getInputStream();
			String fileContent = StreamUtils.copyToString(is, StandardCharsets.UTF_8);
			logger.info("Processing file: " + fileName);
			logger.debug("Converting json to entity:" + fileContent);
			List<T> newEntities = FileUtil.<T>processFile(fileContent, clazz);
			logger.debug("New entities: " + newEntities);
			return newEntities;
		} catch (IOException e) {
			logger.error("Can not read file", e);
			throw new FileNotFoundException(fileName);
		}
	}

}
