package com.pethoalpar.exception;

/**
 * @author pethoalpar
 *
 */
public abstract class AbstractBusinessException extends Exception {

	private static final long serialVersionUID = -8308626791014122246L;

	private final String errorMessage;
	private final int errorCode;

	public AbstractBusinessException(String errorMessage, int errorCode) {
		super(errorMessage);
		this.errorMessage = errorMessage;
		this.errorCode = errorCode;

	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public int getErrorCode() {
		return this.errorCode;
	}

}
