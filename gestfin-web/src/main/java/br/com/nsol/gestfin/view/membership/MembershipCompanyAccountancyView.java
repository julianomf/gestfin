package br.com.nsol.gestfin.view.membership;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanyAccountancyDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.Algorithm;
import br.com.nsol.gestfin.utils.ValidateUtil;
import br.com.nsol.gestfin.validator.CnpjValidator;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Classe responsável pelo passo "Empresa" do wizard de adesão
 * @author 
 *
 */
@ViewScoped
@ManagedBean(name="msCompanyAccountancyView")
public class MembershipCompanyAccountancyView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipCompanyAccountancyView.class);
    private static final String MESSAGE_ID = null;
	private boolean hasError;
	
	private String name;
	private Long documentNumber;
	private String sponsor;
	private Long phoneNumber;
	private String mail;
	
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
	 * @return the documentNumber
	 */
	public Long getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber the documentNumber to set
	 */
	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * @return the sponsor
	 */
	public String getSponsor() {
		return sponsor;
	}

	/**
	 * @param sponsor the sponsor to set
	 */
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	/**
	 * @return the phoneNumber
	 */
	public Long getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
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
		if (super.getMembershipInfo() == null || super.getMembershipInfo().getCompany() == null) {
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
				
				// Busca os dados da contabilidade da empresa
				if (super.getMembershipInfo().getCompany().getAccountancy() == null) {
        			CompanyAccountancyDTO accountancy = super.getFacade().findCompanyAccountancy(super.getMembershipInfo().getCompany().getId());
        			if (accountancy == null) {
        				accountancy = new CompanyAccountancyDTO();
        			}
        			super.getMembershipInfo().getCompany().setAccountancy(accountancy);
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

			// Carrega dados
			setName(super.getMembershipInfo().getCompany().getAccountancy().getName());
			setDocumentNumber(super.getMembershipInfo().getCompany().getAccountancy().getDocumentNumber());
			setSponsor(super.getMembershipInfo().getCompany().getAccountancy().getSponsor());
			setPhoneNumber(super.getMembershipInfo().getCompany().getAccountancy().getPhoneNumber());
			setMail(super.getMembershipInfo().getCompany().getAccountancy().getMail());
		}
		
		LOG.debug("Finalizada action: init");
	}

	/**
	 * Action executada ao clicar no botão Voltar
	 */
	public void actionBack() {
		LOG.debug("Iniciando action: actionBack");

		try {

			// O sistema redireciona o usuário para página anterior do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_CONTACTS);
			
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
			saveCompanyAccountancy();
				
			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_SIZE);

		} catch (BusinessException e) {
			LOG.error("Error actionNext (BE)", e);
			addWarnMessage(MESSAGE_ID, super.getMessageBundle(e.getMessage()));
			setHasError(true);
		} catch (TechnicalException e) {
			LOG.error("Error actionNext (TE)", e);
			addErrorMessage(MESSAGE_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionNext (E)", e);
			addErrorMessage(MESSAGE_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionNext");
	}

	public void saveCompanyAccountancy() throws TechnicalException {

		// -- O sistema salva os dados
		CompanyAccountancyDTO accountancy = new CompanyAccountancyDTO();
		accountancy.setName(getName());
		accountancy.setDocumentNumber(getDocumentNumber());
		accountancy.setSponsor(getSponsor());
		accountancy.setPhoneNumber(getPhoneNumber());
		accountancy.setMail(getMail());
		accountancy.setCompanyId(super.getMembershipInfo().getCompany().getId());
		super.getFacade().saveCompanyAccountancy(accountancy);
		super.getMembershipInfo().getCompany().setAccountancy(accountancy);

	}
	
	/**
	 * Action executada ao clicar no botão Salvar
	 */
	public void actionSave() {
		LOG.debug("Iniciando action: actionSave");
		setHasError(false);

		try {

			validate();

			//-- O sistema salva os dados 
			saveCompanyAccountancy();
			addInfoMessage(null, getMessageBundle("message.operation.success"));

		} catch (BusinessException e) {
			LOG.error("Error actionSave (BE)", e);
			addWarnMessage(null, getMessageBundle(e.getMessage()));
			setHasError(true);
		} catch (TechnicalException e) {
			LOG.error("Error actionSave (TE)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionSave (E)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionSave");
	}
	
	/**
	 * Faz a validação dos dados do formulário
	 * @return
	 */
	private void validate() throws BusinessException {
			
		ValidateCompanyAccountancy validator = new ValidateCompanyAccountancy();
		validator.checkName();
		validator.checkDocumentNumber();
		validator.checkSponsor();
		validator.checkPhoneNumber();
		validator.checkMail();
		
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateCompanyAccountancy {
		
		/**
		 * Realiza validação do nome  
		 * @return true/false
		 */
		public void checkName() throws BusinessException {
			if (ValidateUtil.isMinLengthString(getName(), NAME_MINLENGHT)){
				throw new BusinessException("membership.company_accountancy.message.validation.name");
			}
		}

		/**
		 * Realiza validação do CPF/CNPJ
		 * @return true/false
		 */
		public void checkDocumentNumber() throws BusinessException {
			if(ValidateUtil.isNull(getDocumentNumber()) || !CnpjValidator.isValid(String.valueOf(getDocumentNumber()))) {
				throw new BusinessException("membership.company_accountancy.message.validation.documentnumber");
			}
		}

		/**
		 * Realiza validação do Responsável  
		 * @return true/false
		 */
		public void checkSponsor() throws BusinessException {
			if (ValidateUtil.isMinLengthString(getSponsor(), NAME_MINLENGHT)){
				throw new BusinessException("membership.company_accountancy.message.validation.sponsor");
			}
		}

		/**
		 * Validação do telefone
		 * @return true/false
		 */
		public void checkPhoneNumber() throws BusinessException {
			if (ValidateUtil.isNull(getPhoneNumber()) || ValidateUtil.isMinLengthString(getPhoneNumber().toString(), PHONE_MINLENGHT)) {
				throw new BusinessException("membership.company_accountancy.message.validation.phone_number");
			}
		}

		/**
		 * Validação do email
		 * @return true/false
		 */
		public void checkMail() throws BusinessException {
			if (getMail() == null || !Algorithm.emailValidatePattern(getMail())) {
				throw new BusinessException("membership.company_accountancy.message.validation.mail");
			}
		}

	}	
}
