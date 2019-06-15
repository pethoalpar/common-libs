package com.pethoalpar.service.api;

import java.util.List;

import com.pethoalpar.entity.AbstractEntity;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public interface IDeleteService<T extends AbstractEntity> extends IWriteService<T> {

	void deleteById(Integer id);

	void deleteByIds(List<Integer> ids);

	void delete(T entity);

}
