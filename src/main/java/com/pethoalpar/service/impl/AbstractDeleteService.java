package com.pethoalpar.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.service.api.IDeleteService;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public abstract class AbstractDeleteService<T extends AbstractEntity> extends AbstractWriteService<T>
		implements IDeleteService<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void preDeleteById(Integer id) {

	}

	@Override
	public void deleteById(Integer id) {

		logger.info("Delete by id.");
		logger.debug("Delete by id:" + id);

		preDeleteById(id);
		repository.deleteById(id);
		postDeleteById(id);
	}

	public void postDeleteById(Integer id) {

	}

	public void preDeleteByIds(List<Integer> ids) {

	}

	@Override
	@Transactional
	public void deleteByIds(List<Integer> ids) {
		logger.info("Delete by ids.");
		logger.debug("Delete by ids:" + ids);
		preDeleteByIds(ids);
		for (Integer id : ids) {
			this.deleteById(id);
		}
		postDeleteByIds(ids);
	}

	public void postDeleteByIds(List<Integer> ids) {

	}

	public void preDelete(T entity) {

	}

	@Override
	public void delete(T entity) {
		logger.info("Delete by id.");
		logger.debug("Delete by");

		preDelete(entity);
		repository.delete(entity);
		postDelete(entity);
	}

	public void postDelete(T entity) {

	}

}
