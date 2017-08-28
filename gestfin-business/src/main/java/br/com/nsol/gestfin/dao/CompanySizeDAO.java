package br.com.nsol.gestfin.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanySizeDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;

/**
 * Camada de acesso 
 * 
 * @author 
 */
@Stateless
public class CompanySizeDAO extends GenericDAO<CompanySizeDAO> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CompanySizeDAO.class);

	public CompanySizeDAO() {
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
	public CompanySizeDAO(SqlSessionFactory sf) {
		super(CompanySizeDAO.class, sf);
	}

	/**
	 * Lista todos os registros
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings(UNCHECKED)
	public List<CompanySizeDTO> listAll()
			throws TechnicalException {
		LOG.debug("UserDAO.listAll");
		
		return (List<CompanySizeDTO>) this.list("listAll");
	}
	
}
