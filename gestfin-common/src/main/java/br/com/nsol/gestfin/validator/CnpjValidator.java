package br.com.nsol.gestfin.validator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CnpjValidator implements Serializable {
	private static final long serialVersionUID = 3635867302253284674L;
	private static final List<String> INVALID_CNPJS = Arrays.asList(
			new String[] { "00000000000000", "11111111111111", "22222222222222", "33333333333333", "44444444444444",
					"55555555555555", "66666666666666", "77777777777777", "88888888888888", "99999999999999" });

	@Deprecated
	public static Boolean isValid(Long cnpj) {
		return isValid(cnpj == null ? null : Double.valueOf(cnpj.doubleValue()));
	}

	public static Boolean isValid(Double cnpj) {
		if (cnpj == null) {
			return Boolean.valueOf(false);
		}
		return isValid(new BigDecimal(cnpj.doubleValue()).toString());
	}

	public static Boolean isValid(String cnpj) {
		if (StringValidator.isEmpty(cnpj).booleanValue()) {
			return Boolean.valueOf(false);
		}

		if (cnpj.length() < 14) {
			String zeroes = "";
			for (int i = 0; i < 14 - cnpj.length(); i++) {
				zeroes = zeroes + "0";
			}
			cnpj = zeroes + cnpj;
		}

		cnpj = cnpj.replaceAll("[^\\d]", "");

		if (cnpj.length() != 14)
			return Boolean.valueOf(false);
		if (INVALID_CNPJS.contains(cnpj)) {
			return Boolean.valueOf(false);
		}

		int size = cnpj.length() - 2;
		String numbers = cnpj.substring(0, size);
		String digits = cnpj.substring(size);
		int sum = 0;
		int pos = size - 7;
		for (int i = size; i >= 1; i--) {
			sum += Integer.parseInt(numbers.charAt(size - i) + "") * pos--;
			if (pos < 2) {
				pos = 9;
			}
		}
		int result = sum % 11 < 2 ? 0 : 11 - sum % 11;
		if (result != Integer.parseInt(digits.charAt(0) + "")) {
			return Boolean.valueOf(false);
		}

		size += 1;
		numbers = cnpj.substring(0, size);
		sum = 0;
		pos = size - 7;
		for (int i = size; i >= 1; i--) {
			sum += Integer.parseInt(numbers.charAt(size - i) + "") * pos--;
			if (pos < 2) {
				pos = 9;
			}
		}
		result = sum % 11 < 2 ? 0 : 11 - sum % 11;
		if (result != Integer.parseInt(digits.charAt(1) + "")) {
			return Boolean.valueOf(false);
		}

		return Boolean.valueOf(true);
	}
}