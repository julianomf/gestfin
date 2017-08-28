package br.com.nsol.gestfin.view.base;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.MembershipDTO;

/**
 * Classe base para os beans de adesão de empresa 
 * @author 
 */
public class MembershipBaseView extends BaseView {
	private static final Logger LOG = Logger.getLogger(MembershipBaseView.class);
	
    // Passos da migalha de pão
	protected static final Integer MEMBERSHIP_STEP_COMPANY = 1;
	protected static final Integer MEMBERSHIP_STEP_SIZE = 2;
	protected static final Integer MEMBERSHIP_STEP_BUSINESS = 3;
	protected static final Integer MEMBERSHIP_STEP_ISSUES = 4;
	protected static final Integer MEMBERSHIP_STEP_INVOICES = 5;
	protected static final Integer MEMBERSHIP_STEP_FINISH = 6;

	// Páginas que pode navegar
    protected static final String PAGE_EMPTY_HOME = "/pages/home/home.jsf";
    protected static final String PAGE_MEMBERSHIP_NEW = "/adesao/adesao.jsf";
    protected static final String PAGE_MEMBERSHIP_YOUR_DATA = "/adesao/seus-dados.jsf";
    protected static final String PAGE_MEMBERSHIP_COMPANY_DATA = "/adesao/dados-da-empresa.jsf";
    protected static final String PAGE_MEMBERSHIP_COMPANY_ADDRESS = "/adesao/enderecos-da-empresa.jsf";
    protected static final String PAGE_MEMBERSHIP_COMPANY_CONTACTS = "/adesao/contatos-da-empresa.jsf";
    protected static final String PAGE_MEMBERSHIP_ACCOUNTANCY = "/adesao/contabilidade-da-empresa.jsf";
    protected static final String PAGE_MEMBERSHIP_COMPANY_SIZE = "/adesao/tamanho-da-empresa.jsf";
    protected static final String PAGE_MEMBERSHIP_COMPANY_EMPLOYEES = "/adesao/colaboradores-da-empresa.jsf";
    protected static final String PAGE_MEMBERSHIP_COMPANY_SELL = "/adesao/o-que-a-empresa-vende.jsf";
    protected static final String PAGE_MEMBERSHIP_COMPANY_BUSINESS = "/adesao/negocio-da-empresa.jsf";
    protected static final String PAGE_MEMBERSHIP_COMPANY_ISSUES = "/adesao/problemas-da-empresa.jsf";
    protected static final String PAGE_MEMBERSHIP_COMPANY_INVOICES = "/adesao/empresa-ja-emitiu-nf.jsf";
    protected static final String PAGE_MEMBERSHIP_CONFIRMATION = "/adesao/confirmacao-adesao.jsf";

	private Integer currentMembershipStep;
	private Integer lastValidatedMembershipStep;
	
	private boolean hideBreadcumb = false;

	/**
	 * Navega para uma página de navegação padrão quando ocorre algum erro crítico na adesão
	 */
	public void redirectToMembershipLoadErrorPage() {
		try {
			super.redirect(PAGE_MEMBERSHIP_NEW);
		} catch (IOException e) {
			LOG.error("Error in redirectToMembershipLoadErrorPage", e);
		}
	}
	
	/**
	 * Retorna a classe CSS para aplicar na navegação inferior da página
	 * @param step
	 * @return
	 */
	public String mainNavigationStepClass(String step) {
		String sepClass = "";
		if (Integer.parseInt(step) + 1 < getLastValidatedMembershipStep()) {
			sepClass = "sep-red";
		} else {
			if (Integer.parseInt(step) < getLastValidatedMembershipStep() ||
				Integer.parseInt(step) < getCurrentMembershipStep()) {
				sepClass = "sep-red";
			} else {
				sepClass = "sep-black";
			}
		}
		if (Integer.parseInt(step) == getCurrentMembershipStep()) {
			return "current " + sepClass;
		} else {
			if (Integer.parseInt(step) <= getLastValidatedMembershipStep()) {
				return "completed " + sepClass;
			} else {
				return "unexecuted " + sepClass;
			}
		}
	}

	/**
	 * Verifica se pode navegar para um passo específico dentro da avaliação veículo
	 * @param step
	 * @return
	 */
	public boolean membershipStepNavigation(Integer step) {
		boolean isEnabled = false;
		if (!step.equals(getCurrentMembershipStep()) && 
			(step.intValue() <= getLastValidatedMembershipStep().intValue() ||
			 step.intValue() <= getCurrentMembershipStep().intValue())) {
			isEnabled = true;
		}
		return isEnabled;
	}

	/**
	 * Navega para um passo específico da adesão da empresa
	 * @param membershipStep
	 * @throws IOException
	 */
	public void navigateTo(Integer membershipStep) throws IOException {
		switch (membershipStep) {
		case 1: //MEMBERSHIP_STEP_COMPANY_DATA: 	//-- Seus dados
			super.redirect(PAGE_MEMBERSHIP_YOUR_DATA);
			break;
		case 2: //MEMBERSHIP_STEP_COMPANY_SIZE: 	//-- Tamanho da empresa
			super.redirect(PAGE_MEMBERSHIP_COMPANY_SIZE);
			break;
		case 3: //MEMBERSHIP_STEP_COMPANY_BUSINESS: 	//-- O que a empresa vende
			super.redirect(PAGE_MEMBERSHIP_COMPANY_SELL);
			break;
		case 4: //MEMBERSHIP_STEP_COMPANY_ISSUES: 	//-- Problemas da empresa
			super.redirect(PAGE_MEMBERSHIP_COMPANY_ISSUES);
			break;
		case 5: //MEMBERSHIP_STEP_COMPANY_INVOICES: 	//-- Empresa já emitiu notas fiscais
			super.redirect(PAGE_MEMBERSHIP_COMPANY_INVOICES);
			break;
		default:	//-- Qualquer outro manda para a página inicial da adesão
			super.redirect(PAGE_MEMBERSHIP_NEW);
			break;
		}
	}
	
	/**
	 * @return the currentMembershipStep
	 */
	public Integer getCurrentMembershipStep() {
		return (currentMembershipStep == null ? -1 : currentMembershipStep);
	}

	/**
	 * @param currentMembershipStep the currentMembershipStep to set
	 */
	public void setCurrentMembershipStep(Integer currentMembershipStep) {
		this.currentMembershipStep = currentMembershipStep;
		this.lastValidatedMembershipStep = currentMembershipStep;
	}
	
	/**
	 * @return the lastValidatedMembershipStep
	 */
	public Integer getLastValidatedMembershipStep() {
		return (lastValidatedMembershipStep == null ? -1 : lastValidatedMembershipStep);
	}

	/**
	 * @param lastValidatedMembershipStep the lastValidatedMembershipStep to set
	 */
	public void setLastValidatedMembershipStep(Integer lastValidatedMembershipStep) {
		this.lastValidatedMembershipStep = lastValidatedMembershipStep;
	}

	/**
	 * @return the appraisalInfo
	 */
	public MembershipDTO getMembershipInfo() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		MembershipDTO membershipInfo = (MembershipDTO) sessionMap.get("membershipInfo");
		return membershipInfo;
	}

	/**
	 * @param appraisalInfo the appraisalInfo to set
	 */
	public void setMembershipInfo(MembershipDTO membershipInfo) {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.put("membershipInfo", membershipInfo);
	}


	/**
	 * @return the hideBreadcumb
	 */
	public boolean isHideBreadcumb() {
		return hideBreadcumb;
	}
	

	/**
	 * @param hideBreadcumb the hideBreadcumb to set
	 */
	public void setHideBreadcumb(boolean hideBreadcumb) {
		this.hideBreadcumb = hideBreadcumb;
	}

	/**
	 * Algumas páginas podem ter 2 templates, um no processo de adesão, outro para acesso direto de um owner de empresa
	 * @return
	 */
	public String getPageTemplate() {
		if (super.getLoggedUser() == null) {
			return "template-membership";
		} else {
			return "template";
		}
	}
	
}
