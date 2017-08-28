package br.com.nsol.gestfin.validator;

import java.io.Serializable;
import java.util.Collection;

public class Validator implements Serializable {
	private static final long serialVersionUID = -8442618581787999557L;

	public static Boolean isNull(Object obj) {
		return Boolean.valueOf(obj == null);
	}

	public static Boolean isEmpty(Collection<?> collection) {
		if (isNull(collection).booleanValue()) {
			return Boolean.valueOf(true);
		}
		return Boolean.valueOf(collection.isEmpty());
	}

	public static Boolean isNegativeNumber(Number num) {
		if (isNull(num).booleanValue()) {
			return null;
		}
		return Boolean.valueOf(num.intValue() < 0);
	}
}