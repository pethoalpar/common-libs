package com.pethoalpar.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.repository.IAbstractRepository;
import com.pethoalpar.service.api.IReadService;
import com.pethoalpar.util.QueryUtil;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public abstract class AbstractReadService<T extends AbstractEntity> extends AbstractService<T>
		implements IReadService<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final String UUID = "uuid";

	private static final String ID = "id";

	@Autowired
	protected IAbstractRepository<T> repository;

	@PersistenceContext
	protected EntityManager entityManager;

	protected void preFindAll(Pageable pageable) {

	}

	@Override
	public Page<T> findAll(Pageable pageable) {
		logger.info("Finding all.");
		preFindAll(pageable);
		Page<T> entities = repository.findAll(pageable);
		postFindAll(entities);
		return entities;
	}

	protected void postFindAll(Page<T> entities) {

	}

	protected void preFindAll(Sort sort) {

	}

	@Override
	public List<T> findAll(Sort sort) {
		logger.info("Finding all.");
		preFindAll(sort);
		List<T> entities = repository.findAll(sort);
		postFindAll(entities);
		return entities;
	}

	protected void postFindAll(List<T> entities) {

	}

	protected void preFindAll() {

	}

	@Override
	public List<T> findAll() {
		logger.info("Finding all.");
		preFindAll();
		List<T> entities = repository.findAll();
		postFindAll(entities);
		return entities;
	}

	protected void preFindById(Integer id) {

	}

	@Override
	public T findById(Integer id) throws EntityNotFoundException {
		logger.info("Find by id.");
		if (logger.isDebugEnabled()) {
			logger.debug("Find by id:" + id);
		}
		preFindById(id);
		Optional<T> opt = repository.findById(id);
		if (opt.isPresent()) {
			T entity = opt.get();
			postFindById(entity);
			return entity;
		} else {
			throw new EntityNotFoundException();
		}
	}

	protected void postFindById(T entity) {

	}

	protected void preFind(T entity, Pageable pageable) {

	}

	@Override
	public Page<T> findAllByEntity(T entity, Pageable pageable) {
		logger.info("Finding all by entity");
		logger.debug("Finding all by entity:" + entity);
		preFind(entity, pageable);
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIgnoreNullValues()
				.withIgnorePaths(ID, UUID);
		Example<T> example = Example.of(entity, exampleMatcher);
		Page<T> entities = repository.findAll(example, pageable);
		postFind(entities);
		return entities;
	}

	protected void postFind(Page<T> entities) {

	}

	protected void preFindByIds(List<Integer> ids) {

	}

	@Override
	public List<T> findByIds(List<Integer> ids) {
		logger.info("Find by ids");
		logger.info("Find by ids:" + ids);
		preFindByIds(ids);
		List<T> entities = repository.findAllById(ids);
		postFindByIds(entities);
		return entities;
	}

	protected void postFindByIds(List<T> entities) {

	}

	protected void preFindByEntityAndPeriod(List<Predicate> filterPredicates, Order order) {

	}

	@Override
	public List<T> findByEntityAndPeriod(T entity, Date from, Date to, String dateFieldName, Sort sort) {
		logger.info("Finding by entity and period.");
		logger.debug("From date:" + from);
		logger.debug("To date:" + to);
		logger.debug("Date field name:" + dateFieldName);
		logger.debug("Sort: " + sort);
		Class<T> clazz = this.getGenericType();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
		Root<T> root = query.from(clazz);

		List<Predicate> filterPredicates = QueryUtil.<T>buildPredicatesFromEntity(entity, dateFieldName,
				criteriaBuilder, root, clazz);

		filterPredicates.add(criteriaBuilder.between(root.get(dateFieldName), criteriaBuilder.literal(from),
				criteriaBuilder.literal(to)));

		Order order = QueryUtil.<T>orderBuilder(sort, criteriaBuilder, root);

		preFindByEntityAndPeriod(filterPredicates, order);

		query.where(filterPredicates.toArray(new Predicate[filterPredicates.size()]));
		query.orderBy(order);
		return entityManager.createQuery(query).getResultList();
	}

	protected void preFindByParentId(List<Predicate> filterPredicates, Order order) {

	}

	@Override
	public List<T> findByParentId(Integer parentId, String parentName, Sort sort) {
		logger.info("Find by parent id");
		logger.debug("Parent id:" + parentId);
		logger.debug("Parent entity name:" + parentName);
		logger.debug("Sort: " + sort);
		Class<T> clazz = this.getGenericType();
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<T> query = criteriaBuilder.createQuery(clazz);
		Root<T> root = query.from(clazz);

		List<Predicate> filterPredicates = new ArrayList<>();

		Path<Object> parentPath = root.join(parentName).get(ID);

		filterPredicates.add(criteriaBuilder.equal(parentPath, parentId));

		Order order = QueryUtil.<T>orderBuilder(sort, criteriaBuilder, root);

		preFindByParentId(filterPredicates, order);

		query.where(filterPredicates.toArray(new Predicate[filterPredicates.size()]));
		query.orderBy(order);
		return entityManager.createQuery(query).getResultList();
	}

}
