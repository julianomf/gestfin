package br.com.nsol.gestfin.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public final class Base64Util {

	private Base64Util() {
		// for sonar only
	}

	/**
	 * Codifica um array de bytes em uma string base 64
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String encode(byte[] value) {
		if(value == null) {
			return "";
		}
		
		return Base64.getEncoder().encodeToString(value);
	}

	/**
	 * Codifica uma string para outra em formato base 64
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String encode(String value) {
		return Base64.getEncoder().encodeToString(value.getBytes(StandardCharsets.UTF_8));
	}

	/**
	 * Decodifica uma string base 64
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static String decode(String value) {
		byte[] decodedValue = Base64.getDecoder().decode(value);
		return new String(decodedValue, StandardCharsets.UTF_8);
	}

}