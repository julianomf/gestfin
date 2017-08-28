package br.com.nsol.gestfin.view.membership;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanyContactDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.Algorithm;
import br.com.nsol.gestfin.utils.ValidateUtil;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Classe responsável pelo passo "Empresa" do wizard de adesão
 * @author 
 *
 */
@ViewScoped
@ManagedBean(name="msCompanyContactView")
public class MembershipCompanyContactView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipCompanyContactView.class);
    private static final String MESSAGE_MODAL_EDIT_ID = "message-maintain-edit-modal-contact";
    private static final String MESSAGE_MODAL_ADD_ID = "message-maintain-add-modal-contact";
    private static final String MESSAGE_ID = null;
	private boolean hasError;
    private int rowIndex = 0;
    private boolean showModal;
	
	private List<CompanyContactDTO> listContacts;

	private CompanyContactDTO tableEditSelelectedContact;
	private String editMail;
	private Long editMobileNumber;
	private String editName;
	private Long editPhoneNumber;

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

	/**
	 * @return the showModal
	 */
	public boolean isShowModal() {
		return showModal;
	}

	/**
	 * @param showModal the showModal to set
	 */
	public void setShowModal(boolean showModal) {
		this.showModal = showModal;
	}

	public void closeModal(){
		showModal = false;
	}
	
	/**
	 * @return the listContacts
	 */
	public List<CompanyContactDTO> getListContacts() {
		return listContacts;
	}

	/**
	 * @param listContacts the listContacts to set
	 */
	public void setListContacts(List<CompanyContactDTO> listContacts) {
		this.listContacts = listContacts;
	}

	/**
	 * @return the tableEditSelelectedContact
	 */
	public CompanyContactDTO getTableEditSelelectedContact() {
		return tableEditSelelectedContact;
	}

	/**
	 * @param tableEditSelelectedContact the tableEditSelelectedContact to set
	 */
	public void setTableEditSelelectedContact(CompanyContactDTO tableEditSelelectedContact) {
		this.tableEditSelelectedContact = tableEditSelelectedContact;
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
	 * @return the editMobileNumber
	 */
	public Long getEditMobileNumber() {
		return editMobileNumber;
	}

	/**
	 * @param editMobileNumber the editMobileNumber to set
	 */
	public void setEditMobileNumber(Long editMobileNumber) {
		this.editMobileNumber = editMobileNumber;
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
	 * @return the editPhoneNumber
	 */
	public Long getEditPhoneNumber() {
		return editPhoneNumber;
	}

	/**
	 * @param editPhoneNumber the editPhoneNumber to set
	 */
	public void setEditPhoneNumber(Long editPhoneNumber) {
		this.editPhoneNumber = editPhoneNumber;
	}

	/**
	 * Metodo de inicialização do bean
	 */
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		// Configura o passo atual da avaliação
		super.setCurrentMembershipStep(MEMBERSHIP_STEP_COMPANY);

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

				// Busca os dados da empresa pelo e-mail do responsável do cadastro
				if (super.getMembershipInfo().getCompany().getContacts() == null) {
        			List<CompanyContactDTO> list = super.getFacade().listCompanyContact(super.getMembershipInfo().getCompany().getId());
        			if (list == null) {
        				list = new ArrayList<CompanyContactDTO>();
        			}
        			super.getMembershipInfo().getCompany().setContacts(list);
				}
    			setListContacts(super.getMembershipInfo().getCompany().getContacts());

			} catch (TechnicalException e) {
				LOG.error("Error init (TE)", e);
				addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			} catch (Exception e) {
				LOG.error("Error init (E)", e);
				addErrorMessage(MESSAGE_ID, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			}
		}
		
		LOG.debug("Finalizada action: init");
	}

	/**
	 * Monta mensagem de visualização de pagina
	 * @return
	 */
	public String getYouAreSeeing() {
		return getMessageValues("membership.company_contact.you_are_seeing",  
				getListContacts() != null ? getListContacts().size() : 0);
		
	}
	
	/**
	 * Ajusta campos para adicionar um novo usuário
	 */
	public void actionBeforeNewContact() {
		LOG.debug("Iniciando action: actionBeforeNewContact");

		setEditMail(null);
		setEditMobileNumber(null);
		setEditName(null);
		setEditPhoneNumber(null);

		LOG.debug("Finalizada action: actionBeforeNewContact");
	}

	/**
	 * Salva os dados alterados
	 */
	public void actionSaveNewContact() {
		LOG.debug("Iniciando action: actionSaveNewContact");
		setHasError(false);
		
		try {

			validate();

			CompanyContactDTO contact = new CompanyContactDTO();
			contact.setName(getEditName());
			contact.setMail(getEditMail());
			contact.setPhoneNumber(getEditPhoneNumber());
			contact.setMobileNumber(getEditMobileNumber());
			contact.setCompanyId(super.getMembershipInfo().getCompany().getId());
			contact.setId(super.getFacade().saveCompanyContact(contact));

			listContacts.add(contact);

			addInfoMessage(null, getMessageBundle("message.operation.success"));

		} catch (BusinessException e) {
			LOG.error("Error actionSaveNewContact (BE)", e);
			addWarnMessage(MESSAGE_MODAL_ADD_ID, super.getMessageBundle(e.getMessage()));
			setHasError(true);
		} catch (TechnicalException e) {
			LOG.error("Error actionSaveNewContact (TE)", e);
			addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionSaveNewContact (E)", e);
			addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionSaveNewContact");
	}

	/**
	 * Carrega todos os dados do modal de edição
	 * @param value
	 */
	public void actionBeforeEditContact(CompanyContactDTO value){
		LOG.debug("Iniciando action actionBeforeEditContact: " + value);

		setTableEditSelelectedContact(value);
		if (value != null) {
			setEditName(value.getName());
			setEditMail(value.getMail());
			setEditPhoneNumber(value.getPhoneNumber());
			setEditMobileNumber(value.getMobileNumber());
		}
		
		showModal = true;
		
		LOG.debug("Finalizando action actionBeforeEditContact");
	}

	/**
	 * Salva os dados alterados
	 */
	public void actionSaveEditContact() {
		LOG.debug("Iniciando action: actionSaveEditContact");
		setHasError(false);
		try {

			validate();

			//Atualiza dados da lista
			getTableEditSelelectedContact().setName(getEditName());
			getTableEditSelelectedContact().setMail(getEditMail());
			getTableEditSelelectedContact().setPhoneNumber(getEditPhoneNumber());
			getTableEditSelelectedContact().setMobileNumber(getEditMobileNumber());

			super.getFacade().saveCompanyContact(getTableEditSelelectedContact());

			addInfoMessage(null, getMessageBundle("message.operation.success"));
			
		} catch (BusinessException e) {
			LOG.error("Error actionSaveEditContact (BE)", e);
			addWarnMessage(MESSAGE_MODAL_EDIT_ID, super.getMessageBundle(e.getMessage()));
			setHasError(true);
		} catch (TechnicalException e) {
			LOG.error("Error actionSaveEditContact (TE)", e);
			addErrorMessage(MESSAGE_MODAL_EDIT_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionSaveEditContact (E)", e);
			addErrorMessage(MESSAGE_MODAL_EDIT_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionSaveEditContact");
	}

	/**
	 * Carrega todos os dados do modal de edição
	 * @param value
	 */
	public void actionBeforeDeleteContact(CompanyContactDTO value){
		LOG.debug("Iniciando action actionBeforeDeleteContact: " + value);

		setTableEditSelelectedContact(value);
		
		showModal = true;
		
		LOG.debug("Finalizando action actionBeforeDeleteContact");
	}

	/**
	 * Salva os dados alterados
	 */
	public void actionDeleteContact() {
		LOG.debug("Iniciando action: actionDeleteContact");
		setHasError(false);

		try {

			super.getFacade().deleteCompanyContact(getTableEditSelelectedContact().getId());
			listContacts.remove(getTableEditSelelectedContact());
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));
			
		} catch (TechnicalException e) {
			LOG.error("Error actionDeleteContact (TE)", e);
			addErrorMessage(MESSAGE_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionDeleteContact (E)", e);
			addErrorMessage(MESSAGE_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionDeleteContact");
	}

	/**
	 * Action executada ao clicar no botão Voltar
	 */
	public void actionBack() {
		LOG.debug("Iniciando action: actionBack");

		try {

			// O sistema redireciona o usuário para página anterior do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_ADDRESS);
			
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

		try {

			// O sistema redireciona o usuário para próxima página do fluxo
			super.redirect(PAGE_MEMBERSHIP_ACCOUNTANCY);

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
			
		ValidateCustomerData validator = new ValidateCustomerData();
		validator.checkName();
		validator.checkMail();
		validator.checkPhone();
		
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateCustomerData {
		
		/**
		 * Realiza validação do nome  
		 * @return true/false
		 */
		public void checkName() throws BusinessException {
			if (ValidateUtil.isEmptyString(getEditName())) {
				throw new BusinessException("membership.company_contact.message.validation.name");
			}
			boolean isDuplicated = false;
			for (CompanyContactDTO contact : listContacts) {
				if (getEditName().equalsIgnoreCase(contact.getName())) {
					if (getTableEditSelelectedContact() == null) {
						isDuplicated = true;
						break;
					} else {
						if (!getTableEditSelelectedContact().getId().equals(contact.getId())) {
							isDuplicated = true;
							break;
						}
					}
				}
			}
			if (isDuplicated) {
				throw new BusinessException("membership.company_contact.message.validation.duplicated");
			}
		}

		/**
		 * Validação do email
		 * @return true/false
		 */
		public void checkMail() throws BusinessException {
			if (getEditMail() == null || !Algorithm.emailValidatePattern(getEditMail())) {
				throw new BusinessException("membership.company_contact.message.validation.mail");
			}
		}

		/**
		 * Validação do telefone
		 * @return true/false
		 */
		public void checkPhone() throws BusinessException {
			if (ValidateUtil.isNull(getEditPhoneNumber()) && ValidateUtil.isNull(getEditMobileNumber())) {
				throw new BusinessException("membership.company_contact.message.validation.phone");
			}
			if ((!ValidateUtil.isNull(getEditPhoneNumber()) && ValidateUtil.isMinLengthString(getEditPhoneNumber().toString(), PHONE_MINLENGHT)) ||
				 (!ValidateUtil.isNull(getEditMobileNumber()) && ValidateUtil.isMinLengthString(getEditMobileNumber().toString(), PHONE_MINLENGHT))){
				throw new BusinessException("membership.company_contact.message.validation.phone_invalid");
			}
		}
	}
}
