package com.pethoalpar.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pethoalpar.entity.AbstractEntity;
import com.pethoalpar.exception.AbstractBusinessException;
import com.pethoalpar.exception.AssignException;
import com.pethoalpar.exception.EntityNotFoundException;
import com.pethoalpar.repository.IAbstractRepository;
import com.pethoalpar.service.api.IAssignService;

/**
 * @author pethoalpar
 *
 * @param <C>
 * @param <P>
 */
public abstract class AbstractAssignService<C extends AbstractEntity, P extends AbstractEntity>
		implements IAssignService<C, P> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private IAbstractRepository<C> childRepository;

	@Autowired
	private IAbstractRepository<P> parentRepository;

	@Override
	public P assign(Integer parentId, List<Integer> childIds)
			throws AbstractBusinessException, IllegalAccessException, InvocationTargetException {
		logger.info("Assign entity");
		Optional<P> parentOPt = parentRepository.findById(parentId);
		if (!parentOPt.isPresent()) {
			throw new EntityNotFoundException();
		}
		P parent = parentOPt.get();

		List<C> childs = childRepository.findAllById(childIds);
		Method[] methods = parent.getClass().getDeclaredMethods();
		Method method = null;
		for (Method m : methods) {
			if (m.getName().contains("set") && m.getParameterCount() == 1
					&& m.getParameters()[0].getType().isInstance(childs)) {
				method = m;
			}
		}
		if (method != null) {
			method.setAccessible(true);
			method.invoke(parent, childs);
			return parentRepository.save(parent);
		} else {
			throw new AssignException("method not found");
		}
	}

}
