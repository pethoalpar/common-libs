package com.pethoalpar.exception;

/**
 * @author pethoalpar
 *
 */
public class FileIsEmptyException extends AbstractBusinessException {

	private static final long serialVersionUID = -1740589299560565282L;

	public FileIsEmptyException(String fileName) {
		super("File is empty: " + fileName, 1003);
	}

}
