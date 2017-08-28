package br.com.nsol.gestfin.services.business;

import java.util.List;

import javax.ejb.Local;

import br.com.nsol.gestfin.dto.TaxDTO;
import br.com.nsol.gestfin.exceptions.BaseException;

@Local
public interface TaxService {
	/**
	 * Nome da classe
	 */
	String NAME = "TaxService";
	
	/**
	 * Lista taxas registradas no sistema para exibição na manutenção do cadastro
	 * @param filter
	 * @return
	 * @throws BaseException
	 */
	List<TaxDTO> listTaxes(TaxDTO filter) throws BaseException;

	/**
	 * Procura os dados da taxa a partir do ID fornecido
	 * @param taxId id da taxa
	 * @return TaxDTO
	 * @throws BaseException
	 */
	TaxDTO findTaxById(Integer id) throws BaseException;

	/**
	 * Atualiza os dados de uma taxa
	 * @param tax
	 * @throws BaseException
	 */
	void updateTax(TaxDTO tax) throws BaseException;

	/**
	 * Insere uma taxa
	 * @param tax
	 * @throws BaseException
	 */
	void insertTax(TaxDTO tax) throws BaseException ;

	/**
	 * Exclui uma taxa
	 * @param tax
	 * @throws BaseException
	 */
	void deleteTax(TaxDTO tax) throws BaseException;
}