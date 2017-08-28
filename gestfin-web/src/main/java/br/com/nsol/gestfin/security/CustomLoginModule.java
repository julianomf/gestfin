package br.com.nsol.gestfin.security;

import java.security.NoSuchAlgorithmException;
import java.security.acl.Group;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.login.LoginException;

import org.apache.log4j.Logger;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.UsernamePasswordLoginModule;

import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade;
import br.com.nsol.gestfin.service.ServiceLocator;
import br.com.nsol.gestfin.utils.Algorithm;

/**
 * Username and password based login module, extending JBoss'
 * {@link UsernamePasswordLoginModule}.
 */
public class CustomLoginModule extends UsernamePasswordLoginModule {
	private static final Logger LOG = Logger.getLogger(CustomLoginModule.class);
	 
	private AppraisalToolBussinessFacade facade;
	private String userRole;
	
	@SuppressWarnings("rawtypes")
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
		// We could read options passed via <module-option> in standalone.xml if
		// there were any here
		// For an example see
		// http://docs.redhat.com/docs/en-US/JBoss_Enterprise_Application_Platform/5/html/Security_Guide/sect-Custom_LoginModule_Example.html

		// We could also f.ex. lookup a data source in JNDI
		// For an example see
		// http://www.docjar.com/html/api/org/jboss/security/auth/spi/DatabaseServerLoginModule.java.html
		super.initialize(subject, callbackHandler, sharedState, options);

		try {
			facade = (AppraisalToolBussinessFacade) ServiceLocator.getService(AppraisalToolBussinessFacade.class);
		} catch (TechnicalException e) {
			LOG.error("Erro ao tentar carregar o serviço de fachada para acesso a camada de business", e);
		}
	}

	@Override
	protected String getUsersPassword() throws LoginException {
		LOG.debug("CustomLoginModule: authenticating user '%s'\n" + getUsername());
		String password = "";
		String[] list = getUsernameAndPassword();
		if (list.length > 0){
			password = list[1];
		}
		
		return password;
	}

	/**
	 * (optional) Override if you want to change how the password are compared
	 * or if you need to perform some conversion on them.
	 */
	@Override
	protected boolean validatePassword(String inputPassword, String expectedPassword) {
		String encryptedInputPassword = "";
		
		try {
			if (inputPassword.startsWith("md5:")) {
				userRole = facade.validadeUserPassword(getUsername(), inputPassword.substring(4));
			}
			else { 
				userRole = facade.validadeUserPassword(getUsername(), Algorithm.generateMD5(inputPassword).toUpperCase());
			}
			if (userRole != null) {
				if (inputPassword.startsWith("md5:")) {
					encryptedInputPassword = (inputPassword == null) ? null : inputPassword.substring(4);
				}
				else {
					encryptedInputPassword = (inputPassword == null) ? null : Algorithm.generateMD5(inputPassword).toUpperCase();
				}
				return super.validatePassword(encryptedInputPassword, encryptedInputPassword);
			}
		} 
		catch (TechnicalException | NoSuchAlgorithmException e1) {
			log.error("Error login:", e1);
		}
		return false;
		
	}

	/**
	 * (required) The groups of the user, there must be at least one group
	 * called "Roles" (though it likely can be empty) containing the roles the
	 * user has.
	 */
	@Override
	protected Group[] getRoleSets() throws LoginException {
		SimpleGroup group = new SimpleGroup("Roles");
		try {
			group.addMember(new SimplePrincipal(userRole));
		} catch (Exception e) {
			throw new LoginException("Failed to create group member for " + group);
		}
		return new Group[] { group };
	}

}
