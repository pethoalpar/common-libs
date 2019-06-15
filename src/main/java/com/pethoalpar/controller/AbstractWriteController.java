package com.pethoalpar.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.entity.Response;
import com.pethoalpar.enums.ResponseStatus;
import com.pethoalpar.exception.AbstractBusinessException;
import com.pethoalpar.service.api.IWriteService;

/**
 * @author alpar.petho
 *
 */
@RestController
public abstract class AbstractWriteController<T extends AbstractEntity> extends AbstractExportController<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IWriteService<T> service;

	@CrossOrigin
	@PostMapping(value = "/insert", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> insert(@NonNull @Valid @RequestBody T entity, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Insert entity");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Insert entity:" + entity);
		}
		T e = service.insert(entity);
		List<T> allEntities = new ArrayList<>();
		allEntities.add(e);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities,
				allEntities.size(), 0, 0, allEntities.size(), "", 0), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = "/insertList", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> insertList(@NonNull @RequestBody List<T> entities, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Insert entitities");
		}
		if (logger.isDebugEnabled()) {
			logger.info("Insert entitities:" + entities);
		}
		List<T> savedEntities = service.insert(entities);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), savedEntities,
				savedEntities.size(), 0, 0, savedEntities.size(), "", 0), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = "/update", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> update(@NonNull @RequestBody T entity, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Find by entity");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Find by entity:" + entity);
		}
		T e = service.update(entity);
		List<T> allEntities = new ArrayList<>();
		allEntities.add(e);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), allEntities,
				allEntities.size(), 0, 0, allEntities.size(), "", 0), HttpStatus.OK);
	}

	@CrossOrigin
	@PostMapping(value = "/updateList", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> update(@NonNull @RequestBody List<T> entities, HttpServletRequest request,
			HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Update entities");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Update entities:" + entities);
		}
		List<T> savedEntities = service.update(entities);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), savedEntities,
				savedEntities.size(), 0, 0, savedEntities.size(), "", 0), HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value = "/init", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> init(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Initialize");
		StopWatch watch = new StopWatch();
		watch.start();

		try {
			service.init(this.getGenericType());
		} catch (AbstractBusinessException e) {
			logger.warn(e.getErrorMessage(), e);
			return new ResponseEntity<>(new Response<T>(ResponseStatus.FAILED, watch.getTime(), null, 0, 0, 0, 0,
					e.getMessage(), e.getErrorCode()), HttpStatus.FORBIDDEN);
		}
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), null, 0, 0, 0, 0, "", 0),
				HttpStatus.OK);
	}

}
