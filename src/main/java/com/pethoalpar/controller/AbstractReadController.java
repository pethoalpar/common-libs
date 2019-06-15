package com.pethoalpar.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.entity.Response;
import com.pethoalpar.enums.ResponseStatus;
import com.pethoalpar.exception.AbstractBusinessException;
import com.pethoalpar.service.api.IReadService;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
@RestController
public abstract class AbstractReadController<T extends AbstractEntity> extends AbstractController<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IReadService<T> service;

	@CrossOrigin
	@GetMapping(value = "/findAll", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> findAll(Pageable pageable, HttpServletRequest request,
			HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Find all");
		}
		if (pageable.isUnpaged() && pageable.getSort().isUnsorted()) {
			List<T> allEntities = service.findAll();
			watch.stop();
			return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities,
					allEntities.size(), 0, 1, allEntities.size(), "", 0), HttpStatus.OK);
		}
		if (pageable.isUnpaged() && pageable.getSort().isSorted()) {
			List<T> allEntities = service.findAll(pageable.getSort());
			watch.stop();
			return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities,
					allEntities.size(), 0, 1, allEntities.size(), "", 0), HttpStatus.OK);
		}
		Page<T> allEntities = service.findAll(pageable);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities.getContent(),
				allEntities.getTotalElements(), allEntities.getPageable().getPageNumber(), allEntities.getTotalPages(),
				allEntities.getNumberOfElements(), "", 0), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value = "/findById/{id}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> findById(@NonNull @PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Find by id");
		}
		T entity = service.findById(id);
		List<T> allEntities = new ArrayList<>();
		allEntities.add(entity);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities,
				allEntities.size(), 1, 0, allEntities.size(), "", 0), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = "/findAllByEntity", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> findAllByEntity(@NonNull @RequestBody T entity, Pageable pageable,
			HttpServletRequest request, HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Find by entity");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Find by entity:" + entity);
		}

		Page<T> allEntities = service.findAllByEntity(entity, pageable);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities.getContent(),
				allEntities.getTotalElements(), allEntities.getPageable().getPageNumber(), allEntities.getTotalPages(),
				allEntities.getNumberOfElements(), "", 0), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = "/findAllByEntityPeriod", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> findAllByEntityPeriod(@NonNull @RequestBody T entity,
			@RequestParam @NonNull Long from, @RequestParam @NonNull Long to,
			@RequestParam @NonNull String dateFieldName, Sort sort, HttpServletRequest request,
			HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Find by entity and period");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Find by entity and period:" + entity);
		}
		List<T> allEntities = service.findByEntityAndPeriod(entity, new Date(from), new Date(to), dateFieldName, sort);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities,
				allEntities.size(), 0, 1, allEntities.size(), "", 0), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = "/findByIds", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> findAByIds(@NonNull @RequestBody List<Integer> ids, HttpServletRequest request,
			HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Find by ids");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Find by ids:" + ids);
		}
		List<T> allEntities = service.findByIds(ids);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities,
				allEntities.size(), 0, 1, allEntities.size(), "", 0), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value = "/findByParentId/{parentId}/{parentName}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> findByParentId(@NonNull @PathVariable Integer parentId,
			@NonNull @PathVariable String parentName, Sort sort, HttpServletRequest request,
			HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Findby parent id");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Findby parent id:" + parentId);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Findby parent parentName:" + parentName);
		}

		List<T> allEntities = service.findByParentId(parentId, parentName, sort);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities,
				allEntities.size(), 0, 1, allEntities.size(), "", 0), HttpStatus.OK);
	}

}
