package com.pethoalpar.service.api;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.exception.AbstractBusinessException;

/**
 * @author pethoalpar
 *
 * @param <C>
 * @param <P>
 */
public interface IAssignService<C extends AbstractEntity, P extends AbstractEntity> {

	P assign(Integer parentId, List<Integer> childIds)
			throws AbstractBusinessException, IllegalAccessException, InvocationTargetException;

}
