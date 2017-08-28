package br.com.nsol.gestfin.view.membership;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.Algorithm;
import br.com.nsol.gestfin.utils.ValidateUtil;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Classe responsável pelo passo "Empresa - Seus dados" do wizard de adesão
 * @author 
 *
 */
@ViewScoped
@ManagedBean(name="msYourDataView")
public class MembershipYourDataView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipYourDataView.class);
    private static final String MESSAGE_ID = null;
	private boolean hasError;
	
    private String name;
    private String password;
    private String passwordConfirmation;
    
	/**
	 * @return the hasError
	 */
	public boolean isHasError() {
		return hasError;
	}

	/**
	 * @param hasError the hasError to set
	 */
	public void setHasError(boolean hasError) {
		this.hasError = hasError;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the passwordConfirmation
	 */
	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	/**
	 * @param passwordConfirmation the passwordConfirmation to set
	 */
	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
	}

	/**
	 * Metodo de inicialização do bean
	 */
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		// Configura o passo atual da avaliação
		super.setCurrentMembershipStep(MEMBERSHIP_STEP_COMPANY);
		
		// Verifica se ainda tem a sessão válida com os dados do fluxo
		if (super.getMembershipInfo() == null || super.getMembershipInfo().getUser() == null) {
			try {
				
				// Redireciona para o início da adesão
				super.redirect(PAGE_MEMBERSHIP_NEW);
				
			} catch (IOException e) {
				LOG.error("Error init (E)", e);
				addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			}
		} else {
			try {
				
				// Busca os dados da empresa pelo e-mail do responsável do cadastro
    			UserDTO user = super.getFacade().findUserByMail(super.getMembershipInfo().getUser().getUserMail());
    			if (user != null) {
        			super.getMembershipInfo().setUser(user);
    			}
    			
			} catch (TechnicalException e) {
				LOG.error("Error init (TE)", e);
				addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			} catch (Exception e) {
				LOG.error("Error init (E)", e);
				addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			}
			
			setName(super.getMembershipInfo().getUser().getUserName());
			// Senhas não recarrega por segurança
			//setPassword(super.getMembershipInfo().getUser().getUserPassword());
			//setPasswordConfirmation(getPassword());
		}
		
		LOG.debug("Finalizada action: init");
	}

	/**
	 * Action executada ao clicar no botão Voltar
	 */
	public void actionBack() {
		LOG.debug("Iniciando action: actionBack");

		try {

			//-- O sistema redireciona o usuário para página inicial da adesão
			super.redirect(PAGE_MEMBERSHIP_NEW);
			
		} catch (Exception e){
			LOG.error("Error actionBack (E)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		}
		
		LOG.debug("Finalizada action: actionBack");
	}

	/**
	 * Action executada ao clicar no botão Próximo
	 */
	public void actionNext() {
		LOG.debug("Iniciando action: actionNext");
		setHasError(false);

		try {

			validate();

			// -- O sistema salva os dados
			UserDTO user = super.getFacade().findUserByMail(super.getMembershipInfo().getUser().getUserMail());
			if (user == null) {
				user = new UserDTO();
				user.setUserMail(super.getMembershipInfo().getUser().getUserMail());
				user.setUserName(getName());
				user.setUserPassword(Algorithm.generateMD5(getPassword()).toUpperCase());
				user.setIsPasswordReseted("N");
				user.setProfileId(USER_PROFILE_ADMINISTRATOR);
				user.setIsActive(STATUS_ACTIVE);
				user.setLastUpdatedByUser("Membership Wizard");
			} else {
				user.setUserName(getName());
				user.setUserPassword(Algorithm.generateMD5(getPassword()).toUpperCase());
				user.setLastUpdatedByUser("Membership Wizard");
			}
			super.getMembershipInfo().setUser(user);

			// -- O sistema redireciona o usuário para página com os dados da empresa
			super.redirect(PAGE_MEMBERSHIP_COMPANY_DATA);

		} catch (BusinessException e) {
			LOG.error("Error actionNext (BE)", e);
			addWarnMessage(null, getMessageBundle(e.getMessage()));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionNext (E)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionNext");
	}

	/**
	 * Faz a validação dos dados do formulário
	 * @return
	 */
	private void validate() throws BusinessException {
			
		ValidateCustomerData validator = new ValidateCustomerData();
		validator.checkName();
		validator.checkPassword();
		
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateCustomerData {
		
		/**
		 * Realiza validação do nome  
		 * @return true/false
		 */
		public void checkName() throws BusinessException {
			if (ValidateUtil.isMinLengthString(getName(), NAME_MINLENGHT)){
				throw new BusinessException("membership.your_data.message.validation.name");
			}
		}

		/**
		 * Realiza validação da senha e sua confirmação
		 * @return true/false
		 */
		public void checkPassword() throws BusinessException {
			if (getPassword().isEmpty() || getPasswordConfirmation().isEmpty() || !Algorithm.validatePasswordPattern(getPassword())){
				throw new BusinessException("membership.your_data.message.validation.password");
			}			
			if (!getPassword().equals(getPasswordConfirmation())){
				throw new BusinessException("membership.your_data.message.validation.password.unequal");
			}
		}

	}	
}
