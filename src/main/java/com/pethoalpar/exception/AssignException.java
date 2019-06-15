package com.pethoalpar.exception;

/**
 * @author pethoalpar
 *
 */
public class AssignException extends AbstractBusinessException {

	private static final long serialVersionUID = -9077609501173977574L;

	public AssignException(String problem) {
		super("Can not assign: " + problem, 1009);
	}

}
