package br.com.nsol.gestfin.services.business;

import javax.ejb.Local;

import br.com.nsol.gestfin.exceptions.TechnicalException;

@Local
public interface SystemParameterService {
	
	/**
	 * Realiza busca do valor do parametro
	 * @param parameterNmae Nome do parametro
	 * @return Valor
	 * @throws TechnicalException
	 */
	String findParameterValue(String parameterName) throws TechnicalException;
	
	/**
	 * Realiza busca do valor do parametro
	 * @param parameterNmae Nome do parametro
	 * @return Valor
	 * @throws TechnicalException
	 */
	Integer findParameterIntValue (String parameterName) throws TechnicalException;
	
	/**
	 * Realiza busca do valor do parametro
	 * @param parameterNmae Nome do parametro
	 * @return Valor
	 * @throws TechnicalException
	 */
	Float findParameterFloatValue (String parameterName) throws TechnicalException;
	
	/**
	 * Atualiza o valor do parametro
	 * @param parameterNmae Nome do parametro
	 * @param parameterValue Valor do parâmetro
	 * @return Valor
	 * @throws TechnicalException
	 */
	void updateParameterValue(String parameterName, String parameterValue) throws TechnicalException;
	
	/**
	 * Atualiza o valor do parametro
	 * @param parameterNmae Nome do parametro
	 * @param parameterValue Valor do parâmetro
	 * @return Valor
	 * @throws TechnicalException
	 */
	void updateParameterIntValue(String parameterName, Integer parameterValue) throws TechnicalException;

}
