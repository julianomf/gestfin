package br.com.nsol.gestfin.dao;

import java.util.List;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.ProfileDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;

/**
 * Camada de acesso para as funcionalidade do perfil
 * 
 * @author 
 */
@Stateless
public class ProfileDAO extends GenericDAO<ProfileDAO> {
	private static final Logger LOG = Logger.getLogger(ProfileDAO.class);
	private static final long serialVersionUID = -1894404277743837494L;

	public ProfileDAO() {
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
	public ProfileDAO(SqlSessionFactory sf) {
		super(ProfileDAO.class, sf);
	}

	/**
	 * Lista usuários registrados no sistema para exibição na manutenção do cadastro
	 * 
	 * @param filters
	 * @param allowedProfiles
	 * @return
	 * @throws TechnicalException
	 */
	@SuppressWarnings(UNCHECKED)
	public List<ProfileDTO> listProfiles()
			throws TechnicalException {
		LOG.debug("UserDAO.listProfiles");
		
		return (List<ProfileDTO>) this.list("listProfiles");
	}
	
}
