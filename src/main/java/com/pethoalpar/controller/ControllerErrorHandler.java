package com.pethoalpar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.pethoalpar.entity.Response;
import com.pethoalpar.enums.ResponseStatus;
import com.pethoalpar.exception.AbstractBusinessException;

/**
 * @author pethoalpar
 *
 */
@ControllerAdvice
public class ControllerErrorHandler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler({ AbstractBusinessException.class })
	public ResponseEntity<Response<String>> handleBusinessException(AbstractBusinessException e) {
		logger.error(e.getErrorMessage());
		return new ResponseEntity<>(
				new Response<>(ResponseStatus.FAILED, 100, null, 0, 0, 0, 0, e.getMessage(), e.getErrorCode()),
				HttpStatus.FORBIDDEN);
	}

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Response<String>> handleException(Exception e) {
		logger.error(e.getMessage());
		return new ResponseEntity<>(
				new Response<>(ResponseStatus.FAILED, 100, null, 0, 0, 0, 0, e.getClass().getSimpleName(), -1),
				HttpStatus.FORBIDDEN);
	}

}
