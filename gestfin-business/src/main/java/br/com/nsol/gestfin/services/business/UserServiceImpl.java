/**
 * 
 */
package br.com.nsol.gestfin.services.business;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dao.ProfileDAO;
import br.com.nsol.gestfin.dao.SystemParameterDAO;
import br.com.nsol.gestfin.dao.UserDAO;
import br.com.nsol.gestfin.dto.ProfileDTO;
import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.enums.BusinessExceptionType;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.Algorithm;
import br.com.nsol.gestfin.utils.EmailUtil;
import br.com.nsol.gestfin.utils.EmailUtil.Attachment;

/**
 * @author 
 *
 */
@Stateless(name=UserService.NAME)
public class UserServiceImpl implements UserService {
	private static final Logger LOG = Logger.getLogger(UserServiceImpl.class);
	@EJB
	private UserDAO userDAO;
	@EJB
	private ProfileDAO profileDAO;
	@EJB
	private SystemParameterDAO systemParameterDAO;

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.services.business.UserService#listUsers(br.com.nsol.gestfin.dto.UserDTO)
	 */
	@Override
	public List<UserDTO> listUsers(UserDTO filters)
			throws TechnicalException {
		LOG.debug("UserService.listUsers");
		return userDAO.listUsers(filters);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.business.services.user.UserService#findUserByMail(java.lang.String)
	 */
	@Override
	public UserDTO findUserByMail(String email) throws TechnicalException {
		return userDAO.findUserByMail(email);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.business.services.user.UserService#findUserById(java.lang.String)
	 */
	@Override
	public UserDTO findUserById(Integer userId) throws TechnicalException {
		LOG.debug("UserService.findUserById: " + userId);
		return userDAO.findUserById(userId);
	}
	
	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.services.business.UserService#updateUser(br.com.nsol.gestfin.dto.UserDTO)
	 */
	@Override
	public void updateUser(UserDTO user) throws TechnicalException {
		LOG.debug("UserService.updateUser");
		userDAO.updateUser(user);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.services.business.UserService#updateUserAndPassword(br.com.nsol.gestfin.dto.UserDTO)
	 */
	@Override
	public void updateUserAndPassword(UserDTO user) throws TechnicalException {
		LOG.debug("UserService.updateUserAndPassword");
		userDAO.updateUserAndPassword(user);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.services.business.UserService#insertUser(br.com.nsol.gestfin.dto.UserDTO)
	 */
	@Override
	public void insertUser(UserDTO user) throws BusinessException, TechnicalException {
		LOG.debug("UserService.insertUser");

		if (userDAO.findUserByMail(user.getUserMail()) != null) {
			throw new BusinessException(BusinessExceptionType.EMAIL_ALREADY_EXISTS);
		}		
		
		String newPassword = Algorithm.generateID();
		try {
			user.setUserPassword(Algorithm.generateMD5(newPassword).toUpperCase());
		} catch (NoSuchAlgorithmException e) {
			LOG.error("error", e);
			throw new TechnicalException("Error trying to set the user's password", e);
		}
		user.setIsPasswordReseted("S");
		userDAO.insertUser(user);
		user.setUserPassword(newPassword);
		
		// Envia e-mail com a senha inicial do usuário
		EmailUtil mail = new EmailUtil();
		mail.setAuth(systemParameterDAO.getParameterValue("SMTP_AUTH"));
		mail.setHost(systemParameterDAO.getParameterValue("SMTP_SERVER"));
		mail.setPassword(systemParameterDAO.getParameterValue("SMTP_PASSWORD"));
		mail.setPort(systemParameterDAO.getParameterValue("SMTP_PORT"));
		mail.setStartTlsEnable(systemParameterDAO.getParameterValue("SMTP_STARTTLS_ENABLE"));
		mail.setUserName(systemParameterDAO.getParameterValue("SMTP_USERNAME"));
		try {
			mail.sendEmail(
					systemParameterDAO.getParameterValue("EMAIL_NEW_USER_FROM"), 
					user.getUserMail(), 
					systemParameterDAO.getParameterValue("EMAIL_NEW_USER_SUBJECT"), 
					mail.buildBody(systemParameterDAO.getParameterValue("EMAIL_NEW_USER_BODY"), user), 
					new ArrayList<Attachment>());
		} catch (MessagingException e) {
			LOG.error("error", e);
			throw new TechnicalException("Error sending mail", e);
		}
	}
		
	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.business.services.user.UserService#validadeUserPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public String validadeUserPassword(String email, String password) throws TechnicalException {
		LOG.debug("UserService.validadeUserPassword: " + email);
		return userDAO.validateUserPassword(email, password);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.services.business.UserService#insertUserPassword(br.com.nsol.gestfin.dto.UserDTO)
	 */
	@Override
	public Integer insertUserAndPassword(UserDTO user) throws BusinessException, TechnicalException {
		LOG.debug("UserService.insertUserAndPassword");

		if (userDAO.findUserByMail(user.getUserMail()) != null) {
			throw new BusinessException(BusinessExceptionType.EMAIL_ALREADY_EXISTS);
		}		
		user.setIsPasswordReseted("N");
		userDAO.insertUser(user);
		return user.getUserId();
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.business.services.user.UserService#updateUserPassword(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateUserPassword(String email, String password) throws TechnicalException {
		userDAO.updateUserPassword(email, password);
	}

	/* (non-Javadoc)
	 * @see br.com.nsol.gestfin.business.services.user.UserService#recoverNewPassword(java.lang.String)
	 */
	@Override
	public void recoverNewPassword(String email) throws BusinessException, TechnicalException {
		
		UserDTO user = userDAO.findUserByMail(email);
		if (user == null) {
			throw new BusinessException(BusinessExceptionType.EMAIL_NOT_FOUND);
		}		
		
		//Gera nova senha para a solicitação do usuário
		String newPassword = Algorithm.generateID();
		user.setUserPassword(newPassword);

		try {
			//Altera a senha do usuário
			userDAO.updateUserPassword(email, Algorithm.generateMD5(newPassword).toUpperCase(), "S");
		} catch (NoSuchAlgorithmException e) {
			LOG.error("error", e);
			throw new TechnicalException("Error trying to change the user's password", e);
		}
		
		// Envia e-mail com a senha inicial do usuário
		EmailUtil mail = new EmailUtil();
		mail.setAuth(systemParameterDAO.getParameterValue("SMTP_AUTH"));
		mail.setHost(systemParameterDAO.getParameterValue("SMTP_SERVER"));
		mail.setPassword(systemParameterDAO.getParameterValue("SMTP_PASSWORD"));
		mail.setPort(systemParameterDAO.getParameterValue("SMTP_PORT"));
		mail.setStartTlsEnable(systemParameterDAO.getParameterValue("SMTP_STARTTLS_ENABLE"));
		mail.setUserName(systemParameterDAO.getParameterValue("SMTP_USERNAME"));
		try {
			mail.sendEmail(
					systemParameterDAO.getParameterValue("EMAIL_RESET_PASSWORD_FROM"), 
					user.getUserMail(), 
					systemParameterDAO.getParameterValue("EMAIL_RESET_PASSWORD_SUBJECT"), 
					mail.buildBody(systemParameterDAO.getParameterValue("EMAIL_RESET_PASSWORD_BODY"), user), 
					new ArrayList<Attachment>());
		} catch (MessagingException e) {
			LOG.error("error", e);
			throw new TechnicalException("Error sending mail", e);
		}

	}

	@Override
	public List<ProfileDTO> listProfiles() throws TechnicalException {
		LOG.debug("UserService.listProfiles");
		return profileDAO.listProfiles();
	}

}
