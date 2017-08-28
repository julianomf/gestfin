package br.com.nsol.gestfin.view.administration;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.ProfileDTO;
import br.com.nsol.gestfin.dto.UserDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.Algorithm;
import br.com.nsol.gestfin.utils.ValidateUtil;
import br.com.nsol.gestfin.view.base.BaseView;

/**
 * Bean referente as funcionalidades do cadstro de usuário
 * @author 
 */
@ManagedBean(name="userRegisterView")
@ViewScoped
public class UserRegisterView extends BaseView{
    private static final Logger LOG = Logger.getLogger(UserRegisterView.class);
    private static final String MESSAGE_MODAL_EDIT_ID = "message-maintain-edit-modal-userid";
    private static final String MESSAGE_MODAL_ADD_ID = "message-maintain-add-modal-userid";
    private static final BigDecimal MAX_OF_LINES = new BigDecimal(15);
    private BigDecimal numberOfPages;
    private int countPage;
    private int numberLines = MAX_OF_LINES.intValue();
    private int rowIndex = 0;
    private boolean showModal;
    private boolean hasError;

    private String selectedName;
    private String selectedMail;
    private ProfileDTO selectedProfile;
    private List<ProfileDTO> listProfiles;
    private List<UserDTO> listSelectedUsers;

    private Integer editId;
    private String editName;
    private String editMail;
    private ProfileDTO editProfile;
    private boolean editActive;
    
	private UserDTO tableEditSelelectedUser;
	
	/**
	 * construtor padrão
	 */
	public UserRegisterView() {
		
	}
	
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		try {
			listProfiles = getFacade().listProfiles();
		} catch (TechnicalException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		selectedProfile = null;
		
		listSelectedUsers = new ArrayList<UserDTO>();
		
		LOG.debug("Finalizada action: init");
	}
	
	/**
	 * Procura usuaruios registrados
	 */
	public void actionSearchUsers() {
		LOG.debug("Iniciando action: actionSearchUsers");
		numberLines = MAX_OF_LINES.intValue();
		
		UserDTO registerDTO = new UserDTO();
		if (!ValidateUtil.isEmptyString(getSelectedName())) {
			registerDTO.setUserName(getSelectedName());
		} else {
			registerDTO.setUserName(null);
		}
		if (!ValidateUtil.isEmptyString(getSelectedMail())) {
			registerDTO.setUserMail(getSelectedMail());
		} else {
			registerDTO.setUserMail(null);
		}
		if (getSelectedProfile() != null) {
			registerDTO.setProfileId(getSelectedProfile().getProfileId());
		}
		
		try {
			
			listSelectedUsers = super.getFacade().listUsers(registerDTO);
			if (listSelectedUsers == null || listSelectedUsers.size() == 0) {
				addWarnMessage(null, getMessageBundle("message.user.not.found"));
			} else {
				if (listSelectedUsers.size() <= numberLines) {
					numberLines = listSelectedUsers.size();
				} else {
					numberLines = MAX_OF_LINES.intValue();
				}
				numberOfPages = new BigDecimal((double) getListSelectedUsers().size() / MAX_OF_LINES.intValue())
						.setScale(2, RoundingMode.HALF_UP);
				countPage = 0;
			}
			
		} catch (TechnicalException e) {
			LOG.error("Error actionSearchUsers (TE)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		}
		LOG.debug("Finalizada action: actionSearchUsers");
	}
	
	/**
	 * Incrementa a paginação
	 */
	public void addMoreLines(){
		if (getListSelectedUsers() != null) {
			if (getListSelectedUsers().size() <= MAX_OF_LINES.intValue()) {
				numberLines = getListSelectedUsers().size();
			} else {
				if (countPage <= numberOfPages.intValue()) {
					int offset = getListSelectedUsers().size() - numberLines;
					if (offset <= MAX_OF_LINES.intValue()) {
						numberLines = getListSelectedUsers().size();
					} else {
						numberLines += MAX_OF_LINES.intValue();
					}
					countPage ++;
				} else if ((numberOfPages.intValue()-countPage) != 0) {
					numberLines = getListSelectedUsers().size();
				}
			}
			
		}
	}
		
	/**
	 * Carrega todos os dados do modal de edição
	 * @param value
	 */
	public void loadEditForm(UserDTO value){
		LOG.debug("Iniciando action loadUserData: " + value);

		setTableEditSelelectedUser(value);
		
		if (value != null) {
			setEditId(value.getUserId());
			setEditName(value.getUserName());
			setEditMail(value.getUserMail());
			if (value.getProfileId() != null) {
				setEditProfile(listProfiles.stream()
		                .filter(p -> p.getProfileId().equals(value.getProfileId()))
		                .findFirst()
		                .get());
			} else {
				setEditProfile(null);
			}
			setEditActive(STATUS_ACTIVE.equals(value.getIsActive()));
		}
		
		showModal = true;
		
		LOG.debug("Finalizando action loadUserData");
	}

	/**
	 * Salva os dados alterados
	 */
	public void actionEditSave() {
		LOG.debug("Iniciando action: actionEditSave");
		hasError = false;
		
		try {
			validate();
		} catch (TechnicalException e) {
			addWarnMessage(MESSAGE_MODAL_EDIT_ID, getMessageBundle(e.getMessage()));
			hasError = true;
			return;
		}
		
		UserDTO user = new UserDTO();
		user.setUserId(getEditId());
		user.setUserName(getEditName());
		user.setUserMail(getEditMail());
		user.setProfileId(getEditProfile().getProfileId());
		user.setProfileName(getEditProfile().getProfileName());
		user.setIsActive(getEditActive() ? STATUS_ACTIVE : STATUS_INACTIVE);
		user.setLastUpdateDate(new Date());
		user.setLastUpdatedByUser(super.getLoggedUser().getUserMail());
		
		try {
			
			super.getFacade().updateUser(user);
			
			//Atualiza dados da lista
			getTableEditSelelectedUser().setUserName(user.getUserName());
			getTableEditSelelectedUser().setUserMail(user.getUserMail());
			getTableEditSelelectedUser().setProfileId(user.getProfileId());
			getTableEditSelelectedUser().setProfileName(user.getProfileName());
			getTableEditSelelectedUser().setIsActive(user.getIsActive());
			getTableEditSelelectedUser().setLastUpdateDate(user.getLastUpdateDate());
			getTableEditSelelectedUser().setLastUpdatedByUser(user.getLastUpdatedByUser());
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));
			
		} catch (TechnicalException e) {
			LOG.error("Error actionEditSave (TE)", e);
			addErrorMessage(MESSAGE_MODAL_EDIT_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionEditSave");
	}

	/**
	 * Ajusta campos para adicionar um novo usuário
	 */
	public void actionBeforeNewUserListener() {
		LOG.debug("Iniciando action: actionBeforeNewUserListener");

		setEditName(null);
		setEditMail(null);
		setEditProfile(null);
		setEditActive(true);

		LOG.debug("Finalizada action: actionBeforeNewUserListener");
	}
	
	/**
	 * Salva os dados alterados
	 */
	public void actionNewUser() {
		LOG.debug("Iniciando action: actionNewUser");
		hasError = false;

		try {
			validate();
		} catch (TechnicalException e) {
			addWarnMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(e.getMessage()));
			hasError = true;
			return;
		}
		
		try {

			UserDTO user = new UserDTO();
			user.setUserId(null);
			user.setUserName(getEditName());
			user.setUserMail(getEditMail());
			user.setProfileId(getEditProfile().getProfileId());
			user.setProfileName(getEditProfile().getProfileName());
			user.setIsActive(STATUS_ACTIVE);
			user.setLastUpdateDate(new Date());
			user.setLastUpdatedByUser(super.getLoggedUser().getUserMail());
			
			super.getFacade().insertUser(user);
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));
			
		} catch (TechnicalException e) {
			LOG.error("Error actionNewUser (TE)", e);
			addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (BusinessException e) {
			LOG.error("Error actionNewUser (BE)", e);
			addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(e.getType().getBundleKey()));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionNewUser");
	}
	
	
	/**
	 * @return Lista de usuário pesquisados
	 */
	public List<UserDTO> getListSelectedUsers() {
		
		return listSelectedUsers;
	}

	/**
	 * @return the tableEditSelelectedUser
	 */
	public UserDTO getTableEditSelelectedUser() {
		return tableEditSelelectedUser;
	}

	/**
	 * @param tableEditSelelectedUser the tableEditSelelectedUser to set
	 */
	public void setTableEditSelelectedUser(UserDTO tableEditSelelectedUser) {
		this.tableEditSelelectedUser = tableEditSelelectedUser;
	}
	
	/**
	 * Nome pesquisado
	 * @param name
	 */
	public void setSelectedName(String name){
		this.selectedName = name;
	}

	/**
	 * @return the selectedName
	 */
	public String getSelectedName() {
		return selectedName;
	}

	/**
	 * E-mail pesquisado
	 * @param mail
	 */
	public void setSelectedMail(String mail){
		this.selectedMail = mail;
	}

	/**
	 * @return the selectedMail
	 */
	public String getSelectedMail() {
		return selectedMail;
	}

	/**
	 * Perfil pesquisado
	 * @param profile
	 */
	public void setSelectedProfile(ProfileDTO profile){
		this.selectedProfile = profile;
	}

	/**
	 * @return the selectedProfile
	 */
	public ProfileDTO getSelectedProfile() {
		return selectedProfile;
	}
	
	/**
	 * @return Lista de perfis cadastrados
	 */
	public List<ProfileDTO> getListProfiles() {
		return listProfiles;
	}

	/**
	 * @return the editId
	 */
	public Integer getEditId() {
		return editId;
	}

	/**
	 * @param editId the editId to set
	 */
	public void setEditId(Integer editId) {
		this.editId = editId;
	}

	/**
	 * @return the editName
	 */
	public String getEditName() {
		return editName;
	}

	/**
	 * @param editName the editName to set
	 */
	public void setEditName(String editName) {
		this.editName = editName;
	}


	/**
	 * @return the editMail
	 */
	public String getEditMail() {
		return editMail;
	}

	/**
	 * @param editMail the editMail to set
	 */
	public void setEditMail(String editMail) {
		this.editMail = editMail;
	}

	/**
	 * Perfil editado
	 * @param profile
	 */
	public void setEditProfile(ProfileDTO profile){
		this.editProfile = profile;
	}

	/**
	 * @return the editProfile
	 */
	public ProfileDTO getEditProfile() {
		return editProfile;
	}

	/**
	 * @return the editActive
	 */
	public boolean getEditActive() {
		return editActive;
	}

	/**
	 * @param editActive the editActive to set
	 */
	public void setEditActive(boolean editActive) {
		this.editActive = editActive;
	}
	
	public int getNumberLines() {
		return numberLines;
	}
	
	/**
	 * @return the rowIndex
	 */
	public int getRowIndex() {
		return rowIndex;
	}

	/**
	 * @param rowIndex the rowIndex to set
	 */
	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public boolean isShowModal() {
		return showModal;
	}
	
	public void closeModal(){
		showModal = false;
	}


	/**
	 * Verifica se existe erro a ser mostrado no modal
	 * @return true se existe mensagem
	 */
	public boolean hasMessageModalError() {
		return hasMessageError(MESSAGE_MODAL_EDIT_ID);
	}

	/**
	 * @return the hasError
	 */
	public boolean isHasError() {
		return hasError;
	}

	/**
	 * 
	 * @param error
	 */
	public void setHasError(boolean error) {
		hasError = error;
	}

	/**
	 * @return the numberLines
	 */
	public int getCurrentPage() {
		
		return numberLines;
	}
	
	/**
	 * Monta mensagem de visualização de pagina
	 * @return
	 */
	public String getYouAreSeeing() {
		return getMessageValues("user_maintain.label.you_are_seeing", getCurrentPage(), 
				getListSelectedUsers() != null ? getListSelectedUsers().size() : 0);
		
	}

	/**
	 * Realiza a consistência dos dados informados
	 * @throws TechnicalException
	 */
	private void validate() throws TechnicalException {
		ValidateUser validate = new ValidateUser();
		validate.checkName();
		validate.checkEmail();
		//TODO: Validar se e-mail já utilizado
		validate.checkUserProfile();
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateUser {

		/**
		 * Validação do nome
		 * @return true/false
		 */
		public void checkName() throws TechnicalException {
			if (getEditName() == null || getEditName().length() == 0) {
				throw new TechnicalException("message.text.required.name");
			}
		}

		/**
		 * Validação do email
		 * @return true/false
		 */
		public void checkEmail() throws TechnicalException {
			if (getEditMail() == null || !Algorithm.emailValidatePattern(getEditMail())) {
				throw new TechnicalException("message.invalid.email.error");
			}
		}

		/**
		 * Validação do perfil do usuário
		 * @return true/false
		 */
		public void checkUserProfile() throws TechnicalException {
			if (getEditProfile() == null) {
				throw new TechnicalException("message.select.required.profile");
			}
		}
		
	}
}
