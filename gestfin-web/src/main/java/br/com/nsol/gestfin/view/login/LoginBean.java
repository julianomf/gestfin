package br.com.nsol.gestfin.view.login;

import java.io.IOException;
import java.security.Principal;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.view.base.BaseView;

/**
 * @author 
 */
@ManagedBean(name="loginBean")
@SessionScoped
public class LoginBean extends BaseView{
	private static final Logger LOGGER = Logger.getLogger(LoginBean.class);

	
	private String username;
	private String password;
	private static final String PAGE_HOME = "/pages/home/home.xhtml?faces-redirect=true";
	private static final String PAGE_RECOVER_PASSWORD = "/users/trocar-senha.xhtml?faces-redirect=true";
	private String showError = "none;";
	private String errorMessage;
 	
	/**
	 * Construtor da classe
	 */
	public LoginBean(){
		super();
		LOGGER.debug("construct: LoginBean");
	}
	
	/**
	 * Realiza login do usuário na aplicação
	 * @return "error" ou direciona para a pagina principal
	 * @throws IOException
	 * @throws ServletException
	 */
	public String actionLogin() throws IOException, ServletException {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
		
		LOGGER.debug("Realizando login");
		LOGGER.debug("\tUsername: " + this.username);
		LOGGER.debug("\tPassword: " + this.password);
		try {
			showError = "none;";
			
			if (getUserPrincipal() == null){
				
				// realiza o loogin
				request.login(this.username, this.password);
				
				// Salva usuário logado na sessão
				UserDTO userDTO = super.getFacade().findUserByMail(this.username);
				HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
				session.setAttribute(LOGGED_BUSINESS_USER, userDTO);
				
			}
			
			// Verifica se é necessário mudar a senha
			if ("S".equals(getLoggedUser().getIsPasswordReseted())){
				return PAGE_RECOVER_PASSWORD;
			}
			
		} catch (Exception e) {
			showError = "inline;";
			LOGGER.error("Error", e);
			if (e.getMessage() != null && e.getMessage().contains("UT010031")){
				errorMessage = getMessageBundle("login.error.user_password_invalid");
			} else {
				errorMessage = getMessageBundle(BUNDLE_UNKNOWN_ERROR);
			}

			return PAGE_LOGIN;
		}
		LOGGER.debug("Redirect para: " + request.getContextPath() + "/");
		
		//if (isUserInRole("S")){
		//	return PAGE_ADMIN_MONITOR;
		//}
		return PAGE_HOME;
	}

	public Principal getUserPrincipal() {
		return this.getRequest().getUserPrincipal();
	}
	
	/**
	 * User Name
	 * @return String
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Atribuir o User Name
	 * @param username Nome do usuário
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Retorna senha
	 * @return String
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Atribui senha
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Mostra a tela de erro para o usuário
	 * @return String
	 */
	public String getShowError(){
		return showError;
	}
	
	/**
	 * Erro mapeado
	 * @return erro
	 */
	public String getMessage(){
		return errorMessage;
	}
	
	/**
	 * Limpa os campos de login
	 */
	public String cleanFields(){
		setPassword(null);
		setUsername("");
		showError = "none;";
		
		return PAGE_LOGIN;
	}
}
