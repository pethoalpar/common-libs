package com.pethoalpar.exception;

/**
 * @author pethoalpar
 *
 */
public class InvalidEnumException extends AbstractBusinessException {

	private static final long serialVersionUID = -6484225569268656437L;

	public InvalidEnumException() {
		super("Invalid enum value", 1001);
	}

}
