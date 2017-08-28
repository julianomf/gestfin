package br.com.nsol.gestfin.validator;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class CpfValidator implements Serializable {
	private static final long serialVersionUID = 6060036039825415044L;
	private static final List<String> INVALID_CPFS = Arrays
			.asList(new String[] { "00000000000", "11111111111", "22222222222", "33333333333", "44444444444",
					"55555555555", "66666666666", "77777777777", "88888888888", "99999999999" });

	@Deprecated
	public static Boolean isValid(Long cpf) {
		return isValid(cpf == null ? null : Double.valueOf(cpf.doubleValue()));
	}

	public static Boolean isValid(Double cpf) {
		if (cpf == null) {
			return Boolean.valueOf(false);
		}
		return isValid(new BigDecimal(cpf.doubleValue()).toString());
	}

	public static Boolean isValid(String cpf) {
		if (StringValidator.isEmpty(cpf).booleanValue()) {
			return Boolean.valueOf(false);
		}

		if (cpf.length() < 11) {
			String zeroes = "";
			for (int i = 0; i < 11 - cpf.length(); i++) {
				zeroes = zeroes + "0";
			}
			cpf = zeroes + cpf;
		}

		cpf = cpf.replaceAll("[^\\d]", "");

		if (cpf.length() != 11)
			return Boolean.valueOf(false);
		if (INVALID_CPFS.contains(cpf)) {
			return Boolean.valueOf(false);
		}

		int add = 0;
		for (int i = 0; i < 9; i++) {
			add += Integer.parseInt(cpf.charAt(i) + "") * (10 - i);
		}

		int rev = 11 - add % 11;
		if ((rev == 10) || (rev == 11)) {
			rev = 0;
		}

		if (rev != Integer.parseInt(cpf.charAt(9) + "")) {
			return Boolean.valueOf(false);
		}

		add = 0;
		for (int i = 0; i < 10; i++) {
			add += Integer.parseInt(cpf.charAt(i) + "") * (11 - i);
		}
		rev = 11 - add % 11;
		if ((rev == 10) || (rev == 11)) {
			rev = 0;
		}
		if (rev != Integer.parseInt(cpf.charAt(10) + "")) {
			return Boolean.valueOf(false);
		}

		return Boolean.valueOf(true);
	}
}
