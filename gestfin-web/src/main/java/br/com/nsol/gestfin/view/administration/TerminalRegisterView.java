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

import br.com.nsol.gestfin.dto.EstablishmentDTO;
import br.com.nsol.gestfin.dto.OperatorDTO;
import br.com.nsol.gestfin.dto.TerminalDTO;
import br.com.nsol.gestfin.exceptions.BaseException;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.ValidateUtil;
import br.com.nsol.gestfin.view.base.BaseView;

/**
 * Bean referente as funcionalidades do cadstro de terminais
 * @author 
 */
@ManagedBean(name="terminalRegisterView")
@ViewScoped
public class TerminalRegisterView extends BaseView{
    private static final Logger LOG = Logger.getLogger(TerminalRegisterView.class);
    private static final String MESSAGE_MODAL_EDIT_ID = "message-maintain-edit-modal-terminalid";
    private static final String MESSAGE_MODAL_ADD_ID = "message-maintain-add-modal-terminalid";
    private static final BigDecimal MAX_OF_LINES = new BigDecimal(15);
    private BigDecimal numberOfPages;
    private int countPage;
    private int numberLines = MAX_OF_LINES.intValue();
    private int rowIndex = 0;
    private boolean showModal;
    private boolean hasError;

    private String selectedTerminalNumber;
    private String selectedDescription;
    private OperatorDTO selectedOperator;
    private EstablishmentDTO selectedEstablishment;
    
    private List<OperatorDTO> listOperators;
    private List<EstablishmentDTO> listEstablishments;
    
    private List<TerminalDTO> listSelectedTerminals;

    private Integer editId;
    private String editTerminalNumber;
    private String editDescription;
    private OperatorDTO editOperator;
    private EstablishmentDTO editEstablishment;
    private boolean editActive;
    
	private TerminalDTO tableEditSelelectedTerminal;
	
	/**
	 * construtor padrão
	 */
	public TerminalRegisterView() {
		
	}
	
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		try {
			listOperators = getFacade().listOperators();
			listEstablishments = getFacade().listEstablishments();
		} catch (BaseException e) {
			LOG.error(e);
		}
		selectedOperator = null;
		selectedEstablishment = null;
		
		listSelectedTerminals = new ArrayList<TerminalDTO>();
		
		LOG.debug("Finalizada action: init");
	}
	
	/**
	 * Procura terminais registrados
	 */
	public void actionSearchTerminals() {
		LOG.debug("Iniciando action: actionSearchTerminals");
		numberLines = MAX_OF_LINES.intValue();
		
		TerminalDTO filterDTO = new TerminalDTO();
		if (!ValidateUtil.isEmptyString(getSelectedTerminalNumber())) {
			filterDTO.setTerminalNumber(getSelectedTerminalNumber());
		} else {
			filterDTO.setTerminalNumber(null);
		}
		if (!ValidateUtil.isEmptyString(getSelectedDescription())) {
			filterDTO.setDescription(getSelectedDescription());
		} else {
			filterDTO.setDescription(null);
		}
		if (getSelectedOperator() != null) {
			filterDTO.setOperator(getSelectedOperator());
		}
		if (getSelectedEstablishment() != null) {
			filterDTO.setEstablishment(getSelectedEstablishment());
		}
		
		try {
			listSelectedTerminals = super.getFacade().listTerminals(filterDTO);
			if (listSelectedTerminals == null || listSelectedTerminals.size() == 0) {
				addWarnMessage(null, getMessageBundle("message.terminal.not.found"));
			} else {
				if (listSelectedTerminals.size() <= numberLines) {
					numberLines = listSelectedTerminals.size();
				} else {
					numberLines = MAX_OF_LINES.intValue();
				}
				numberOfPages = new BigDecimal((double) getlistSelectedTerminals().size() / MAX_OF_LINES.intValue())
						.setScale(2, RoundingMode.HALF_UP);
				countPage = 0;
			}
			
		} catch (BaseException e) {
			LOG.error("Error actionSearchTerminals (TE)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		}
		LOG.debug("Finalizada action: actionSearchTerminals");
	}
	
	/**
	 * Incrementa a paginação
	 */
	public void addMoreLines(){
		if (getlistSelectedTerminals() != null) {
			if (getlistSelectedTerminals().size() <= MAX_OF_LINES.intValue()) {
				numberLines = getlistSelectedTerminals().size();
			} else {
				if (countPage <= numberOfPages.intValue()) {
					int offset = getlistSelectedTerminals().size() - numberLines;
					if (offset <= MAX_OF_LINES.intValue()) {
						numberLines = getlistSelectedTerminals().size();
					} else {
						numberLines += MAX_OF_LINES.intValue();
					}
					countPage ++;
				} else if ((numberOfPages.intValue()-countPage) != 0) {
					numberLines = getlistSelectedTerminals().size();
				}
			}
			
		}
	}
		
	/**
	 * Carrega todos os dados do modal de edição
	 * @param value
	 */
	public void loadEditForm(TerminalDTO value){
		LOG.debug("Iniciando action loadTerminalData: " + value);

		setTableEditSelelectedTerminal(value);
		
		if (value != null) {
			setEditId(value.getTerminalId());
			setEditTerminalNumber(value.getTerminalNumber());
			setEditDescription(value.getDescription());
			if (value.getOperator() != null) {
				setEditOperator(listOperators.stream()
		                .filter(o -> o.getId().equals(value.getOperator().getId()))
		                .findFirst()
		                .get());
			} else {
				setEditOperator(null);
			}
			if (value.getEstablishment() != null) {
				setEditEstablishment(listEstablishments.stream()
		                .filter(o -> o.getId().equals(value.getEstablishment().getId()))
		                .findFirst()
		                .get());
			} else {
				setEditEstablishment(null);
			}
			setEditActive(STATUS_ACTIVE.equals(value.getIsActive()));
		}
		
		showModal = true;
		
		LOG.debug("Finalizando action loadTerminalData");
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
		
		TerminalDTO terminal = new TerminalDTO();
		terminal.setTerminalId(getEditId());
		terminal.setTerminalNumber(getEditTerminalNumber());
		terminal.setDescription(getEditDescription());
		terminal.setOperator(getEditOperator());
		terminal.setEstablishment(getEditEstablishment());
		terminal.setIsActive(getEditActive() ? STATUS_ACTIVE : STATUS_INACTIVE);
		terminal.setLastUpdateDate(new Date());
		terminal.setLastUpdatedByUser(super.getLoggedUser().getUserMail());
		
		try {
			
			super.getFacade().updateTerminal(terminal);
			
			//Atualiza dados da lista
			getTableEditSelelectedTerminal().setTerminalNumber(terminal.getTerminalNumber());
			getTableEditSelelectedTerminal().setDescription(terminal.getDescription());
			getTableEditSelelectedTerminal().setOperator(terminal.getOperator());
			getTableEditSelelectedTerminal().setEstablishment(terminal.getEstablishment());
			getTableEditSelelectedTerminal().setIsActive(terminal.getIsActive());
			getTableEditSelelectedTerminal().setLastUpdateDate(terminal.getLastUpdateDate());
			getTableEditSelelectedTerminal().setLastUpdatedByUser(terminal.getLastUpdatedByUser());
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));

			actionSearchTerminals();
			
		} catch (BaseException e) {
			if (e instanceof TechnicalException) {
				LOG.error("Error actionEditSave (TE)", e);
				addErrorMessage(MESSAGE_MODAL_EDIT_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			} else {
				LOG.error("Error actionEditSave (BE)", e);
				addErrorMessage(MESSAGE_MODAL_EDIT_ID, getMessageBundle(((BusinessException)e).getType().getBundleKey()));
				setHasError(true);
			}
		}

		LOG.debug("Finalizada action: actionEditSave");
	}

	/**
	 * Ajusta campos para adicionar um novo usuário
	 */
	public void actionBeforeNewTerminalListener() {
		LOG.debug("Iniciando action: actionBeforeNewTerminalListener");

		setEditTerminalNumber(null);
		setEditDescription(null);
		setEditOperator(new OperatorDTO());
		setEditEstablishment(new EstablishmentDTO());
		setEditActive(true);

		LOG.debug("Finalizada action: actionBeforeNewTerminalListener");
	}
	
	/**
	 * Salva os dados alterados
	 */
	public void actionNewTerminal() {
		LOG.debug("Iniciando action: actionNewTerminal");
		hasError = false;

		try {
			validate();
		} catch (TechnicalException e) {
			addWarnMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(e.getMessage()));
			hasError = true;
			return;
		}
		
		try {
			TerminalDTO terminal = new TerminalDTO();
			terminal.setTerminalId(null);
			terminal.setTerminalNumber(getEditTerminalNumber());
			terminal.setDescription(getEditDescription());
			terminal.setEstablishment(getEditEstablishment());
			terminal.setOperator(getEditOperator());
			terminal.setIsActive(STATUS_ACTIVE);
			terminal.setLastUpdateDate(new Date());
			terminal.setLastUpdatedByUser(super.getLoggedUser().getUserMail());
			
			super.getFacade().insertTerminal(terminal);
			
			actionSearchTerminals();
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));
		} catch (BaseException e) {
			if (e instanceof TechnicalException) {
				LOG.error("Error actionNewTerminal (TE)", e);
				addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			} else {
				LOG.error("Error actionNewTerminal (BE)", e);
				addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(((BusinessException)e).getType().getBundleKey()));
				setHasError(true);
			}
		}

		LOG.debug("Finalizada action: actionNewTerminal");
	}
	
	/**
	 * Exclui um terminal
	 */
	public void actionDeleteTerminal() {
		LOG.debug("Iniciando action: actionDeleteTerminal");
		hasError = false;

		try {
			TerminalDTO terminal = new TerminalDTO();
			terminal.setTerminalId(getEditId());
			
			super.getFacade().deleteTerminal(terminal);
			
			actionSearchTerminals();
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));
		} catch (BaseException e) {
			if (e instanceof TechnicalException) {
				LOG.error("Error actionDeleteTerminal (TE)", e);
				addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			} else {
				LOG.error("Error actionDeleteTerminal (BE)", e);
				addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(((BusinessException)e).getType().getBundleKey()));
				setHasError(true);
			}
		}

		LOG.debug("Finalizada action: actionDeleteTerminal");
	}
	
	/**
	 * @return Lista de usuário pesquisados
	 */
	public List<TerminalDTO> getlistSelectedTerminals() {
		
		return listSelectedTerminals;
	}

	/**
	 * @return the tableEditSelelectedTerminal
	 */
	public TerminalDTO getTableEditSelelectedTerminal() {
		return tableEditSelelectedTerminal;
	}

	/**
	 * @param tableEditSelelectedTerminal the tableEditSelelectedTerminal to set
	 */
	public void setTableEditSelelectedTerminal(TerminalDTO tableEditSelelectedTerminal) {
		this.tableEditSelelectedTerminal = tableEditSelelectedTerminal;
	}
	
	/**
	 * Descricao pesquisada
	 * @param name
	 */
	public void setSelectedDescription(String description){
		this.selectedDescription = description;
	}

	/**
	 * @return the selectedDescription
	 */
	public String getSelectedDescription() {
		return selectedDescription;
	}

	/**
	 * @return the selectedTerminalNumber
	 */
	public String getSelectedTerminalNumber() {
		return selectedTerminalNumber;
	}

	/**
	 * @param selectedTerminalNumber the selectedTerminalNumber to set
	 */
	public void setSelectedTerminalNumber(String selectedTerminalNumber) {
		this.selectedTerminalNumber = selectedTerminalNumber;
	}

	/**
	 * Operadora pesquisado
	 * @param operator
	 */
	public void setSelectedOperator(OperatorDTO operator){
		this.selectedOperator = operator;
	}

	/**
	 * @return the selectedOperator
	 */
	public OperatorDTO getSelectedOperator() {
		return selectedOperator;
	}
	
	/**
	 * @return the selectedEstablishment
	 */
	public EstablishmentDTO getSelectedEstablishment() {
		return selectedEstablishment;
	}

	/**
	 * @param selectedEstablishment the selectedEstablishment to set
	 */
	public void setSelectedEstablishment(EstablishmentDTO selectedEstablishment) {
		this.selectedEstablishment = selectedEstablishment;
	}

	/**
	 * @return Lista de perfis cadastrados
	 */
	public List<OperatorDTO> getListOperators() {
		return listOperators;
	}

	/**
	 * @return the listEstablishments
	 */
	public List<EstablishmentDTO> getListEstablishments() {
		return listEstablishments;
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
	 * Operadora editado
	 * @param editOperator
	 */
	public void setEditOperator(OperatorDTO editOperator){
		this.editOperator = editOperator;
	}

	/**
	 * @return the editOperator
	 */
	public OperatorDTO getEditOperator() {
		return editOperator;
	}

	/**
	 * @return the editTerminalNumber
	 */
	public String getEditTerminalNumber() {
		return editTerminalNumber;
	}

	/**
	 * @param editTerminalNumber the editTerminalNumber to set
	 */
	public void setEditTerminalNumber(String editTerminalNumber) {
		this.editTerminalNumber = editTerminalNumber;
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
	 * @return the editEstablishment
	 */
	public EstablishmentDTO getEditEstablishment() {
		return editEstablishment;
	}

	/**
	 * @param editEstablishment the editEstablishment to set
	 */
	public void setEditEstablishment(EstablishmentDTO editEstablishment) {
		this.editEstablishment = editEstablishment;
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
		return getMessageValues("terminal_maintain.label.you_are_seeing", getCurrentPage(), 
				getlistSelectedTerminals() != null ? getlistSelectedTerminals().size() : 0);
		
	}

	/**
	 * Realiza a consistência dos dados informados
	 * @throws TechnicalException
	 */
	private void validate() throws TechnicalException {
		ValidateTerminal validate = new ValidateTerminal();
		validate.checkTerminalNumber();
		validate.checkDescription();
		validate.checkOperator();
		validate.checkEstablishment();
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateTerminal {
		/**
		 * Validação do numero de terminal
		 * @return true/false
		 */
		public void checkTerminalNumber() throws TechnicalException {
			if (getEditTerminalNumber() == null || getEditTerminalNumber().length() == 0) {
				throw new TechnicalException("message.text.required.terminal.number");
			}
		}

		/**
		 * Validação da descrição
		 * @return true/false
		 */
		public void checkDescription() throws TechnicalException {
			if (getEditDescription() == null || getEditDescription().length() == 0) {
				throw new TechnicalException("message.text.required.description");
			}
		}

		/**
		 * Validação da operadora
		 * @return true/false
		 */
		public void checkOperator() throws TechnicalException {
			if (getEditOperator() == null) {
				throw new TechnicalException("message.select.required.operator");
			}
		}

		/**
		 * Validação do estabelecimento
		 * @return true/false
		 */
		public void checkEstablishment() throws TechnicalException {
			if (getEditEstablishment() == null) {
				throw new TechnicalException("message.select.required.establishment");
			}
		}
	}
}
