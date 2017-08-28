package br.com.nsol.gestfin.view.membership;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanyQuestionaireDTO;
import br.com.nsol.gestfin.dto.CompanySellDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.ValidateUtil;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Classe responsável pelo passo "O que a empresa vende" do wizard de adesão
 * @author 
 *
 */
@ViewScoped
@ManagedBean(name="msCompanySellView")
public class MembershipCompanySellView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipCompanySellView.class);
    private static final String MESSAGE_ID = null;
	private boolean hasError;
	
	private Integer selectedSellId;
	private List<CompanySellDTO> listSells;
    
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
	 * @return the selectedSellId
	 */
	public Integer getSelectedSellId() {
		return selectedSellId;
	}

	/**
	 * @param selectedSellId the selectedSellId to set
	 */
	public void setSelectedSellId(Integer selectedSellId) {
		this.selectedSellId = selectedSellId;
	}

	/**
	 * @return the listSells
	 */
	public List<CompanySellDTO> getListSells() {
		return listSells;
	}

	/**
	 * @param listSells the listSells to set
	 */
	public void setListSells(List<CompanySellDTO> listSells) {
		this.listSells = listSells;
	}

	/**
	 * Metodo de inicialização do bean
	 */
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		// Configura o passo atual da avaliação
		super.setCurrentMembershipStep(MEMBERSHIP_STEP_BUSINESS);
		
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
				this.listSells = super.getFacade().listMembershipSells();
				
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
				if (super.getMembershipInfo().getCompany().getQuestionaire().getSell() != null) {
					setSelectedSellId(super.getMembershipInfo().getCompany().getQuestionaire().getSell().getId());
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
			super.redirect(PAGE_MEMBERSHIP_COMPANY_EMPLOYEES);
			
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
			CompanySellDTO companySell = listSells.stream().filter(p-> p.getId().equals(getSelectedSellId())).findFirst().get();
			super.getMembershipInfo().getCompany().getQuestionaire().setSell(companySell);
			super.getFacade().saveCompanyQuestionaire(super.getMembershipInfo().getCompany().getQuestionaire());

			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_BUSINESS);

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
			
		ValidateCompanySell validator = new ValidateCompanySell();
		validator.checkSelectedSell();

	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateCompanySell {
		
		/**
		 * Realiza validação do que a empresa vende
		 * @return true/false
		 */
		public void checkSelectedSell() throws BusinessException {
			if (ValidateUtil.isNull(getSelectedSellId())){
				throw new BusinessException("membership.company_sell.message.validation.sell");
			}
		}

	}	
}
