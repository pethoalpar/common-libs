package com.pethoalpar.service.api;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.enums.ExportFormats;

/**
 * @author pethoalpar
 *
 * @param <T>
 */
public interface IExportService<T extends AbstractEntity> extends IReadService<T> {

	byte[] export(ExportFormats format, List<Integer> ids);

	byte[] export(ExportFormats format, T entity, Pageable pageable);

}
