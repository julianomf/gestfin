package br.com.nsol.gestfin.services.business;

import java.util.List;

import javax.ejb.Local;

import br.com.nsol.gestfin.dto.PostalCodeDTO;
import br.com.nsol.gestfin.dto.StateDTO;
import br.com.nsol.gestfin.dto.TaxRegimeDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.dto.CardFlagDTO;
import br.com.nsol.gestfin.dto.EstablishmentDTO;
import br.com.nsol.gestfin.dto.OperatorDTO;
import br.com.nsol.gestfin.dto.TransactionTypeDTO;
import br.com.nsol.gestfin.exceptions.BaseException;

@Local
public interface CommonService {
	/**
	 * Nome da classe
	 */
	String NAME = "CommonService";

	/**
	 * Lista todos os estados cadastrados
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<StateDTO> listStates() throws TechnicalException;

	/**
	 * Lista todos os estados cadastrados
	 * @return Lista
	 * @throws TechnicalException
	 */
	List<TaxRegimeDTO> listTaxRegimes() throws TechnicalException;

	/**
	 * Procura dados relacionados a um CEP
	 * @param postalCode
	 * @return
	 * @throws TechnicalException
	 */
	PostalCodeDTO findPostalCode(Integer postalCode) throws TechnicalException;

	/**
	 * Lista todos as operadoras cadastradas
	 * @return
	 * @throws BaseException
	 */
	List<OperatorDTO> listOperators() throws BaseException;

	/**
	 * Lista todos os estabelecimentos cadastradas
	 * @return
	 * @throws BaseException
	 */
	List<EstablishmentDTO> listEstablishments() throws BaseException;

	/**
	 * Lista todos as bandeiras cadastradas
	 * @return
	 * @throws BaseException
	 */
	List<CardFlagDTO> listCardFlags() throws BaseException;

	/**
	 * Lista todos os tipoas de transação cadastradas
	 * @return
	 * @throws BaseException
	 */
	List<TransactionTypeDTO> listTransactionTypes() throws BaseException;
	
}
