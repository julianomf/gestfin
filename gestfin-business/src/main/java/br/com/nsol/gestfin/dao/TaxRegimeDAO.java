package br.com.nsol.gestfin.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.TaxRegimeDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;

/**
 * Camada de acesso 
 * 
 * @author 
 */
@Stateless
public class TaxRegimeDAO extends GenericDAO<TaxRegimeDAO> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(TaxRegimeDAO.class);

	public TaxRegimeDAO() {
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
	public TaxRegimeDAO(SqlSessionFactory sf) {
		super(TaxRegimeDAO.class, sf);
	}

	/**
	 * Lista todos os registros
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings(UNCHECKED)
	public List<TaxRegimeDTO> listAll()
			throws TechnicalException {
		LOG.debug("TaxRegimeDAO.listAll");
		
		return (List<TaxRegimeDTO>) this.list("listAll");
	}
	
}
