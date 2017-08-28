package br.com.nsol.gestfin.validator;

import java.io.Serializable;

public class StringValidator implements Serializable {
	private static final long serialVersionUID = -3742679224502657439L;

	public static Boolean isEmpty(String str) {
		return isEmpty(str, true);
	}

	public static Boolean isEmpty(String str, boolean trim) {
		if (Validator.isNull(str).booleanValue()) {
			return Boolean.valueOf(true);
		}
		if (trim) {
			return Boolean.valueOf(str.trim().isEmpty());
		}
		return Boolean.valueOf(str.isEmpty());
	}

	public static Boolean isLenghtMinorThan(String str, int length) {
		if (Validator.isNegativeNumber(Integer.valueOf(length)).booleanValue()) {
			return Boolean.valueOf(false);
		}
		if ((isEmpty(str).booleanValue()) && (length > 0)) {
			return Boolean.valueOf(true);
		}
		return Boolean.valueOf(str.length() < length);
	}

	public static Boolean isLenghtMajorThan(String str, int length) {
		if (Validator.isNegativeNumber(Integer.valueOf(length)).booleanValue()) {
			return Boolean.valueOf(true);
		}
		if ((isEmpty(str).booleanValue()) && (length > 0)) {
			return Boolean.valueOf(false);
		}

		return Boolean.valueOf(str.length() > length);
	}

	public static Boolean isLenghtEqualsThan(String str, int length) {
		if (Validator.isNegativeNumber(Integer.valueOf(length)).booleanValue()) {
			return Boolean.valueOf(false);
		}
		if ((isEmpty(str).booleanValue()) && (length > 0)) {
			return Boolean.valueOf(false);
		}
		return Boolean.valueOf(str.length() == length);
	}
}