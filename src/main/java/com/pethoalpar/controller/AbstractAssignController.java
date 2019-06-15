package com.pethoalpar.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.entity.Response;
import com.pethoalpar.enums.ResponseStatus;
import com.pethoalpar.exception.AbstractBusinessException;
import com.pethoalpar.service.api.IAssignService;

/**
 * @author pethoalpar
 *
 * @param <C>
 * @param <P>
 */
@RestController
public abstract class AbstractAssignController<C extends AbstractEntity, P extends AbstractEntity>
		extends AbstractController<C> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IAssignService<C, P> assignService;

	@CrossOrigin
	@PostMapping(value = "/assign/{parentId}", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Response<P>> assign(@PathVariable("parentId") Integer parentId,
			@RequestBody List<Integer> childIds, HttpServletRequest request, HttpServletResponse response)
			throws AbstractBusinessException, IllegalAccessException, InvocationTargetException {
		StopWatch watch = new StopWatch();
		watch.start();
		if (logger.isInfoEnabled()) {
			logger.info("Find all");
		}
		P parent = assignService.assign(parentId, childIds);
		List<P> retList = new ArrayList<>();
		retList.add(parent);
		watch.stop();
		return new ResponseEntity<>(new Response<P>(ResponseStatus.SUCCESS, watch.getTime(), retList, retList.size(), 0,
				1, retList.size(), "", 0), HttpStatus.OK);
	}

}
