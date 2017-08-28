package br.com.nsol.gestfin.view.membership;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanyIssueDTO;
import br.com.nsol.gestfin.dto.CompanyQuestionaireDTO;
import br.com.nsol.gestfin.dto.CompanySizeDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Classe responsável pelo passo "Empresa" do wizard de adesão
 * @author 
 *
 */
@ViewScoped
@ManagedBean(name="msCompanyIssuesView")
public class MembershipCompanyIssuesView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipCompanyIssuesView.class);
    private static final String MESSAGE_ID = null;
	private boolean hasError;
	
	private List<CompanyIssueDTO> listIssues;
    
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
	 * @return the listIssues
	 */
	public List<CompanyIssueDTO> getListIssues() {
		return listIssues;
	}

	/**
	 * @param listIssues the listIssues to set
	 */
	public void setListIssues(List<CompanyIssueDTO> listIssues) {
		this.listIssues = listIssues;
	}

	/**
	 * Metodo de inicialização do bean
	 */
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		// Configura o passo atual da avaliação
		super.setCurrentMembershipStep(MEMBERSHIP_STEP_ISSUES);
		
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
				this.listIssues = super.getFacade().listMembershipIssues();

				// Busca os dados do questionário da empresa
				if (super.getMembershipInfo().getCompany().getQuestionaire() == null) {
        			CompanyQuestionaireDTO questionaire = super.getFacade().findCompanyQuestionaire(super.getMembershipInfo().getCompany().getId());
        			if (questionaire == null) {
        				questionaire = new CompanyQuestionaireDTO();
        				questionaire.setCompanyId(super.getMembershipInfo().getCompany().getId());
        			}
        			super.getMembershipInfo().getCompany().setQuestionaire(questionaire);
				} else {
					if (super.getMembershipInfo().getCompany().getQuestionaire().getIssues() == null) {
	        			List<CompanyIssueDTO> issues = super.getFacade().listCompanyQuestionaireIssues(super.getMembershipInfo().getCompany().getId());
	        			if (issues == null) {
	        				issues = new ArrayList<CompanyIssueDTO>();
	        			}
	        			super.getMembershipInfo().getCompany().getQuestionaire().setIssues(issues);
					} 
				}

				// Carrega dados pré-cadastrados
				if (super.getMembershipInfo().getCompany().getQuestionaire().getIssues() != null) {
    				for (CompanyIssueDTO issue : listIssues) {
    					Optional<CompanyIssueDTO> sel = super.getMembershipInfo().getCompany().getQuestionaire().getIssues().stream()
    														.filter(p-> p.getId().equals(issue.getId())).findFirst();
    					issue.setSelected(sel.isPresent());
    				}
				}

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
			super.redirect(PAGE_MEMBERSHIP_COMPANY_BUSINESS);
			
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
			super.getMembershipInfo().getCompany().getQuestionaire().setIssues(new ArrayList<CompanyIssueDTO>());
			for (CompanyIssueDTO issue : listIssues) {
				if (issue.isSelected()) {
					super.getMembershipInfo().getCompany().getQuestionaire().getIssues().add(issue);
				}
			}
			super.getFacade().saveCompanyQuestionaireIssues(
					super.getMembershipInfo().getCompany().getId(), 
					super.getMembershipInfo().getCompany().getQuestionaire().getIssues());
				
			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_INVOICES);

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
			
		ValidateCompanyIssue validator = new ValidateCompanyIssue();
		validator.checkIssue();
		
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateCompanyIssue {
		
		/**
		 * Realiza validação de seleção de problemas
		 * @return true/false
		 */
		public void checkIssue() throws BusinessException {
			boolean isSelected = false;
			for (CompanyIssueDTO issue : listIssues) {
				if (issue.isSelected()) {
					isSelected = true;
					break;
				}
			}
			if (!isSelected){
				throw new BusinessException("membership.company_issues.message.validation.selected");
			}
		}

	}	
}
