package br.com.nsol.gestfin.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.factory.GenericDAO;
import br.com.nsol.gestfin.factory.MyBatisSessionManager;
import br.com.nsol.gestfin.types.DaoParameterEnum;

/**
 * Camada de acesso para as funcionalidade do usuário
 * 
 * @author 
 */
@Stateless
public class UserDAO extends GenericDAO<UserDAO> {
	private static final Logger LOG = Logger.getLogger(UserDAO.class);
	private static final long serialVersionUID = -3180534217527834634L;

	public UserDAO() {
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
	public UserDAO(SqlSessionFactory sf) {
		super(UserDAO.class, sf);
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
	public List<UserDTO> listUsers(UserDTO filters)
			throws TechnicalException {
		LOG.debug("UserDAO.listUsers: " + filters);
		
		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_NAME.getValue(), filters.getUserName());
		keys.put(DaoParameterEnum.PARAM_EMAIL.getValue(), filters.getUserMail());
		keys.put(DaoParameterEnum.PARAM_PROFILEID.getValue(), filters.getProfileId());

		return (List<UserDTO>) this.list("listUsers", keys);
	}
	
	/**
	 * Procura o usuário por seu e-mail
	 * 
	 * @param email
	 *            e-mail do usuário
	 * @return BusinessUserDTO
	 * @throws TechnicalException
	 */
	public UserDTO findUserByMail(String email) throws TechnicalException {
		LOG.debug("UserDAO.findUserByMail: " + email);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_EMAIL.getValue(), email);

		UserDTO dto = (UserDTO) this.find("findUserByMail", keys);

		return dto;
	}

	/**
	 * Procura o usuário pelo seu id
	 * 
	 * @param userId
	 *            id do usuário
	 * @return BusinessUserDTO
	 * @throws TechnicalException
	 */
	public UserDTO findUserById(Integer userId) throws TechnicalException {
		LOG.debug("UserDAO.findUserById: " + userId);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_USERID.getValue(), userId);

		UserDTO dto = (UserDTO) this.find("findUserById", keys);

		return dto;
	}
	
	/**
	 * Atualiza os dados de um usuário
	 * @param user
	 * @throws TechnicalException
	 */
	public void updateUser(UserDTO user) throws TechnicalException {
		LOG.debug("UserDAO.updateUser: " + user);
		this.update("updateUser", user);
	}

	/**
	 * Atualiza os dados de um usuário incluindo sua senha
	 * @param user
	 * @throws TechnicalException
	 */
	public void updateUserAndPassword(UserDTO user) throws TechnicalException {
		LOG.debug("UserDAO.updateUserAndPassword: " + user);
		this.update("updateUserAndPassword", user);
	}

	/**
	 * Insere um usuário
	 * @param user
	 * @throws TechnicalException
	 */
	public void insertUser(UserDTO user) throws TechnicalException {
		LOG.debug("UserDAO.insertUser: " + user);
		this.insert("insertUser", user);
		LOG.debug("UserDAO.insertUser (after): " + user);
	}
		
	/**
	 * Valida o usuário e a senha
	 * 
	 * @param email
	 *            user id
	 * @param password
	 *            password
	 * @return true Se o usuário precisa resetar a senha
	 */
	public String validateUserPassword(String email, String password) throws TechnicalException {
		LOG.debug("UserDAO.validatePassword: " + email);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_EMAIL.getValue(), email);
		keys.put(DaoParameterEnum.PARAM_PASSWORD.getValue(), password);
		String result = (String) this.find("validateUserPassword", keys);

		return result;
	}

	/**
	 * Altera a senha do usuário
	 * 
	 * @param email
	 *            ID para localização do usuário
	 * @param password
	 *            Nova senha
	 * @throws TechnicalException
	 */
	public void updateUserPassword(String email, String password) throws TechnicalException {
		LOG.debug("updateUserPassword: " + email);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_EMAIL.getValue(), email);
		keys.put(DaoParameterEnum.PARAM_PASSWORD.getValue(), password);

		this.updateUserPassword(email, password, "N");
	}

	/**
	 * Altera a senha do usuário
	 * 
	 * @param email
	 *            ID para localização do usuário
	 * @param password
	 *            Nova senha
	 * @throws TechnicalException
	 */
	public void updateUserPassword(String email, String password, String reset) throws TechnicalException {
		LOG.debug("updateUserPassword: " + email + "," + reset);

		Map<String, Object> keys = new HashMap<String, Object>();
		keys.put(DaoParameterEnum.PARAM_EMAIL.getValue(), email);
		keys.put(DaoParameterEnum.PARAM_PASSWORD.getValue(), password);
		keys.put(DaoParameterEnum.PARAM_RESET.getValue(), reset);

		this.update("updateUserPassword", keys);
	}
}
