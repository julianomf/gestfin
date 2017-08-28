package br.com.nsol.gestfin.validator;

import java.io.Serializable;

public class CepValidator implements Serializable {
	private static final long serialVersionUID = 6060036039825415044L;

	public static Boolean isValid(Integer cep) {
		return isValid(cep.toString());
	}

	public static Boolean isValid(Long cep) {
		return isValid(Integer.valueOf(cep.intValue()));
	}

	public static Boolean isValid(String cep) {
		if (StringValidator.isEmpty(cep).booleanValue()) {
			return Boolean.valueOf(false);
		}

		return Boolean.valueOf((cep.matches(RegularExpression.CEP_WITH_HYPHEN.getRegex()))
				|| (cep.matches(RegularExpression.CEP_WITHOUT_HYPHEN.getRegex())));
	}
}
