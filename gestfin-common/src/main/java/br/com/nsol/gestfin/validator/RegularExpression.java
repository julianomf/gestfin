package br.com.nsol.gestfin.validator;

public enum RegularExpression {
	CEP_WITH_HYPHEN("^[0-9]{5}\\-[0-9]{3}$"),
	CEP_WITHOUT_HYPHEN("^[0-9]{8}$"),
	VIN("^([0-9]|[A-Z]){11}+[0-9]{6}$"),
	VIN1("^([0-9]|[A-Z]){11}$"),
	VIN2("^[0-9]{0,6}$");

	private String regex;

	private RegularExpression(String regex) {
		this.regex = regex;
	}

	public String getRegex() {
		return this.regex;
	}
}