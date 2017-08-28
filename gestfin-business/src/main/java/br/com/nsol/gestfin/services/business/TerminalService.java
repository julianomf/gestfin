package br.com.nsol.gestfin.services.business;

import java.util.List;

import javax.ejb.Local;

import br.com.nsol.gestfin.dto.TerminalDTO;
import br.com.nsol.gestfin.exceptions.BaseException;

@Local
public interface TerminalService {
	/**
	 * Nome da classe
	 */
	String NAME = "TerminalService";
	
	/**
	 * Lista terminais registrados no sistema para exibição na manutenção do cadastro
	 * @param filter
	 * @return
	 * @throws BaseException
	 */
	List<TerminalDTO> listTerminals(TerminalDTO filter) throws BaseException;

	/**
	 * Procura os dados do terminal a partir do ID fornecido
	 * @param terminalId id do terminal
	 * @return TerminalDTO
	 * @throws BaseException
	 */
	TerminalDTO findTerminalById(Integer id) throws BaseException;

	/**
	 * Atualiza os dados de um terminal
	 * @param terminal
	 * @throws BaseException
	 */
	void updateTerminal(TerminalDTO terminal) throws BaseException;

	/**
	 * Insere um terminal
	 * @param terminal
	 * @throws BaseException
	 */
	void insertTerminal(TerminalDTO terminal) throws BaseException ;

	/**
	 * Exclui um terminal
	 * @param terminal
	 * @throws BaseException
	 */
	void deleteTerminal(TerminalDTO terminal) throws BaseException;
}