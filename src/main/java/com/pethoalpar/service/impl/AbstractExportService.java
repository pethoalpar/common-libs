package com.pethoalpar.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.enums.ExportFormats;
import com.pethoalpar.generator.CSVGenerator;
import com.pethoalpar.service.api.IExportService;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public abstract class AbstractExportService<T extends AbstractEntity> extends AbstractReadService<T>
		implements IExportService<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public byte[] export(ExportFormats format, List<Integer> ids) {
		logger.info("Exporting in " + format);
		List<T> entities = repository.findAllById(ids);
		return this.exportByType(format, entities);
	}

	@Override
	public byte[] export(ExportFormats format, T entity, Pageable pageable) {
		logger.info("Exporting in " + format);
		Page<T> entities = this.findAllByEntity(entity, pageable);
		return this.exportByType(format, entities.getContent());
	}

	private byte[] exportByType(ExportFormats format, List<T> entities) {

		switch (format) {
		case PDF:
			break;
		case EXCEL:
			break;

		case CSV:
			CSVGenerator<T> csvGenerator = new CSVGenerator<>();
			try {
				return csvGenerator.generateCSV(entities).getBytes();
			} catch (IllegalAccessException e) {
				logger.warn("", e);
			}
			break;
		case JSON:
			break;
		default:
			break;
		}

		return "SUCCESS".getBytes();
	}

}
