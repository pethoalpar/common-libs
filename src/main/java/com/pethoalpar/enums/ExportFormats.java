package com.pethoalpar.enums;

import com.pethoalpar.exception.InvalidEnumException;

/**
 * @author pethoalpar
 *
 */
public enum ExportFormats {

	PDF("pdf"), EXCEL("excel"), CSV("csv"), JSON("json");

	private String format;

	public String getFormat() {
		return format;
	}

	private ExportFormats(String format) {
		this.format = format;
	}

	public static ExportFormats getByFormat(String format) throws InvalidEnumException {
		for (ExportFormats exportFormat : values()) {
			if (exportFormat.getFormat().equalsIgnoreCase(format)) {
				return exportFormat;
			}
		}
		throw new InvalidEnumException();
	}

	public static boolean containsFormat(String format) {
		for (ExportFormats exportFormat : values()) {
			if (exportFormat.getFormat().equalsIgnoreCase(format)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return this.format;
	}

}
