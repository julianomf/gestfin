package br.com.nsol.gestfin.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Performs systems algorithms such as MD5 and others
 * 
 * @author 
 *
 */
public final class Algorithm {

	private Algorithm() {
	      //for sonar only
	}

	  private static final String PASSWORD_PATTERN = 
              "((?=.*\\d)(?=.*[a-zA-Z_@./#&+-]).{6,12})";
	/**
	 * Metodo responsável por gerar um hash a partir de um valor passado
	 * @param value Valor para calcular o hasg
	 * @return hash
	 * @throws NoSuchAlgorithmException
	 */
	public static final String generateMD5(final String value) throws NoSuchAlgorithmException {

		MessageDigest m = MessageDigest.getInstance("MD5");
		m.reset();
		m.update(value.getBytes());
		byte[] digest = m.digest();
		BigInteger bigInt = new BigInteger(1, digest);
		String hashtext = bigInt.toString(16);
		// Now we need to zero pad it if you actually want the full 32 chars.
		while (hashtext.length() < 32) {
			hashtext = "0" + hashtext;
		}
		return hashtext;
	}
	
	/**
	 * Metodo para criar uma nova senha de acesso ao sistema
	 * @return Senha provisória
	 */
	public static final String generateID(){
		long seed = System.currentTimeMillis() + System.nanoTime();
		Random generator = new Random(seed);
		int minValue = 100000;
		int maxValue = 999999;
		Integer id = generator.nextInt(maxValue-minValue) + minValue;
		
		return String.valueOf(id);
	}
	
	/**
	 * Valida se formato da senha é válido
	 * @param password password a ser validado
	 * @return true se está ok
	 */
	public static boolean validatePasswordPattern(String password) {
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	/**
	 * Verifica se o email digitado está OK
	 * @param email Email
	 * @return True se OK
	 */
	public static boolean emailValidatePattern(String email){
		String pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		return email.matches(pattern);
	}
	
	public static void main(String[] args) {
		String md5 = "admin";
		try {
			System.out.println(md5 + ": " + generateMD5(md5));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
