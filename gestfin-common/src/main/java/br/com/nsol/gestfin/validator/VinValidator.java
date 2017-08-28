package br.com.nsol.gestfin.validator;

import java.io.Serializable;

public class VinValidator implements Serializable {
	private static final long serialVersionUID = -679140301962064408L;

	public static boolean isValidVin(String vin) {
		return (!Validator.isNull(vin).booleanValue()) && (vin.matches(RegularExpression.VIN.getRegex()));
	}

	public static boolean isValidVin1(String vin1) {
		return (!Validator.isNull(vin1).booleanValue()) && (vin1.matches(RegularExpression.VIN1.getRegex()));
	}

	public static boolean isValidVin2(Integer vin2) {
		return (!Validator.isNull(vin2).booleanValue()) && (vin2.toString().matches(RegularExpression.VIN2.getRegex()));
	}
}