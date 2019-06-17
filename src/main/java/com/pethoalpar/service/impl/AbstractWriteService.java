package com.pethoalpar.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.exception.AbstractBusinessException;
import com.pethoalpar.exception.EntityNotFoundException;
import com.pethoalpar.service.api.IWriteService;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public abstract class AbstractWriteService<T extends AbstractEntity> extends AbstractExportService<T>
		implements IWriteService<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void preInsert(T entity) {

	}

	@Override
	public T insert(T entity) throws Exception {
		logger.info("Insert new " + entity.getClass().getSimpleName());
		preInsert(entity);
		T savedEntity = repository.save(entity);
		postInsert(savedEntity);
		return savedEntity;
	}

	public void postInsert(T entity) {

	}

	public void preInsert(List<T> entities) {

	}

	@Override
	public List<T> insert(List<T> entities) throws Exception {
		logger.info("Insert new " + entities.getClass().getSimpleName());
		preInsert(entities);
		List<T> savedEntities = repository.saveAll(entities);
		postInsert(savedEntities);
		return savedEntities;
	}

	public void postInsert(List<T> entities) {

	}

	public void preUpdate(T entity) {

	}

	@Override
	public T update(T entity) throws EntityNotFoundException {
		logger.info("Updating " + entity.getClass().getSimpleName());
		preUpdate(entity);
		if (!repository.existsById(entity.getId())) {
			throw new EntityNotFoundException();
		}
		T savedEntity = repository.save(entity);
		postUpdate(savedEntity);
		return savedEntity;
	}

	public void postUpdate(T entity) {

	}

	public void preUpdate(List<T> entities) {

	}

	@Override
	@Transactional
	public List<T> update(List<T> entities) throws EntityNotFoundException {
		logger.info("Update " + entities.getClass().getSimpleName());
		preInsert(entities);
		List<T> savedEntities = new ArrayList<>();
		for (T e : entities) {
			savedEntities.add(this.update(e));
		}
		postInsert(savedEntities);
		return savedEntities;
	}

	public void postUpdate(List<T> entities) {

	}

	@Override
	public void init(Class<T> clazz) throws AbstractBusinessException {
		logger.info("Initialize : " + clazz.getSimpleName());
		List<T> entities = this.getContent(clazz);
		for (T entity : entities) {
			try {
				this.insert(entity);
			} catch (Exception e) {
				logger.warn("", e);
			}
		}
	}

}
