package com.pethoalpar.exception;

/**
 * @author pethoalpar
 *
 */
public class JPAException extends AbstractBusinessException {

	private static final long serialVersionUID = 365645287043838434L;

	public JPAException() {
		super("Database problem", 1005);
	}

}
