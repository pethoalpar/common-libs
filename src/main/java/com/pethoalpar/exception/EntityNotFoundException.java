package com.pethoalpar.exception;

/**
 * @author pethoalpar
 *
 */
public class EntityNotFoundException extends AbstractBusinessException {

	private static final long serialVersionUID = -6629883122578797688L;

	public EntityNotFoundException() {
		super("Entity not found ", 1008);
	}

}
