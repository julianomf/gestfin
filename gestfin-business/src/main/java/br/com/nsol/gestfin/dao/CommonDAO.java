package br.com.nsol.gestfin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.cache.CacheType;
import br.com.nsol.gestfin.dto.CardFlagDTO;
import br.com.nsol.gestfin.dto.EstablishmentDTO;
import br.com.nsol.gestfin.dto.OperatorDTO;
import br.com.nsol.gestfin.dto.PostalCodeDTO;
import br.com.nsol.gestfin.dto.StateDTO;
import br.com.nsol.gestfin.dto.TransactionTypeDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;
import br.com.nsol.gestfin.types.DaoParameterEnum;
/**
 * Camada de acesso para as funcionalidade comuns
 * @author 
 */
@Stateless
public class CommonDAO extends GenericDAO<CommonDAO>{
	private static final long serialVersionUID = -6250872253127422822L;
	private static final Logger LOG = Logger.getLogger(CommonDAO.class);

	public CommonDAO() {
		this(MyBatisSessionManager.getInstance());
	}
	
	/**
	 * Construtor da classe
	 * @param daoType Classe
	 * @param sf Factory
	 */
	public CommonDAO(SqlSessionFactory sf) {
		super(CommonDAO.class, sf);
	}

	/**
	 * Lista todos os registros
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings(UNCHECKED)
	public List<StateDTO> listStates()
			throws TechnicalException {
		LOG.debug("CommonDAO.listStates");
		
		return (List<StateDTO>) this.list("listStates");
	}

	/**
	 * Lista todos os registros
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	public PostalCodeDTO findPostalCode(Integer postalCode)
			throws TechnicalException {
		LOG.debug("CommonDAO.findPostalCode");
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_POSTALCODE.getValue(), postalCode);
		
		return (PostalCodeDTO) this.find("findPostalCode", keys);
	}

	/**
	 * Lista todas as operadoras
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings(UNCHECKED)
	public List<OperatorDTO> listOperators() throws TechnicalException {
		Long cacheKey = 1000L;
		String methodName = "listOperators";
		List<OperatorDTO> list = (List<OperatorDTO>)super.getCachedList(methodName, cacheKey, CacheType.LIST_OPERATORS);
		
		if (list == null) {
			list = (List<OperatorDTO>) this.list(OperatorDTO.class);
			super.putCacheList(methodName, cacheKey, CacheType.LIST_OPERATORS, list);
		}
		
		return list;		
	}

	/**
	 * List Establishment
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings(UNCHECKED)
	public List<EstablishmentDTO> listEstablishments() throws TechnicalException {
		Long cacheKey = 1010L;
		String methodName = "listEstablishments";
		List<EstablishmentDTO> list = (List<EstablishmentDTO>)super.getCachedList(methodName, cacheKey, CacheType.LIST_ESTABLISHMENTS);
		
		if (list == null) {
			list = (List<EstablishmentDTO>) this.list(EstablishmentDTO.class);
			super.putCacheList(methodName, cacheKey, CacheType.LIST_ESTABLISHMENTS, list);
		}
		
		return list;		
	}
	
	@SuppressWarnings(UNCHECKED)
	public List<CardFlagDTO> listCardFlags() throws TechnicalException {
		Long cacheKey = 1020L;
		String methodName = "listCardFlags";
		List<CardFlagDTO> list = (List<CardFlagDTO>)super.getCachedList(methodName, cacheKey, CacheType.LIST_CARD_FLAGS);
		
		if (list == null) {
			list = (List<CardFlagDTO>) this.list(CardFlagDTO.class);
			super.putCacheList(methodName, cacheKey, CacheType.LIST_CARD_FLAGS, list);
		}
		
		return list;		
	}
	
	@SuppressWarnings(UNCHECKED)
	public List<TransactionTypeDTO> listTransactionTypes() throws TechnicalException {
		Long cacheKey = 1030L;
		String methodName = "listTransactionTypes";
		List<TransactionTypeDTO> list = (List<TransactionTypeDTO>)super.getCachedList(methodName, cacheKey, CacheType.LIST_TRANSACTION_TYPES);
		
		if (list == null) {
			list = (List<TransactionTypeDTO>) this.list(TransactionTypeDTO.class);
			super.putCacheList(methodName, cacheKey, CacheType.LIST_TRANSACTION_TYPES, list);
		}
		
		return list;		
	}
}
