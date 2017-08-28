package br.com.nsol.gestfin.view.base;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade;
import br.com.nsol.gestfin.service.ServiceLocator;

/**
 * Classe base para todos os beans 
 * @author 
 */
public class BaseView {
	private static final Logger LOG = Logger.getLogger(BaseView.class);
	private static AppraisalToolBussinessFacade facade;
	protected static final String LOGGED_BUSINESS_USER = "LOGGED_BUSINESS_USER";
	protected static final String PAGE_LOGIN = "/login.jsf?faces-redirect=true";

	protected static final Integer USER_PROFILE_ADMINISTRATOR = 1;

    protected static final Integer NAME_MINLENGHT = 5;
    protected static final Integer PHONE_MINLENGHT = 10;
    protected static final Integer CPF_MAXLENGHT = 11;
    protected static final Integer CNAE_MINLENGHT = 4;
    protected static final Integer STATE_REGISTRATION_MINLENGHT = 2;
    protected static final Integer MUNICIPAL_REGISTRATION_MINLENGHT = 2;

    protected static final String STATUS_ACTIVE = "A";
    protected static final String STATUS_INACTIVE = "I";

    public static final String BUNDLE_UNKNOWN_ERROR = "application.error.unknown";
    
	public BaseView(){
		try {
			LOG.debug("BaseView Constructor");
			if (facade == null){
				facade = (AppraisalToolBussinessFacade) ServiceLocator.getService(AppraisalToolBussinessFacade.class);
			}
		} catch (TechnicalException e) {
			LOG.error("Error lookup services:", e);
		} 
	}
	
	/**
	 * Retorna os parâmetros recebidos pela página via URL
	 * @return
	 */
	public Map<String, String> getRequestParams() {
		Map<String, String> params = (Map<String, String>) FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
		if (params == null) {
			params = new HashMap<String, String>();
		}
		return params;
	}
	
	/**
	 * Metodo para requisitar o serviço da camada business do Appraisal Tool
	 * @return AppraisalToolBussinessFacade
	 */
	public AppraisalToolBussinessFacade getFacade(){
		return facade;
	}
	

	/**
	 * Redirecionamento padrão
	 * @param uri
	 * @throws IOException
	 */
	public void redirect(String uri) throws IOException {
		ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
		ec.redirect(ec.getRequestContextPath() + uri);
	}
	
	/**
	 * Retorna o usuário logado no sistema
	 * @return UserDTO
	 */
	public UserDTO getLoggedUser(){
		FacesContext context = FacesContext.getCurrentInstance();
		HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
		
		UserDTO userDTO = (UserDTO) session.getAttribute(LOGGED_BUSINESS_USER);
		
		return userDTO;
	}

	/**
	 * Retorna o perfil do usuário logado
	 * @return
	 */
	public Integer getLoggedUserProfile() {
		if (getLoggedUser() == null) {
			return null;
		} else {
			return getLoggedUser().getProfileId();
		}
	}

	/**
	 * Retorna o nome do usuário logado
	 * @return
	 */
	public String getLoggedUserName() {
		String name = "";
		if (getLoggedUser() != null) {
			String[] nameParts = getLoggedUser().getUserName().split(" ");
			name = nameParts[0].substring(0, Math.min(nameParts[0].length(), 15));
		}
		return name;
	}

	public String getMessageBundle(String key) {
		return FacesContext.getCurrentInstance().getApplication()
				.getResourceBundle(FacesContext.getCurrentInstance(), "i18n").getString(key);
	}
	
	public String getMessage(String key, Object... valueKey){
		MessageFormat mf = new MessageFormat(getMessageBundle(key));
		Object[] values = new Object[valueKey.length];
		
		for (int i=0; i < valueKey.length; i++) {
			values[i] = getMessageBundle(String.valueOf(valueKey[i]));
		}
		return mf.format(values);		
	}

	public String getMessageValues(String key, Object... valueKey){
		MessageFormat mf = new MessageFormat(getMessageBundle(key));
		Object[] values = new Object[valueKey.length];
		
		for (int i=0; i < valueKey.length; i++) {
			values[i] = String.valueOf(valueKey[i]);
		}
		return mf.format(values);		
	}

	protected HttpServletRequest getRequest() {
		FacesContext fc = FacesContext.getCurrentInstance();
		return (HttpServletRequest) fc.getExternalContext().getRequest();
	}
	/**
	 * Realiza logout do sistema
	 * @return "error" ou direciona para a tela de login
	 * @throws IOException
	 */
	public String actionLogout() throws IOException {
	    HttpServletRequest request = getRequest();
		try {
			LOG.debug("Realizando Logout");
			request.logout();
			LOG.debug("Invalidando a sessão");
			request.getSession(false).invalidate();
			LOG.debug("Redirecionando a pagina");
			
			return PAGE_LOGIN;
		} catch (ServletException e) {
		}
	    return "error";
	}

	/**
	 * Adiciona mensagem de erro
	 * @param id da mensagem de erro
	 * @param message Mensagem a ser disponibilizada
	 */
	protected void addErrorMessage(String key, String message) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null);
		FacesContext.getCurrentInstance().addMessage(key, facesMessage);
	}

	/**
	 * Adiciona mensagem de info
	 * @param id da mensagem de info
	 * @param message Mensagem a ser disponibilizada
	 */
	protected void addInfoMessage(String key, String message) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, null);
		FacesContext.getCurrentInstance().addMessage(key, facesMessage);
	}

	/**
	 * Adiciona mensagem de warn
	 * @param id da mensagem de warn
	 * @param message Mensagem a ser disponibilizada
	 */
	protected void addWarnMessage(String key, String message) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, message, null);
		FacesContext.getCurrentInstance().addMessage(key, facesMessage);
	}
	/**
	 * Verifica se existe mensagem a ser exibida
	 * @param facesMessageKey
	 * @return
	 */
	protected boolean hasMessageError(String facesMessageKey) {
		if (FacesContext.getCurrentInstance().getMessageList(facesMessageKey).size() > 0){
			return true;
		}
		return false;
	}

	/**
	 * Verifica se a Role passada é válida
	 * @param role
	 * @return
	 */
	public boolean isUserInRole(String role) {
		String[] roles = role.split(",");
		for (String rl : roles) {
			if (this.getRequest().isUserInRole(rl.trim())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Action padrão para nada fazer...
	 */
	public void actionEmpty() {
		//NOSONAR
	}

	/**
	 * Gets the remote address from a HttpServletRequest object. It prefers the `X-Forwarded-For` header, as this is the
	 * recommended way to do it (user may be behind one or more proxies).
	 *
	 * @param req the request object where to get the remote address from
	 * @return a string corresponding to the IP address of the remote machine
	 */
	public static String getRemoteAddress(HttpServletRequest req) {
	    String ipAddress = req.getHeader("X-FORWARDED-FOR");
	    if (ipAddress != null) {
	        ipAddress = ipAddress.replaceFirst(",.*", "");  // cares only about the first IP if there is a list
	    } else {
	        ipAddress = req.getRemoteAddr();
	    }
	    return ipAddress;
	}
}
