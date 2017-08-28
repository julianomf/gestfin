package br.com.nsol.gestfin.utils;

import java.util.List;

public final class ValidateUtil {

	private ValidateUtil() {
	      //for sonar only
	}

	/**
	 * Helper para padronizar os testes de string vazia ou nula
	 * @param input
	 * @return
	 */
	public static boolean isEmptyString(String input) {
		return (input == null || input.isEmpty());
	}

	/**
	 * Helper para padronizar os testes de string com tamanho mínimo
	 * @param input
	 * @param minLength
	 * @return
	 */
	public static boolean isMinLengthString(String input, int minLength) {
		return (input == null || input.length() < minLength);
	}

	/**
	 * Helper para padronizar os testes de objeto nulo
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return (obj == null);
	}

	/**
	 * Helper para validar se lista vazia
	 * @param <T>
	 * @param list
	 * @return
	 */
	public static <T> boolean isEmptyList(List<T> list) {
		return (list == null || list.size() == 0);
	}
}