package com.pethoalpar.controller;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.enums.ExportFormats;
import com.pethoalpar.exception.AbstractBusinessException;
import com.pethoalpar.exception.InvalidEnumException;
import com.pethoalpar.service.api.IExportService;
import com.pethoalpar.util.MimeTypeUtil;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
@RestController
public abstract class AbstractExportController<T extends AbstractEntity> extends AbstractReadController<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IExportService<T> service;

	@CrossOrigin
	@PostMapping(value = "/exportbyids/{format}")
	@ResponseBody
	public ResponseEntity<byte[]> export(@NonNull @RequestBody List<Integer> ids, @PathVariable String format,
			HttpServletRequest request, HttpServletResponse response) throws AbstractBusinessException {
		if (!ExportFormats.containsFormat(format)) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		try {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(MimeTypeUtil.getMimeType(format)))
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"" + UUID.randomUUID().toString() + "." + format + "\"")
					.body(service.export(ExportFormats.getByFormat(format), ids));
		} catch (InvalidEnumException e) {
			logger.warn("Invalid enum", e);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@CrossOrigin
	@PostMapping(value = "/export/{format}")
	@ResponseBody
	public ResponseEntity<byte[]> export(@NonNull @RequestBody T entity, @PathVariable String format, Pageable pageable,
			HttpServletRequest request, HttpServletResponse response) throws AbstractBusinessException {
		if (!ExportFormats.containsFormat(format)) {
			return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
		}
		try {
			return ResponseEntity.ok().contentType(MediaType.parseMediaType(MimeTypeUtil.getMimeType(format)))
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"" + UUID.randomUUID().toString() + "." + format + "\"")
					.body(service.export(ExportFormats.getByFormat(format), entity, pageable));
		} catch (InvalidEnumException e) {
			logger.warn("Invalid enum", e);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
