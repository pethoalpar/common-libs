package com.pethoalpar.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.entity.Response;
import com.pethoalpar.enums.ResponseStatus;
import com.pethoalpar.exception.AbstractBusinessException;
import com.pethoalpar.service.api.IDeleteService;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
@RestController
public abstract class AbstractDeleteController<T extends AbstractEntity> extends AbstractWriteController<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IDeleteService<T> service;

	@CrossOrigin
	@DeleteMapping(value = "/delete/{id}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> deleteById(@NonNull @PathVariable Integer id, HttpServletRequest request,
			HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Delete entity");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Delete entity:" + id);
		}
		service.deleteById(id);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), null, 0, 0, 0, 0, "", 0),
				HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping(value = "/deleteByIds", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> deleteByIds(@NonNull @RequestBody List<Integer> ids, HttpServletRequest request,
			HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Delete entitities by ids");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Delete entities by ids:" + ids);
		}
		service.deleteByIds(ids);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), null, 0, 0, 0, 0, "", 0),
				HttpStatus.OK);
	}

	@CrossOrigin
	@DeleteMapping(value = "/delete", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<T>> delete(@NonNull @RequestBody T entity, HttpServletRequest request,
			HttpServletResponse response) throws AbstractBusinessException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Delete entity");
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Delete entity:" + entity);
		}
		service.delete(entity);
		watch.stop();
		return new ResponseEntity<>(new Response<T>(ResponseStatus.SUCCESS, watch.getTime(), null, 0, 0, 0, 0, "", 0),
				HttpStatus.OK);
	}

}
