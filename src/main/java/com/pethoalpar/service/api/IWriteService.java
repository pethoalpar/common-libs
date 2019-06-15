package com.pethoalpar.service.api;

import java.util.List;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.exception.AbstractBusinessException;
import com.pethoalpar.exception.EntityNotFoundException;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public interface IWriteService<T extends AbstractEntity> extends IExportService<T> {

	T insert(T entity) throws Exception;

	List<T> insert(List<T> entities) throws Exception;

	T update(T entity) throws EntityNotFoundException;

	List<T> update(List<T> entities) throws EntityNotFoundException;

	void init(Class<T> clazz) throws AbstractBusinessException;

}
