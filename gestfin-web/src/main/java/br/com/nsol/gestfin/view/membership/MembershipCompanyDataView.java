package br.com.nsol.gestfin.view.membership;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanyDTO;
import br.com.nsol.gestfin.dto.TaxRegimeDTO;
import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.enums.CompanyStatusEnum;
import br.com.nsol.gestfin.enums.KindOfPersonType;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.ValidateUtil;
import br.com.nsol.gestfin.validator.CnpjValidator;
import br.com.nsol.gestfin.validator.CpfValidator;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Classe responsável pelo passo "Empresa" do wizard de adesão
 * @author 
 *
 */
@ViewScoped
@ManagedBean(name="msCompanyDataView")
public class MembershipCompanyDataView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipCompanyDataView.class);
    private static final String MESSAGE_ID = null;
	private boolean hasError;
	
	private String kindOfPerson;
	private Long documentNumber;
	private Long stateRegistration;
	private boolean freeFromStateRegistration;
	private String socialName;
	private String fantasyName;
	private Long municipalRegistration;
	private Integer taxRegimeId;
	private Integer cnae;
	private String sponsor;
	
	private List<TaxRegimeDTO> listTaxRegime;

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
	 * @return the kindOfPerson
	 */
	public String getKindOfPerson() {
		return kindOfPerson;
	}

	/**
	 * @param kindOfPerson the kindOfPerson to set
	 */
	public void setKindOfPerson(String kindOfPerson) {
		this.kindOfPerson = kindOfPerson;
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
	 * @return the stateRegistration
	 */
	public Long getStateRegistration() {
		return stateRegistration;
	}

	/**
	 * @param stateRegistration the stateRegistration to set
	 */
	public void setStateRegistration(Long stateRegistration) {
		this.stateRegistration = stateRegistration;
	}

	/**
	 * @return the freeFromStateRegistration
	 */
	public boolean isFreeFromStateRegistration() {
		return freeFromStateRegistration;
	}

	/**
	 * @param freeFromStateRegistration the freeFromStateRegistration to set
	 */
	public void setFreeFromStateRegistration(boolean freeFromStateRegistration) {
		this.freeFromStateRegistration = freeFromStateRegistration;
	}

	/**
	 * @return the socialName
	 */
	public String getSocialName() {
		return socialName;
	}

	/**
	 * @param socialName the socialName to set
	 */
	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	/**
	 * @return the fantasyName
	 */
	public String getFantasyName() {
		return fantasyName;
	}

	/**
	 * @param fantasyName the fantasyName to set
	 */
	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}

	/**
	 * @return the municipalRegistration
	 */
	public Long getMunicipalRegistration() {
		return municipalRegistration;
	}

	/**
	 * @param municipalRegistration the municipalRegistration to set
	 */
	public void setMunicipalRegistration(Long municipalRegistration) {
		this.municipalRegistration = municipalRegistration;
	}

	/**
	 * @return the taxRegimeId
	 */
	public Integer getTaxRegimeId() {
		return taxRegimeId;
	}

	/**
	 * @param taxRegimeId the taxRegimeId to set
	 */
	public void setTaxRegimeId(Integer taxRegimeId) {
		this.taxRegimeId = taxRegimeId;
	}

	/**
	 * @return the cnae
	 */
	public Integer getCnae() {
		return cnae;
	}

	/**
	 * @param cnae the cnae to set
	 */
	public void setCnae(Integer cnae) {
		this.cnae = cnae;
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
	 * @return the listTaxRegime
	 */
	public List<TaxRegimeDTO> getListTaxRegime() {
		return listTaxRegime;
	}

	/**
	 * @param listTaxRegime the listTaxRegime to set
	 */
	public void setListTaxRegime(List<TaxRegimeDTO> listTaxRegime) {
		this.listTaxRegime = listTaxRegime;
	}

	/**
	 * Retorna a lista de tipos de pessoa conforme enum
	 * @return
	 */
	public KindOfPersonType[] getKindsOfPerson(){
		return KindOfPersonType.values();
	}
	
	/**
	 * Metodo de inicialização do bean
	 */
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		// Configura o passo atual da avaliação
		super.setCurrentMembershipStep(MEMBERSHIP_STEP_COMPANY);
		
		try {
			listTaxRegime = super.getFacade().listTaxRegimess();
		} catch (TechnicalException e) {
			LOG.error("Error init (E)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}
		
		// Verifica se ainda tem a sessão válida com os dados do fluxo
		if (super.getMembershipInfo() == null) {
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
				if (super.getMembershipInfo().getCompany() == null) {
        			CompanyDTO company = super.getFacade().findCompanyByMail(super.getMembershipInfo().getUser().getUserMail());
        			if (company == null) {
        				company = new CompanyDTO();
        			}
        			super.getMembershipInfo().setCompany(company);
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
			setKindOfPerson(super.getMembershipInfo().getCompany().getKindOfPerson());
			setDocumentNumber(super.getMembershipInfo().getCompany().getDocumentNumber());
			setSocialName(super.getMembershipInfo().getCompany().getSocialName());
			setFantasyName(super.getMembershipInfo().getCompany().getFantasyName());
			setStateRegistration(super.getMembershipInfo().getCompany().getStateRegistration());
			setFreeFromStateRegistration(super.getMembershipInfo().getCompany().isFreeFromStateRegistration());
			setMunicipalRegistration(super.getMembershipInfo().getCompany().getMunicipalRegistration());
			if (super.getMembershipInfo().getCompany().getTaxRegime() != null) {
				setTaxRegimeId(super.getMembershipInfo().getCompany().getTaxRegime().getId());
			}
			setCnae(super.getMembershipInfo().getCompany().getCnae());
			setSponsor(super.getMembershipInfo().getCompany().getSponsor());
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
			super.redirect(PAGE_MEMBERSHIP_YOUR_DATA);
			
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

			//-- Neste ponto pode salvar o usuário sem perder o e-mail
			super.getMembershipInfo().getUser().setLastUpdateDate(new Date());
			if (super.getMembershipInfo().getUser().getUserId() == null) {
				super.getMembershipInfo().getUser().setUserId(
						super.getFacade().insertUserAndPassword(super.getMembershipInfo().getUser()));
			} else {
				super.getFacade().updateUserAndPassword(super.getMembershipInfo().getUser());
			}

			//-- O sistema salva os dados da empresa
			saveCompanyData();
			
			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_ADDRESS);

		} catch (BusinessException e) {
			LOG.error("Error actionNext (BE)", e);
			addWarnMessage(null, getMessageBundle(e.getMessage()));
			setHasError(true);
		} catch (TechnicalException e) {
			LOG.error("Error actionNext (TE)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionNext (E)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionNext");
	}

	/**
	 * O sistema salva os dados da empresa
	 * @throws TechnicalException
	 */
	private void saveCompanyData() throws TechnicalException {
		CompanyDTO company = super.getMembershipInfo().getCompany(); 
		company.setKindOfPerson(getKindOfPerson());
		company.setDocumentNumber(getDocumentNumber());
		company.setSocialName(getSocialName());
		company.setFantasyName(getFantasyName());
		company.setStateRegistration(getStateRegistration());
		company.setFreeFromStateRegistration(isFreeFromStateRegistration());
		company.setMunicipalRegistration(getMunicipalRegistration());
		Optional<TaxRegimeDTO> taxRegime = listTaxRegime.stream().filter(p-> p.getId().equals(getTaxRegimeId())).findFirst();
		if (taxRegime.isPresent()) {
			company.setTaxRegime(taxRegime.get());
		}
		company.setCnae(getCnae());
		company.setSponsor(getSponsor());
		if (super.getLoggedUser() == null) {
			company.setStatus(CompanyStatusEnum.UNFINISHED_REGISTRATION.getValue());
		} else {
			company.setStatus(CompanyStatusEnum.REGISTRATION_COMPLETED.getValue());
		}
		company.setOwnerId(super.getMembershipInfo().getUser().getUserId());
		company.setIpAddress(super.getRemoteAddress((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest()));
		company.setId(super.getFacade().saveCompany(company));
		super.getMembershipInfo().setCompany(company);
		super.getMembershipInfo().getUser().setCompanyId(company.getId());
	}
	
	/**
	 * Action executada ao clicar no botão Salvar
	 */
	public void actionSave() {
		LOG.debug("Iniciando action: actionSave");
		setHasError(false);

		try {

			validate();

			//-- O sistema salva os dados da empresa
			saveCompanyData();
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
			
		ValidateCompanyData validator = new ValidateCompanyData();
		validator.checkKindOfPerson();
		validator.checkDocumentNumber();
		validator.checkSocialName();
		validator.checkFantasyName();
		validator.checkStateRegistration();
		validator.checkMunicipalRegistration();
		validator.checkTaxRegime();
		validator.checkCnae();
		validator.checkSponsor();
		
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateCompanyData extends MembershipBaseView {
		
		/**
		 * Realiza validação do Tipo de Pessoa
		 * @return true/false
		 */
		public void checkKindOfPerson() throws BusinessException {
			if (ValidateUtil.isNull(getKindOfPerson())) {
				throw new BusinessException("membership.company_data.message.validation.kindofperson");
			}
		}

		/**
		 * Realiza validação do CPF/CNPJ
		 * @return true/false
		 */
		public void checkDocumentNumber() throws BusinessException {
			if (ValidateUtil.isNull(getDocumentNumber())) {
				throw new BusinessException("membership.company_data.message.validation.documentnumber");
			}
			// Valida o dígito verificador
			if (String.valueOf(getDocumentNumber()).length() > CPF_MAXLENGHT) {
				if(!CnpjValidator.isValid(String.valueOf(getDocumentNumber()))) {
					throw new BusinessException("membership.company_data.message.validation.documentnumber");
				}
			} else {
				if (!CpfValidator.isValid(String.valueOf(getDocumentNumber()))) {
					throw new BusinessException("membership.company_data.message.validation.documentnumber");
				}
			}
			try {
				
				CompanyDTO company = super.getFacade().findCompanyByDocumentNumber(getDocumentNumber());
				if (company != null) {
					if (super.getMembershipInfo().getCompany() == null ||
						!company.getId().equals(super.getMembershipInfo().getCompany().getId())) {
						throw new BusinessException("membership.company_data.message.validation.documentnumber.in_use");
					}
				}
				
			} catch (BusinessException e) {
				throw new BusinessException(e.getMessage());
			} catch (Exception e) {
				LOG.error("Error ValidateCompanyData (E)", e);
				throw new BusinessException(BUNDLE_UNKNOWN_ERROR);
			}
		}
		
		/**
		 * Realiza validação da Razão Social
		 * @return true/false
		 */
		public void checkSocialName() throws BusinessException {
			if (ValidateUtil.isMinLengthString(getSocialName(), NAME_MINLENGHT)){
				throw new BusinessException("membership.company_data.message.validation.socialname");
			}
		}

		/**
		 * Realiza validação do Nome Fantasia
		 * @return true/false
		 */
		public void checkFantasyName() throws BusinessException {
			if (ValidateUtil.isMinLengthString(getFantasyName(), NAME_MINLENGHT)){
				throw new BusinessException("membership.company_data.message.validation.fantasyname");
			}
		}

		/**
		 * Realiza validação da Inscrição Estadual
		 * @return true/false
		 */
		public void checkStateRegistration() throws BusinessException {
			if (!isFreeFromStateRegistration()) {
    			if (ValidateUtil.isNull(getStateRegistration()) || 
    				ValidateUtil.isMinLengthString(getStateRegistration().toString(), STATE_REGISTRATION_MINLENGHT)){
    				throw new BusinessException("membership.company_data.message.validation.stateregistration");
    			}
			}
		}

		/**
		 * Realiza validação da Inscrição Municipal
		 * @return true/false
		 */
		public void checkMunicipalRegistration() throws BusinessException {
			if (ValidateUtil.isNull(getMunicipalRegistration()) || 
				ValidateUtil.isMinLengthString(getMunicipalRegistration().toString(), MUNICIPAL_REGISTRATION_MINLENGHT)){
				throw new BusinessException("membership.company_data.message.validation.municipalregistration");
			}
		}

		/**
		 * Realiza validação do Regime de Tributação  
		 * @return true/false
		 */
		public void checkTaxRegime() throws BusinessException {
			if (ValidateUtil.isNull(getTaxRegimeId())) {
				throw new BusinessException("membership.company_data.message.validation.taxregime");
			}
		}

		/**
		 * Realiza validação do CNAE
		 * @return true/false
		 */
		public void checkCnae() throws BusinessException {
			if (ValidateUtil.isNull(getCnae()) || ValidateUtil.isMinLengthString(getCnae().toString(), CNAE_MINLENGHT)){
				throw new BusinessException("membership.company_data.message.validation.cnae");
			}
		}

		/**
		 * Realiza validação do Responsável  
		 * @return true/false
		 */
		public void checkSponsor() throws BusinessException {
			if (ValidateUtil.isMinLengthString(getSponsor(), NAME_MINLENGHT)){
				throw new BusinessException("membership.company_data.message.validation.sponsor");
			}
		}

	}	
}
