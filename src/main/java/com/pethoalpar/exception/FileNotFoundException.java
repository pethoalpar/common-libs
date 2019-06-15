package com.pethoalpar.exception;

/**
 * @author pethoalpar
 *
 */
public class FileNotFoundException extends AbstractBusinessException {

	private static final long serialVersionUID = -2708265433496039922L;

	public FileNotFoundException(String fileName) {
		super("File not found: " + fileName, 1002);
	}

}
