package br.com.nsol.gestfin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.TaxDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;
import br.com.nsol.gestfin.types.DaoParameterEnum;

/**
 * Camada de acesso para as funcionalidade da taxa
 */
@Stateless
public class TaxDAO extends GenericDAO<TaxDAO> {
	private static final long serialVersionUID = 2925852869700984520L;

	private static final Logger LOG = Logger.getLogger(TaxDAO.class);

	public TaxDAO() {
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
	public TaxDAO(SqlSessionFactory sf) {
		super(TaxDAO.class, sf);
	}

	/**
	 * Lista terminais registrados no sistema para exibição na manutenção do cadastro
	 * 
	 * @param filter
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings(UNCHECKED)
	public List<TaxDTO> listTaxes(TaxDTO filter)
			throws TechnicalException {
		LOG.debug("TaxDAO.listTaxes: " + filter);
		
		Map<String, Object> keys = new HashMap<String, Object>();
		if(filter.getOperator() != null && filter.getOperator().getId() != null ) {
			keys.put(DaoParameterEnum.PARAM_OPERATOR_ID.getValue(), filter.getOperator().getId());
		}
		if(filter.getEstablishment() != null && filter.getEstablishment().getId() != null ) {
			keys.put(DaoParameterEnum.PARAM_ESTABLISHMENT_ID.getValue(), filter.getEstablishment().getId());
		}
		if(filter.getCardFlag() != null && filter.getCardFlag().getId() != null ) {
			keys.put(DaoParameterEnum.PARAM_CARD_FLAG_ID.getValue(), filter.getCardFlag().getId());
		}
		if(filter.getTransactionType() != null && filter.getTransactionType().getId() != null ) {
			keys.put(DaoParameterEnum.PARAM_TRANSACTION_TYPE_ID.getValue(), filter.getTransactionType().getId());
		}

		return (List<TaxDTO>) this.list("listTaxes", keys);
	}
	
	/**
	 * Procura o taxa pelo seu id
	 * 
	 * @param id
	 *            id do taxa
	 * @return BusinessTaxDTO
	 * @throws TechnicalException
	 */
	public TaxDTO findTaxById(Integer id) throws TechnicalException {
		LOG.debug("TaxDAO.findTaxById: " + id);

		TaxDTO dto = (TaxDTO) this.find(TaxDTO.class, id);

		return dto;
	}
	
	/**
	 * Atualiza os dados de uma taxa
	 * @param tax
	 * @throws TechnicalException
	 */
	public void updateTax(TaxDTO tax) throws TechnicalException {
		LOG.debug("TaxDAO.updateTax: " + tax);
		this.update("updateTax", tax);
	}

	/**
	 * Insere uma taxa
	 * @param tax
	 * @throws TechnicalException
	 */
	public void insertTax(TaxDTO tax) throws TechnicalException {
		LOG.debug("TaxDAO.insertTax: " + tax);
		this.insert("insertTax", tax);
	}
	
	/**
	 * Exclui uma taxa
	 * @param tax
	 * @throws TechnicalException
	 */
	public void deleteTax(TaxDTO tax) throws TechnicalException {
		LOG.debug("TaxDAO.deleteTax: " + tax);
		this.delete("deleteTax", tax);
	}
}
