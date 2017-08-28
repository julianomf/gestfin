package br.com.nsol.gestfin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.TerminalDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;
import br.com.nsol.gestfin.types.DaoParameterEnum;

/**
 * Camada de acesso para as funcionalidade do terminal
 * @author 
 */
@Stateless
public class TerminalDAO extends GenericDAO<TerminalDAO> {
	private static final Logger LOG = Logger.getLogger(TerminalDAO.class);
	private static final long serialVersionUID = -1938004975357310453L;

	public TerminalDAO() {
		this(MyBatisSessionManager.getInstance());
	}

	/**
	 * Construtor da classe
	 * 
	 * @param daoType
	 *            Classe
	 * @param sf
	 *            Factory
	 */
	public TerminalDAO(SqlSessionFactory sf) {
		super(TerminalDAO.class, sf);
	}

	/**
	 * Lista terminais registrados no sistema para exibição na manutenção do cadastro
	 * 
	 * @param filter
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings(UNCHECKED)
	public List<TerminalDTO> listTerminals(TerminalDTO filter)
			throws TechnicalException {
		LOG.debug("TerminalDAO.listTerminals: " + filter);
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_TERMINAL_NUMBER.getValue(), filter.getTerminalNumber());
		keys.put(DaoParameterEnum.PARAM_DESCRIPTION.getValue(), filter.getDescription());
		if(filter.getOperator() != null && filter.getOperator().getId() != null ) {
			keys.put(DaoParameterEnum.PARAM_OPERATOR_ID.getValue(), filter.getOperator().getId());
		}
		if(filter.getEstablishment() != null && filter.getEstablishment().getId() != null ) {
			keys.put(DaoParameterEnum.PARAM_ESTABLISHMENT_ID.getValue(), filter.getEstablishment().getId());
		}

		return (List<TerminalDTO>) this.list("listTerminals", keys);
	}
	
	/**
	 * Procura o terminal pelo seu id
	 * 
	 * @param id
	 *            id do terminal
	 * @return BusinessTerminalDTO
	 * @throws TechnicalException
	 */
	public TerminalDTO findTerminalById(Integer id) throws TechnicalException {
		LOG.debug("TerminalDAO.findTerminalById: " + id);

		TerminalDTO dto = (TerminalDTO) this.find(TerminalDTO.class, id);

		return dto;
	}
	
	/**
	 * Atualiza os dados de um terminal
	 * @param terminal
	 * @throws TechnicalException
	 */
	public void updateTerminal(TerminalDTO terminal) throws TechnicalException {
		LOG.debug("TerminalDAO.updateTerminal: " + terminal);
		this.update("updateTerminal", terminal);
	}

	/**
	 * Insere um terminal
	 * @param terminal
	 * @throws TechnicalException
	 */
	public void insertTerminal(TerminalDTO terminal) throws TechnicalException {
		LOG.debug("TerminalDAO.insertTerminal: " + terminal);
		this.insert("insertTerminal", terminal);
	}
	
	/**
	 * Exclui um terminal
	 * @param terminal
	 * @throws TechnicalException
	 */
	public void deleteTerminal(TerminalDTO terminal) throws TechnicalException {
		LOG.debug("TerminalDAO.deleteTerminal: " + terminal);
		this.delete("deleteTerminal", terminal);
	}
}
