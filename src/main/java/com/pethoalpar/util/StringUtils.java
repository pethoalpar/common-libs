package com.pethoalpar.util;

public class StringUtils {

	public static String[] split(String str, String separator) {
		if (isEmpty(str) || isEmpty(separator)) {
			return new String[0];
		}
		int size = count(str, separator);
		String[] retArray = new String[size + 1];
		for (int i = 0; i <= size; ++i) {
			String substring = str.indexOf(separator) > -1 ? str.substring(0, str.indexOf(separator)) : str;
			retArray[i] = substring;
			str = str.indexOf(separator) > -1 ? str.substring(substring.length() + 1) : str;
		}
		return retArray;
	}

	public static int count(String str, String separator) {
		return str.length() - str.replaceAll(separator, "").length();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

}
