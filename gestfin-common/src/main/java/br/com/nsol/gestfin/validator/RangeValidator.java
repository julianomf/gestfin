package br.com.nsol.gestfin.validator;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class RangeValidator implements Serializable {
	private static final long serialVersionUID = 2086245815660034842L;

	public static boolean isRangeValid(Number num1, Number num2) {
		if ((Validator.isNull(num1).booleanValue()) || (Validator.isNull(num2).booleanValue())) {
			return false;
		}
		return num1.doubleValue() <= num2.doubleValue();
	}

	public static boolean isRangeValid(Calendar cal1, Calendar cal2) {
		if ((Validator.isNull(cal1).booleanValue()) || (Validator.isNull(cal2).booleanValue())) {
			return false;
		}
		return isRangeValid(cal1.getTime(), cal2.getTime());
	}

	public static boolean isRangeValid(Date dt1, Date dt2) {
		if ((Validator.isNull(dt1).booleanValue()) || (Validator.isNull(dt2).booleanValue())) {
			return false;
		}
		return (dt1.getTime() == dt2.getTime()) || (dt1.before(dt2));
	}
}