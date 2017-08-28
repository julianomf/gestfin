package br.com.nsol.gestfin.view.membership;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.ButtonSetIconsDTO;
import br.com.nsol.gestfin.dto.CompanyQuestionaireDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.ValidateUtil;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Classe responsável pelo passo "Empresa já emitiu notas fiscais?" do wizard de adesão
 * @author 
 *
 */
@ViewScoped
@ManagedBean(name="msCompanyInvoiceView")
public class MembershipCompanyInvoiceView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipCompanyInvoiceView.class);
    private static final String MESSAGE_ID = null;
	private boolean hasError;
	
	private Integer selectedInvoiceId;
	private List<ButtonSetIconsDTO> listInvoices;
    
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
	 * @return the selectedInvoiceId
	 */
	public Integer getSelectedInvoiceId() {
		return selectedInvoiceId;
	}

	/**
	 * @param selectedInvoiceId the selectedInvoiceId to set
	 */
	public void setSelectedInvoiceId(Integer selectedInvoiceId) {
		this.selectedInvoiceId = selectedInvoiceId;
	}

	/**
	 * @return the listInvoices
	 */
	public List<ButtonSetIconsDTO> getListInvoices() {
		return listInvoices;
	}

	/**
	 * @param listInvoices the listInvoices to set
	 */
	public void setListInvoices(List<ButtonSetIconsDTO> listInvoices) {
		this.listInvoices = listInvoices;
	}

	/**
	 * Metodo de inicialização do bean
	 */
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		// Configura o passo atual da avaliação
		super.setCurrentMembershipStep(MEMBERSHIP_STEP_INVOICES);
		
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
				
				// Carrega a lista de tamanhos de empresa
				List<ButtonSetIconsDTO> list = new ArrayList<ButtonSetIconsDTO>();
				try {
					
    				ButtonSetIconsDTO button = new ButtonSetIconsDTO();
    				button.setId(1);
    				button.setName(super.getMessageBundle("membership.company_invoices.yes.label"));
    				button.setDescription(null);
    				button.setIconReference("check_circle");
    				button.setIconType("G");
    				button.setButtonClass("col-xs-6 col-sm-6");
    				list.add(button.clone());
    				
    				button = new ButtonSetIconsDTO();
    				button.setId(0);
    				button.setName(super.getMessageBundle("membership.company_invoices.no.label"));
    				button.setDescription(null);
    				button.setIconReference("cancel");
    				button.setIconType("G");
    				button.setButtonClass("col-xs-6 col-sm-6");
    				list.add(button.clone());
    				
				} catch (Exception e) {
					LOG.error("Error init (E)", e);
					addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
					setHasError(true);
				}
				this.listInvoices = list;

				// Busca os dados do questionário da empresa
				if (super.getMembershipInfo().getCompany().getQuestionaire() == null) {
        			CompanyQuestionaireDTO questionaire = super.getFacade().findCompanyQuestionaire(super.getMembershipInfo().getCompany().getId());
        			if (questionaire == null) {
        				questionaire = new CompanyQuestionaireDTO();
        				questionaire.setCompanyId(super.getMembershipInfo().getCompany().getId());
        			}
        			super.getMembershipInfo().getCompany().setQuestionaire(questionaire);
				}

				// Carrega dados pré-cadastrados
				setSelectedInvoiceId(Boolean.TRUE.equals(super.getMembershipInfo().getCompany().getQuestionaire().isHasIssuedInvoices()) ? 1 : 0);

			} catch (TechnicalException e) {
				LOG.error("Error init (E)", e);
				addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			}
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
			super.redirect(PAGE_MEMBERSHIP_COMPANY_ISSUES);
			
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
			super.getMembershipInfo().getCompany().getQuestionaire().setHasIssuedInvoices(getSelectedInvoiceId().equals(1));
			super.getFacade().saveCompanyQuestionaire(super.getMembershipInfo().getCompany().getQuestionaire());
			
			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_CONFIRMATION);

		} catch (BusinessException e) {
			LOG.error("Error actionNext (BE)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(e.getMensagem()));
			setHasError(true);
			} catch (TechnicalException e) {
			LOG.error("Error actionNext (TE)", e);
			addErrorMessage(MESSAGE_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
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
			
		ValidateCompanyInvoices validator = new ValidateCompanyInvoices();
		validator.checkSelectedInvoice();
		
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateCompanyInvoices {
		
		/**
		 * Realiza validação sobre a emissão de notas fiscais
		 * @return true/false
		 */
		public void checkSelectedInvoice() throws BusinessException {
			if (ValidateUtil.isNull(getSelectedInvoiceId())){
				throw new BusinessException("membership.company_invoices.message.validation.selected");
			}
		}

	}	
}
