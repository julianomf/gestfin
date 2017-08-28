package br.com.nsol.gestfin.view.membership;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Classe responsável pelo passo "Confirmação" do wizard de adesão
 * @author 
 *
 */
@ViewScoped
@ManagedBean(name="msCompanyConfirmView")
public class MembershipCompanyConfirmView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipCompanyConfirmView.class);
    private static final String MESSAGE_ID = null;
	private boolean hasError;
	
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
	 * Metodo de inicialização do bean
	 */
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		// Configura o passo atual da avaliação
		super.setCurrentMembershipStep(MEMBERSHIP_STEP_FINISH);
		
		// Verifica se ainda tem a sessão válida com os dados do fluxo
		if (super.getMembershipInfo() == null || super.getMembershipInfo().getUser() == null || super.getMembershipInfo().getCompany() == null ) {
			try {
				
				// Redireciona para o início da adesão
				super.redirect(PAGE_MEMBERSHIP_NEW);
				
			} catch (IOException e) {
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
			super.redirect(PAGE_MEMBERSHIP_COMPANY_INVOICES);
			
		} catch (Exception e){
			LOG.error("Error actionBack (E)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		}
		
		LOG.debug("Finalizada action: actionBack");
	}

	/**
	 * Action executada ao clicar no botão Concluir
	 */
	public void actionFinish() {
		LOG.debug("Iniciando action: actionFinish");
		setHasError(false);

		try {

			//-- Marca o cadastro de adesão como finalizado
			super.getFacade().finishCompanyMembership(super.getMembershipInfo().getCompany());
			
			//-- Realiza o login automático do usuário
			FacesContext context = FacesContext.getCurrentInstance();
			HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
			request.login(super.getMembershipInfo().getUser().getUserMail(), "md5:" + super.getMembershipInfo().getUser().getUserPassword());
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute(LOGGED_BUSINESS_USER, super.getMembershipInfo().getUser());
						
			// O sistema redireciona o usuário para home
			super.redirect(PAGE_EMPTY_HOME);

		} catch (TechnicalException e) {
			LOG.error("Error actionFinish (TE)", e);
			addErrorMessage(MESSAGE_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionFinish (E)", e);
			addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionFinish");
	}
}
