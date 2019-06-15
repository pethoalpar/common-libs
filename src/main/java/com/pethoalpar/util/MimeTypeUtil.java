package com.pethoalpar.util;

/**
 * @author pethoalpar
 *
 */
public class MimeTypeUtil {

	private static final String APPLICATION = "application/";

	private MimeTypeUtil() {

	}

	public static final String getMimeType(String format) {
		switch (format.toLowerCase()) {
		case "csv":
			return APPLICATION + "CSV";
		case "xls":
			return APPLICATION + "vnd.ms-excel";
		case "xlsx":
			return APPLICATION + "vnd.openxmlformats-officedocument.spreadsheetml.sheet";
		case "pdf":
			return APPLICATION + "pdf";
		case "doc":
			return APPLICATION + "msword";
		case "json":
			return APPLICATION + "json";
		case "odt":
			return APPLICATION + "vnd.oasis.opendocument.text";
		case "ppt":
			return APPLICATION + "vnd.ms-powerpoint";
		case "html":
			return APPLICATION + "text/html";
		default:
			return "text/plain";
		}
	}

}
