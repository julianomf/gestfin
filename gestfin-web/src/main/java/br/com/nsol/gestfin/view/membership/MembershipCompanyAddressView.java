package br.com.nsol.gestfin.view.membership;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.log4j.Logger;

import br.com.nsol.gestfin.dto.CompanyAddressDTO;
import br.com.nsol.gestfin.dto.PostalCodeDTO;
import br.com.nsol.gestfin.dto.StateDTO;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.ValidateUtil;
import br.com.nsol.gestfin.view.base.MembershipBaseView;

/**
 * Classe responsável pelo passo "Empresa" do wizard de adesão
 * @author 
 *
 */
@ViewScoped
@ManagedBean(name="msCompanyAddressView")
public class MembershipCompanyAddressView extends MembershipBaseView {
    private static final Logger LOG = Logger.getLogger(MembershipCompanyAddressView.class);
    private static final String MESSAGE_MODAL_EDIT_ID = "message-edit-modal-address";
    private static final String MESSAGE_MODAL_ADD_ID = "message-add-modal-address";
    private static final String MESSAGE_ID = null;
	private boolean hasError;
    private int rowIndex = 0;
    private boolean showModal;
	
	private List<CompanyAddressDTO> listAddresses;
	private List<StateDTO> listStates;

	private CompanyAddressDTO tableEditSelelectedAddress;
	private String editDescription;
	private Integer editPostalCode;
	private String editStreetName;
	private String editNumber;
	private String editDistrict;
	private String editComplement;
	private String editCity;
	private String editState;

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

	/**
	 * @return the listAddresses
	 */
	public List<CompanyAddressDTO> getListAddresses() {
		return listAddresses;
	}

	/**
	 * @param listAddresses the listAddresses to set
	 */
	public void setListAddresses(List<CompanyAddressDTO> listAddresses) {
		this.listAddresses = listAddresses;
	}

	/**
	 * @return the listStates
	 */
	public List<StateDTO> getListStates() {
		return listStates;
	}

	/**
	 * @param listStates the listStates to set
	 */
	public void setListStates(List<StateDTO> listStates) {
		this.listStates = listStates;
	}

	/**
	 * @return the tableEditSelelectedAddress
	 */
	public CompanyAddressDTO getTableEditSelelectedAddress() {
		return tableEditSelelectedAddress;
	}

	/**
	 * @param tableEditSelelectedAddress the tableEditSelelectedAddress to set
	 */
	public void setTableEditSelelectedAddress(CompanyAddressDTO tableEditSelelectedAddress) {
		this.tableEditSelelectedAddress = tableEditSelelectedAddress;
	}

	/**
	 * @return the editDescription
	 */
	public String getEditDescription() {
		return editDescription;
	}

	/**
	 * @param editDescription the editDescription to set
	 */
	public void setEditDescription(String editDescription) {
		this.editDescription = editDescription;
	}

	/**
	 * @return the editPostalCode
	 */
	public Integer getEditPostalCode() {
		return editPostalCode;
	}

	/**
	 * @param editPostalCode the editPostalCode to set
	 */
	public void setEditPostalCode(Integer editPostalCode) {
		this.editPostalCode = editPostalCode;
	}

	/**
	 * @return the editStreetName
	 */
	public String getEditStreetName() {
		return editStreetName;
	}

	/**
	 * @param editStreetName the editStreetName to set
	 */
	public void setEditStreetName(String editStreetName) {
		this.editStreetName = editStreetName;
	}

	/**
	 * @return the editNumber
	 */
	public String getEditNumber() {
		return editNumber;
	}

	/**
	 * @param editNumber the editNumber to set
	 */
	public void setEditNumber(String editNumber) {
		this.editNumber = editNumber;
	}

	/**
	 * @return the editDistrict
	 */
	public String getEditDistrict() {
		return editDistrict;
	}

	/**
	 * @param editDistrict the editDistrict to set
	 */
	public void setEditDistrict(String editDistrict) {
		this.editDistrict = editDistrict;
	}

	/**
	 * @return the editComplement
	 */
	public String getEditComplement() {
		return editComplement;
	}

	/**
	 * @param editComplement the editComplement to set
	 */
	public void setEditComplement(String editComplement) {
		this.editComplement = editComplement;
	}

	/**
	 * @return the editCity
	 */
	public String getEditCity() {
		return editCity;
	}

	/**
	 * @param editCity the editCity to set
	 */
	public void setEditCity(String editCity) {
		this.editCity = editCity;
	}

	/**
	 * @return the editState
	 */
	public String getEditState() {
		return editState;
	}

	/**
	 * @param editState the editState to set
	 */
	public void setEditState(String editState) {
		this.editState = editState;
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
				
				// Carrega lista de estados
				listStates = super.getFacade().listStates();

				// Busca os dados da empresa pelo e-mail do responsável do cadastro
				if (super.getMembershipInfo().getCompany().getAddresses() == null) {
        			List<CompanyAddressDTO> list = super.getFacade().listCompanyAddress(super.getMembershipInfo().getCompany().getId());
        			if (list == null) {
        				list = new ArrayList<CompanyAddressDTO>();
        			}
        			super.getMembershipInfo().getCompany().setAddresses(list);
				}
    			setListAddresses(super.getMembershipInfo().getCompany().getAddresses());
				
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
		return getMessageValues("membership.company_address.you_are_seeing",  
				getListAddresses() != null ? getListAddresses().size() : 0);
	}

	/**
	 * Carrega todos os dados do modal de edição
	 * @param value
	 */
	public void actionFindPostalCode() {
		LOG.debug("Iniciando action actionFindPostalCode");

		try {
			
			PostalCodeDTO postalCode = null;
			if (!ValidateUtil.isNull(getEditPostalCode())) {
	    		postalCode = super.getFacade().findPostalCode(getEditPostalCode());
			}
			LOG.debug("postalCode: " + postalCode);
			
			if (ValidateUtil.isNull(postalCode)) {
	    		setEditStreetName(null);
	    		setEditDistrict(null);
	    		setEditCity(null);
	    		setEditState(null);
			} else {
	    		setEditStreetName(postalCode.getStreetName());
	    		setEditDistrict(postalCode.getDistrict());
	    		setEditCity(postalCode.getCity());
	    		setEditState(postalCode.getState());
			}
		
		} catch (TechnicalException e) {
    		LOG.error("Error actionFindPostalCode (TE)", e);
    		addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
    		setHasError(true);
    	} catch (Exception e) {
    		LOG.error("Error actionFindPostalCode (TE)", e);
    		addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
    		setHasError(true);
    	}
		
		LOG.debug("Finalizando action actionFindPostalCode");
	}

	/**
	 * Ajusta campos para adicionar um novo usuário
	 */
	public void actionBeforeNewAddress() {
		LOG.debug("Iniciando action: actionBeforeNewAddress");

		setTableEditSelelectedAddress(null);
		setEditDescription(null);
		setEditPostalCode(null);
		setEditStreetName(null);
		setEditNumber(null);
		setEditDistrict(null);
		setEditComplement(null);
		setEditCity(null);
		setEditState(null);

		LOG.debug("Finalizada action: actionBeforeNewAddress");
	}

	/**
	 * Salva os dados alterados
	 */
	public void actionSaveNewAddress() {
		LOG.debug("Iniciando action: actionSaveNewAddress");
		setHasError(false);

		try {

			validate();

			CompanyAddressDTO address = new CompanyAddressDTO();
			address.setDescription(getEditDescription());
			address.setPostalCode(getEditPostalCode());
			address.setStreetName(getEditStreetName());
			address.setNumber(getEditNumber());
			address.setDistrict(getEditDistrict());
			address.setComplement(getEditComplement());
			address.setCity(getEditCity());
			address.setState(getEditState());
			address.setCompanyId(super.getMembershipInfo().getCompany().getId());
			address.setId(super.getFacade().saveCompanyAddress(address));
			
			listAddresses.add(address);

			addInfoMessage(MESSAGE_ID, getMessageBundle("message.operation.success"));

		} catch (BusinessException e) {
			LOG.error("Error actionSaveNewAddress (BE)", e);
			addWarnMessage(MESSAGE_MODAL_ADD_ID, super.getMessageBundle(e.getMessage()));
			setHasError(true);
		} catch (TechnicalException e) {
			LOG.error("Error actionSaveNewAddress (TE)", e);
			addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionSaveNewAddress (E)", e);
			addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}
			
		LOG.debug("Finalizada action: actionSaveNewAddress");
	}

	/**
	 * Carrega todos os dados do modal de edição
	 * @param value
	 */
	public void actionBeforeEditAddress(CompanyAddressDTO value){
		LOG.debug("Iniciando action actionBeforeEditAddress: " + value);

		setTableEditSelelectedAddress(value);
		if (value != null) {
			setEditDescription(value.getDescription());
			setEditPostalCode(value.getPostalCode());
			setEditStreetName(value.getStreetName());
			setEditNumber(value.getNumber());
			setEditDistrict(value.getDistrict());
			setEditComplement(value.getComplement());
			setEditCity(value.getCity());
			setEditState(value.getState());
		}
		
		showModal = true;
		
		LOG.debug("Finalizando action actionBeforeEditAddress");
	}

	/**
	 * Salva os dados alterados
	 */
	public void actionSaveEditAddress() {
		LOG.debug("Iniciando action: actionSaveEditAddress");
		setHasError(false);

		try {

			validate();

			//Atualiza dados da lista
			getTableEditSelelectedAddress().setDescription(getEditDescription());
			getTableEditSelelectedAddress().setPostalCode(getEditPostalCode());
			getTableEditSelelectedAddress().setStreetName(getEditStreetName());
			getTableEditSelelectedAddress().setNumber(getEditNumber());
			getTableEditSelelectedAddress().setDistrict(getEditDistrict());
			getTableEditSelelectedAddress().setComplement(getEditComplement());
			getTableEditSelelectedAddress().setCity(getEditCity());
			getTableEditSelelectedAddress().setState(getEditState());
			
			super.getFacade().saveCompanyAddress(getTableEditSelelectedAddress());
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));
			
		} catch (BusinessException e) {
			LOG.error("Error actionSaveEditAddress (BE)", e);
			addWarnMessage(MESSAGE_MODAL_EDIT_ID, super.getMessageBundle(e.getMessage()));
			setHasError(true);
		} catch (TechnicalException e) {
			LOG.error("Error actionSaveEditAddress (TE)", e);
			addErrorMessage(MESSAGE_MODAL_EDIT_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionSaveEditAddress (E)", e);
			addErrorMessage(MESSAGE_MODAL_EDIT_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionSaveEditAddress");
	}

	/**
	 * Carrega todos os dados do modal de edição
	 * @param value
	 */
	public void actionBeforeDeleteAddress(CompanyAddressDTO value){
		LOG.debug("Iniciando action actionBeforeDeleteAddress: " + value);

		setTableEditSelelectedAddress(value);
		
		showModal = true;
		
		LOG.debug("Finalizando action actionBeforeDeleteAddress");
	}

	/**
	 * Salva os dados alterados
	 */
	public void actionDeleteAddress() {
		LOG.debug("Iniciando action: actionDeleteAddress");
		setHasError(false);

		try {

			super.getFacade().deleteCompanyAddress(getTableEditSelelectedAddress().getId());
			listAddresses.remove(getTableEditSelelectedAddress());
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));
			
		} catch (TechnicalException e) {
			LOG.error("Error actionDeleteAddress (TE)", e);
			addErrorMessage(MESSAGE_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		} catch (Exception e) {
			LOG.error("Error actionDeleteAddress (E)", e);
			addErrorMessage(MESSAGE_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
			setHasError(true);
		}

		LOG.debug("Finalizada action: actionDeleteAddress");
	}

	/**
	 * Action executada ao clicar no botão Voltar
	 */
	public void actionBack() {
		LOG.debug("Iniciando action: actionBack");

		try {

			// O sistema redireciona o usuário para página anterior do fluxo
			super.redirect(PAGE_MEMBERSHIP_COMPANY_DATA);
			
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
			super.redirect(PAGE_MEMBERSHIP_COMPANY_CONTACTS);

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
			
		ValidateCompanyAddress validator = new ValidateCompanyAddress();
		validator.checkDescription();
		validator.checkPostalCode();
		validator.checkStreetName();
		validator.checkNumber();
		validator.checkDistrict();
		validator.checkCity();
		validator.checkState();
		
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateCompanyAddress {
		
		/**
		 * Realiza validação da descrição  
		 * @return true/false
		 */
		public void checkDescription() throws BusinessException {
			if (ValidateUtil.isEmptyString(getEditDescription())) {
				throw new BusinessException("membership.company_address.message.validation.description");
			}
			boolean isDuplicated = false;
			for (CompanyAddressDTO address : listAddresses) {
				if (getEditDescription().equalsIgnoreCase(address.getDescription())) {
					if (getTableEditSelelectedAddress() == null) {
						isDuplicated = true;
						break;
					} else {
						if (!getTableEditSelelectedAddress().getId().equals(address.getId())) {
							isDuplicated = true;
							break;
						}
					}
				}
			}
			if (isDuplicated) {
				throw new BusinessException("membership.company_address.message.validation.duplicated");
			}
		}

		/**
		 * Realiza validação do CEP  
		 * @return true/false
		 */
		public void checkPostalCode() throws BusinessException {
			if (ValidateUtil.isNull(getEditPostalCode()) || getEditPostalCode().intValue() <= 0){
				throw new BusinessException("membership.company_address.message.validation.postalcode");
			}
		}

		/**
		 * Realiza validação do logradouro  
		 * @return true/false
		 */
		public void checkStreetName() throws BusinessException {
			if (ValidateUtil.isEmptyString(getEditStreetName())){
				throw new BusinessException("membership.company_address.message.validation.streetname");
			}
		}
		
		/**
		 * Realiza validação do número 
		 * @return true/false
		 */
		public void checkNumber() throws BusinessException {
			if (ValidateUtil.isEmptyString(getEditNumber())){
				throw new BusinessException("membership.company_address.message.validation.number");
			}
		}

		/**
		 * Realiza validação do bairro
		 * @return true/false
		 */
		public void checkDistrict() throws BusinessException {
			if (ValidateUtil.isEmptyString(getEditDistrict())){
				throw new BusinessException("membership.company_address.message.validation.district");
			}
		}

		/**
		 * Realiza validação da cidade
		 * @return true/false
		 */
		public void checkCity() throws BusinessException {
			if (ValidateUtil.isEmptyString(getEditCity())){
				throw new BusinessException("membership.company_address.message.validation.city");
			}
		}

		/**
		 * Realiza validação do estado
		 * @return true/false
		 */
		public void checkState() throws BusinessException {
			if (ValidateUtil.isEmptyString(getEditState())){
				throw new BusinessException("membership.company_address.message.validation.state");
			}
		}

	}	
}
