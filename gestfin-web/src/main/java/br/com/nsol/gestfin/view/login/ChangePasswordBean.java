package br.com.nsol.gestfin.view.login;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade;
import br.com.nsol.gestfin.utils.Algorithm;
import br.com.nsol.gestfin.view.base.BaseView;

/**
 * @author 
 */
@ManagedBean(name="changePasswordBean")
@ViewScoped
public class ChangePasswordBean extends BaseView {
	private static final Logger LOG = Logger.getLogger(ChangePasswordBean.class);
	private String currentPassword="";
	private String newPassword="";
	private String confirmPassword="";
	private boolean showError = false;
	private boolean showInfo = false;
	private AppraisalToolBussinessFacade apraisalFacade;
	public ChangePasswordBean(){
		super();
		
		apraisalFacade = super.getFacade();
	}
	
	/**
	 * Realiza a alteração de senha
	 */
	public void actionChangePassword(){
		LOG.debug("Executando alteração de senha");
		showError = false;
		showInfo = false;
		
		try {
		
			if (!getNewPassword().equals(getConfirmPassword())){
				addErrorMessage(getMessageBundle("message.new_password.unequal"));
				return;
			}
			
			if (!Algorithm.validatePasswordPattern(getNewPassword())){
				addErrorMessage(getMessageBundle("message.new_password.nonstandardized"));
				return;
			}
			
			if(getCurrentPassword().isEmpty() || getNewPassword().isEmpty()){
				addErrorMessage(getMessageBundle("message.temp_password.invalid"));
				return;
			}			
			
			//Realiza alteração de senha
			apraisalFacade.updateUserPassword(super.getLoggedUser().getUserMail(),
					Algorithm.generateMD5(getCurrentPassword()).toUpperCase(),
					Algorithm.generateMD5(getNewPassword()).toUpperCase());
			
			//Save the changed user
			FacesContext context = FacesContext.getCurrentInstance();
			UserDTO userDTO = super.getLoggedUser();
			userDTO.setIsPasswordReseted("N");
			HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
			session.setAttribute(LOGGED_BUSINESS_USER, userDTO);
			
			addInfoMessage(getMessageBundle("message.password_changed"));

		} catch (BusinessException be) {
			addErrorMessage(getMessageBundle(be.getType().getBundleKey()));
		} catch (Exception e) {
			LOG.error("Error:", e);
			addErrorMessage(getMessageBundle("message.comunication.error"));
		}
	}
	
	/**
	 * @return the currentPassword
	 */
	public String getCurrentPassword() {
		return currentPassword;
	}
	/**
	 * @param currentPassword the currentPassword to set
	 */
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	/**
	 * @return the newPassword
	 */
	public String getNewPassword() {
		return newPassword;
	}
	/**
	 * @param newPassword the newPassword to set
	 */
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	/**
	 * @return the confirmPassord
	 */
	public String getConfirmPassword() {
		return confirmPassword;
	}
	/**
	 * @param confirmPassord the confirmPassord to set
	 */
	public void setConfirmPassword(String confirmPassord) {
		this.confirmPassword = confirmPassord;
	}

	/**
	 * @return the showError
	 */
	public boolean isShowError() {
		return showError;
	}

	/**
	 * @return the showInfo
	 */
	public boolean isShowInfo() {
		return showInfo;
	}

	
	private void addInfoMessage(String message){
		FacesContext.getCurrentInstance().addMessage("frmChangePassword:changeButton", new FacesMessage(message, message));
		showInfo = true;

	}
	private void addErrorMessage(String message){
		FacesContext.getCurrentInstance().addMessage("frmChangePassword:changeButton", new FacesMessage(message, message));
		showError = true;
	}
	
}
