package com.pethoalpar.service.api;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.pethoalpar.entity.AbstractEntity;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public interface IReadService<T extends AbstractEntity> {

	Page<T> findAll(Pageable pageable);

	T findById(Integer id);

	Page<T> findAllByEntity(T entity, Pageable pageable);

	List<T> findByIds(List<Integer> ids);

	List<T> findByEntityAndPeriod(T entity, Date from, Date to, String dateFieldName, Sort sort);

	List<T> findByParentId(Integer parentId, String parentName, Sort sort);

	List<T> findAll(Sort sort);

	List<T> findAll();

}
