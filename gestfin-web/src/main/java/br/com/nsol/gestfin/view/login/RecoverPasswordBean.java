/**
 * 
 */
package br.com.nsol.gestfin.view.login;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.view.base.BaseView;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.facade.AppraisalToolBussinessFacade;
import br.com.nsol.gestfin.utils.Algorithm;

/**
 * @author 
 *
 */
@ManagedBean(name="recoverPasswordBean")
@SessionScoped
public class RecoverPasswordBean extends BaseView {
	private static final Logger LOG = Logger.getLogger(RecoverPasswordBean.class);
	private static final String MESSAGE_ID = "frmRecoverPassword";
	private AppraisalToolBussinessFacade apraisalFacade;
	private boolean showError = false;
	private boolean showInfo = false;
	private String email = "";

	/**
	 * Construtor
	 */
	public RecoverPasswordBean(){
		super();

		apraisalFacade = super.getFacade();
	}
	
	/**
	 * Solicita uma nova senha ao sistema
	 */
	public void actionRecoverPassword(){
		LOG.debug("Solicitando uma nova senha");
		showError = false;
		showInfo = false;
		try {

			if (getEmail().isEmpty() || !Algorithm.emailValidatePattern(getEmail())) {
				this.addErrorMessage(super.getMessageBundle("message.email.invalid"));
				return;
			}

			apraisalFacade.recoverNewPassword(getEmail());
			this.addInfoMessage(super.getMessageBundle("message.temp.password.sent"));
			
		} catch (BusinessException be) {
			this.addErrorMessage(super.getMessageBundle(be.getType().getBundleKey()));
		} catch (Exception e) {
			this.addErrorMessage(super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			LOG.error("Error", e);
		}
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

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	
	private void addInfoMessage(String message){
		FacesContext.getCurrentInstance().addMessage(MESSAGE_ID, new FacesMessage(message, message));
		showInfo = true;

	}
	private void addErrorMessage(String message){
		FacesContext.getCurrentInstance().addMessage(MESSAGE_ID, new FacesMessage(message, message));
		showError = true;
	}

}
