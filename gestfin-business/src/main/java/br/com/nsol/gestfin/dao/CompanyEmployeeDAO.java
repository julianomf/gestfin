package br.com.nsol.gestfin.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanyEmployeeDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;

/**
 * Camada de acesso 
 * 
 * @author 
 */
@Stateless
public class CompanyEmployeeDAO extends GenericDAO<CompanyEmployeeDAO> {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = Logger.getLogger(CompanyEmployeeDAO.class);

	public CompanyEmployeeDAO() {
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
	public CompanyEmployeeDAO(SqlSessionFactory sf) {
		super(CompanyEmployeeDAO.class, sf);
	}

	/**
	 * Lista todos os registros
	 * 
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings(UNCHECKED)
	public List<CompanyEmployeeDTO> listAll()
			throws TechnicalException {
		LOG.debug("UserDAO.listAll");
		
		return (List<CompanyEmployeeDTO>) this.list("listAll");
	}
	
}
