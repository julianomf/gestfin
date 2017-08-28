/**
 * 
 */
package br.com.nsol.gestfin.view.base;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.MembershipDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.view.membership.MembershipCompanyDataView;

/**
 * @author 
 *
 */
@ManagedBean(name="pageNavigationBean")
@ViewScoped
public class PageNavigationBean extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipCompanyDataView.class);
	
	/**
	 * Declaração das constantes da classe
	 */
	
	private static final String CHECK_INACTIVE_TIMEOUT = "CHECK_INACTIVE_TIMEOUT";
	private static final String CHECK_INACTIVE_INTERVAL = "CHECK_INACTIVE_INTERVAL";

	private static final String JAVASCRIPT_VERSION = "2017.08.07-10.13";
	private static final String CSS_VERSION = "2017.08.10-22.37";

	private String page;

	
	/**
	 * Inicia todos os dados da home
	 */
	@PostConstruct
	public void init(){
		setPage("home");
	}
	
	/**
	 * @return the page
	 */
	public String getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(String page) {
		this.page = page;
	}

	/**
	 * @return the checkInactiveTimeout
	 */
	public long getCheckInactiveTimeout() {
		
		Long value = new Long(20 * 60 * 1000);
		
		String temp = System.getProperty(CHECK_INACTIVE_TIMEOUT);
		
		if (temp != null &&	!temp.isEmpty()) {
			value = Long.parseLong(temp) * 60 * 1000;
		}

		return value;
	}

	/**
	 * @return the checkInactiveInterval
	 */
	public long getCheckInactiveInterval() {
		
		Long value = new Long(20 * 1000);
		
		String temp = System.getProperty(CHECK_INACTIVE_INTERVAL);
		
		if (temp != null &&	!temp.isEmpty()) {
			value = Long.parseLong(temp) * 1000;
		}

		return value;
	}
	
	/**
	 * Flag que indica que o usuário está logado
	 * @return
	 */
	public boolean isUserLogged() {
		return super.getLoggedUser() != null;
	}
	
	/**
	 * Flag que indica que o usuário logado é um proprietário de emprsa
	 */
	public Integer getUserLoggedOwnerCompanyId() {
		return super.getLoggedUser().getCompanyId();
	}

	/**
	 * Retorna a versão corrente do arquivo Javascript
	 * @return
	 */
	public String getJavaScriptVersion() {
		return JAVASCRIPT_VERSION;
	}
	
	/**
	 * Retorna a versão corrente do arquivo CSS
	 * @return
	 */
	public String getCssVersion() {
		return CSS_VERSION;
	}

	public void actionCompanyData() {
		LOG.debug("Iniciando action: actionCompanyData");
		try {

			// Carrega os dados para poder acessar a página de dados da empresa reaproveitada do processo de adesão
			MembershipDTO membershipInfo = new MembershipDTO();
			membershipInfo.setUser(getLoggedUser());
			membershipInfo.setCompany(super.getFacade().findCompanyById(getLoggedUser().getCompanyId()));
			super.setMembershipInfo(membershipInfo);
			
			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_DATA);

		} catch (TechnicalException e) {
			LOG.error("Error actionCompanyData (TE)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		} catch (Exception e) {
			LOG.error("Error actionCompanyData (E)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		}
		LOG.debug("Finalizada action: actionCompanyData");
		
	}

	public void actionCompanyAddress() {
		LOG.debug("Iniciando action: actionCompanyAddress");
		try {

			// Carrega os dados para poder acessar a página de dados da empresa reaproveitada do processo de adesão
			MembershipDTO membershipInfo = new MembershipDTO();
			membershipInfo.setUser(getLoggedUser());
			membershipInfo.setCompany(super.getFacade().findCompanyById(getLoggedUser().getCompanyId()));
			super.setMembershipInfo(membershipInfo);
			
			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_ADDRESS);

		} catch (TechnicalException e) {
			LOG.error("Error actionCompanyAddress (TE)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		} catch (Exception e) {
			LOG.error("Error actionCompanyAddress (E)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		}
		LOG.debug("Finalizada action: actionCompanyAddress");
		
	}

	public void actionCompanyContact() {
		LOG.debug("Iniciando action: actionCompanyContact");
		try {

			// Carrega os dados para poder acessar a página de dados da empresa reaproveitada do processo de adesão
			MembershipDTO membershipInfo = new MembershipDTO();
			membershipInfo.setUser(getLoggedUser());
			membershipInfo.setCompany(super.getFacade().findCompanyById(getLoggedUser().getCompanyId()));
			super.setMembershipInfo(membershipInfo);
			
			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_CONTACTS);

		} catch (TechnicalException e) {
			LOG.error("Error actionCompanyContact (TE)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		} catch (Exception e) {
			LOG.error("Error actionCompanyContact (E)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		}
		LOG.debug("Finalizada action: actionCompanyContact");
		
	}

	public void actionCompanyAccountancy() {
		LOG.debug("Iniciando action: actionCompanyAccountancy");
		try {

			// Carrega os dados para poder acessar a página de dados da empresa reaproveitada do processo de adesão
			MembershipDTO membershipInfo = new MembershipDTO();
			membershipInfo.setUser(getLoggedUser());
			membershipInfo.setCompany(super.getFacade().findCompanyById(getLoggedUser().getCompanyId()));
			super.setMembershipInfo(membershipInfo);
			
			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_ACCOUNTANCY);

		} catch (TechnicalException e) {
			LOG.error("Error actionCompanyAccountancy (TE)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		} catch (Exception e) {
			LOG.error("Error actionCompanyAccountancy (E)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		}
		LOG.debug("Finalizada action: actionCompanyAccountancy");
		
	}
}
