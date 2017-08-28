package br.com.nsol.gestfin.view.membership;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanyDTO;
import br.com.nsol.gestfin.dto.MembershipDTO;
import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.enums.CompanyStatusEnum;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.Algorithm;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Bean referente as funcionalidades da adesão de empresa
 * @author 
 */
@ManagedBean(name="membershipView")
@ViewScoped
public class MembershipView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipView.class);

    private boolean hasError;
    private String errorMessage;
    private String userMail;
	
	/**
	 * construtor padrão
	 */
	public MembershipView() {
		
	}
	
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		// Configura o passo atual da avaliação
		super.setCurrentMembershipStep(MEMBERSHIP_STEP_COMPANY);

		// Verifica se tem a sessão válida com os dados do fluxo
		if (super.getMembershipInfo() == null || super.getMembershipInfo().getUser() == null) {
			super.setMembershipInfo(new MembershipDTO());
			super.getMembershipInfo().setUser(new UserDTO());
		}
		setUserMail(super.getMembershipInfo().getUser().getUserMail());

		LOG.debug("Finalizada action: init");
	}

	/**
	 * Action inicial
	 */
	public void actionStart(){
		LOG.debug("Iniciando action: actionStart");
		
		try {

			validate();

			// Atualiza o objeto da sessão
			super.getMembershipInfo().getUser().setUserMail(getUserMail());
			
			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_YOUR_DATA);

		} catch (BusinessException e) {
			LOG.debug("Error actionNext (BE)", e);
			setErrorMessage(getMessageBundle(e.getMessage()));
			setHasError(true);
		} catch (Exception e){
			LOG.error("Error actionBack (E)", e);
			setErrorMessage(getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}
		
		LOG.debug("Finalizada action: actionStart");
	}
	
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
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * @return the userMail
	 */
	public String getUserMail() {
		return userMail;
	}

	/**
	 * @param userMail the userMail to set
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	/**
	 * Realiza a consistência dos dados informados
	 * @throws TechnicalException
	 */
	private void validate() throws BusinessException {
		
		ValidateMembership validate = new ValidateMembership();
		validate.checkEmail();
		
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateMembership extends MembershipBaseView {

		/**
		 * Validação do email
		 * @return true/false
		 */
		public void checkEmail() throws BusinessException {
			if (getUserMail() == null || !Algorithm.emailValidatePattern(getUserMail())) {
				throw new BusinessException("message.invalid.email.error");
			}
			try {
				
				CompanyDTO company = super.getFacade().findCompanyByMail(getUserMail());
				if (company != null) {
					
					// Verifica se o e-mail já está associado a uma empresa registrada (Apenas uma empresa por e-mail!)
					// Se não tiver finalizado o registro pode continuar o processo...
					if (CompanyStatusEnum.REGISTRATION_COMPLETED.getValue().equals(company.getStatus())) {
						throw new BusinessException("membership.message.validation.company_with_mail");
					}
					
				} else {
				
    				// Novas empresas apenas com usuários não ainda não cadastrados!
    				UserDTO user = super.getFacade().findUserByMail(getUserMail());
    				if (user != null) {
						throw new BusinessException("membership.message.validation.email_in_use");
    				}
    				
				}
				
			} catch (BusinessException e) {
				throw new BusinessException(e.getMessage());
			} catch (Exception e) {
				LOG.error("Error ValidateMembership (E)", e);
				throw new BusinessException(BUNDLE_UNKNOWN_ERROR);
			}
		}

	}
}
