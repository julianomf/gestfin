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

import br.com.nsol.gestfin.dto.CardFlagDTO;
import br.com.nsol.gestfin.dto.EstablishmentDTO;
import br.com.nsol.gestfin.dto.OperatorDTO;
import br.com.nsol.gestfin.dto.TaxDTO;
import br.com.nsol.gestfin.dto.TransactionTypeDTO;
import br.com.nsol.gestfin.exceptions.BaseException;
import br.com.nsol.gestfin.exceptions.BusinessException;
import br.com.nsol.gestfin.exceptions.TechnicalException;
import br.com.nsol.gestfin.utils.DateUtil;
import br.com.nsol.gestfin.view.base.BaseView;

/**
 * Bean referente as funcionalidades do cadstro de taxas
 * @author 
 */
@ManagedBean(name="taxRegisterView")
@ViewScoped
public class TaxRegisterView extends BaseView {
    private static final Logger LOG = Logger.getLogger(TaxRegisterView.class);
    private static final String MESSAGE_MODAL_EDIT_ID = "message-maintain-edit-modal-taxid";
    private static final String MESSAGE_MODAL_ADD_ID = "message-maintain-add-modal-taxid";
    private static final BigDecimal MAX_OF_LINES = new BigDecimal(15);
    private BigDecimal numberOfPages;
    private int countPage;
    private int numberLines = MAX_OF_LINES.intValue();
    private int rowIndex = 0;
    private boolean showModal;
    private boolean hasError;

    private TaxDTO filter;
    
    private List<OperatorDTO> listOperators;
    private List<EstablishmentDTO> listEstablishments;
    private List<CardFlagDTO> listCardFlag;
    private List<TransactionTypeDTO> listTransactionType;
    
    private List<TaxDTO> listSelectedTaxes;

    private TaxDTO editDTO;

    private TaxDTO tableEditSelelectedTax;
	
	/**
	 * construtor padrão
	 */
	public TaxRegisterView() {
		
	}
	
	@PostConstruct
	public void init(){
		LOG.debug("Iniciando action: init");
		
		try {
			listOperators = getFacade().listOperators();
			listEstablishments = getFacade().listEstablishments();
		    listCardFlag = getFacade().listCardFlags();
		    listTransactionType = getFacade().listTransactionTypes();
		} catch (BaseException e) {
			LOG.error(e);
		}
		filter = new TaxDTO();

		listSelectedTaxes = new ArrayList<TaxDTO>();
		
		LOG.debug("Finalizada action: init");
	}
	
	/**
	 * Procura taxas registradas
	 */
	public void actionSearchTaxes() {
		LOG.debug("Iniciando action: actionSearchTaxes");
		numberLines = MAX_OF_LINES.intValue();
		
		try {
			listSelectedTaxes = super.getFacade().listTaxes(filter);
			if (listSelectedTaxes == null || listSelectedTaxes.size() == 0) {
				addWarnMessage(null, getMessageBundle("message.tax.not.found"));
			} else {
				if (listSelectedTaxes.size() <= numberLines) {
					numberLines = listSelectedTaxes.size();
				} else {
					numberLines = MAX_OF_LINES.intValue();
				}
				numberOfPages = new BigDecimal((double) getlistSelectedTaxes().size() / MAX_OF_LINES.intValue())
						.setScale(2, RoundingMode.HALF_UP);
				countPage = 0;
			}
			
		} catch (BaseException e) {
			LOG.error("Error actionSearchTaxes (TE)", e);
			addErrorMessage(null, super.getMessageBundle(BUNDLE_UNKNOWN_ERROR));
		}
		LOG.debug("Finalizada action: actionSearchTaxes");
	}
	
	/**
	 * Incrementa a paginação
	 */
	public void addMoreLines(){
		if (getlistSelectedTaxes() != null) {
			if (getlistSelectedTaxes().size() <= MAX_OF_LINES.intValue()) {
				numberLines = getlistSelectedTaxes().size();
			} else {
				if (countPage <= numberOfPages.intValue()) {
					int offset = getlistSelectedTaxes().size() - numberLines;
					if (offset <= MAX_OF_LINES.intValue()) {
						numberLines = getlistSelectedTaxes().size();
					} else {
						numberLines += MAX_OF_LINES.intValue();
					}
					countPage ++;
				} else if ((numberOfPages.intValue()-countPage) != 0) {
					numberLines = getlistSelectedTaxes().size();
				}
			}
			
		}
	}
		
	/**
	 * Carrega todos os dados do modal de edição
	 * @param value
	 */
	public void loadEditForm(TaxDTO value){
		LOG.debug("Iniciando action loadTaxData: " + value);

		setTableEditSelelectedTax(value);
		
		if (value != null) {
			setEditDTO(value);
			
			if (value.getEstablishment() != null) {
				getEditDTO().setEstablishment(listEstablishments.stream()
		                .filter(o -> o.getId().equals(value.getEstablishment().getId()))
		                .findFirst()
		                .get());
			} else {
				getEditDTO().setEstablishment(null);
			}
			
			if (value.getOperator() != null) {
				getEditDTO().setOperator(listOperators.stream()
		                .filter(o -> o.getId().equals(value.getOperator().getId()))
		                .findFirst()
		                .get());
			} else {
				getEditDTO().setOperator(null);
			}
			
			if (value.getCardFlag() != null) {
				getEditDTO().setCardFlag(listCardFlag.stream()
		                .filter(o -> o.getId().equals(value.getCardFlag().getId()))
		                .findFirst()
		                .get());
			} else {
				getEditDTO().setCardFlag(null);
			}
			
			if (value.getTransactionType() != null) {
				getEditDTO().setTransactionType(listTransactionType.stream()
		                .filter(o -> o.getId().equals(value.getTransactionType().getId()))
		                .findFirst()
		                .get());
			} else {
				getEditDTO().setTransactionType(null);
			}

		}
		
		showModal = true;
		
		LOG.debug("Finalizando action loadTaxData");
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
		
		TaxDTO tax = new TaxDTO();
		tax.setTaxId(getEditDTO().getId());
		tax.setOperator(getEditDTO().getOperator());
		tax.setEstablishment(getEditDTO().getEstablishment());
		tax.setCardFlag(getEditDTO().getCardFlag());
		tax.setTransactionType(getEditDTO().getTransactionType());
		tax.setStartDate(getEditDTO().getStartDate());
		tax.setFinalDate(getEditDTO().getFinalDate());
		tax.setPortionMin(getEditDTO().getPortionMin());
		tax.setPortionMax(getEditDTO().getPortionMax());
		tax.setTaxValue(getEditDTO().getTaxValue());
		tax.setLastUpdateDate(new Date());
		tax.setLastUpdatedByUser(super.getLoggedUser().getUserMail());
		
		try {
			
			super.getFacade().updateTax(tax);
			
			//Atualiza dados da lista
			getTableEditSelelectedTax().setOperator(tax.getOperator());
			getTableEditSelelectedTax().setEstablishment(tax.getEstablishment());
			getTableEditSelelectedTax().setCardFlag(tax.getCardFlag());
			getTableEditSelelectedTax().setTransactionType(tax.getTransactionType());
			getTableEditSelelectedTax().setStartDate(tax.getStartDate());
			getTableEditSelelectedTax().setFinalDate(tax.getFinalDate());
			getTableEditSelelectedTax().setPortionMin(tax.getPortionMin());
			getTableEditSelelectedTax().setPortionMax(tax.getPortionMax());
			getTableEditSelelectedTax().setTaxValue(tax.getTaxValue());
			getTableEditSelelectedTax().setLastUpdateDate(tax.getLastUpdateDate());
			getTableEditSelelectedTax().setLastUpdatedByUser(tax.getLastUpdatedByUser());
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));

			actionSearchTaxes();
			
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
	public void actionBeforeNewTaxListener() {
		LOG.debug("Iniciando action: actionBeforeNewTaxListener");

		setEditDTO(new TaxDTO());

		LOG.debug("Finalizada action: actionBeforeNewTaxListener");
	}
	
	/**
	 * Salva os dados alterados
	 */
	public void actionNewTax() {
		LOG.debug("Iniciando action: actionNewTax");
		hasError = false;

		try {
			validate();
		} catch (TechnicalException e) {
			addWarnMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(e.getMessage()));
			hasError = true;
			return;
		}
		
		try {
			TaxDTO tax = new TaxDTO();
			tax.setTaxId(null);
			tax.setEstablishment(getEditDTO().getEstablishment());
			tax.setOperator(getEditDTO().getOperator());
			tax.setCardFlag(getEditDTO().getCardFlag());
			tax.setTransactionType(getEditDTO().getTransactionType());
			tax.setStartDate(getEditDTO().getStartDate());
			tax.setFinalDate(getEditDTO().getFinalDate());
			tax.setPortionMin(getEditDTO().getPortionMin());
			tax.setPortionMax(getEditDTO().getPortionMax());
			tax.setTaxValue(getEditDTO().getTaxValue());
			tax.setLastUpdateDate(new Date());
			tax.setLastUpdatedByUser(super.getLoggedUser().getUserMail());
			
			super.getFacade().insertTax(tax);
			
			actionSearchTaxes();
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));
		} catch (BaseException e) {
			if (e instanceof TechnicalException) {
				LOG.error("Error actionNewTax (TE)", e);
				addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			} else {
				LOG.error("Error actionNewTax (BE)", e);
				addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(((BusinessException)e).getType().getBundleKey()));
				setHasError(true);
			}
		}

		LOG.debug("Finalizada action: actionNewTax");
	}
	
	/**
	 * Exclui um tax
	 */
	public void actionDeleteTax() {
		LOG.debug("Iniciando action: actionDeleteTax");
		hasError = false;

		try {
			TaxDTO tax = new TaxDTO();
			tax.setTaxId(getEditDTO().getId());
			
			super.getFacade().deleteTax(tax);
			
			actionSearchTaxes();
			
			addInfoMessage(null, getMessageBundle("message.operation.success"));
		} catch (BaseException e) {
			if (e instanceof TechnicalException) {
				LOG.error("Error actionDeleteTax (TE)", e);
				addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(BUNDLE_UNKNOWN_ERROR));
				setHasError(true);
			} else {
				LOG.error("Error actionDeleteTax (BE)", e);
				addErrorMessage(MESSAGE_MODAL_ADD_ID, getMessageBundle(((BusinessException)e).getType().getBundleKey()));
				setHasError(true);
			}
		}

		LOG.debug("Finalizada action: actionDeleteTax");
	}
	
	/**
	 * @return Lista de taxas pesquisadas
	 */
	public List<TaxDTO> getlistSelectedTaxes() {
		return listSelectedTaxes;
	}

	/**
	 * @return the tableEditSelelectedTax
	 */
	public TaxDTO getTableEditSelelectedTax() {
		return tableEditSelelectedTax;
	}

	/**
	 * @param tableEditSelelectedTax the tableEditSelelectedTax to set
	 */
	public void setTableEditSelelectedTax(TaxDTO tableEditSelelectedTax) {
		this.tableEditSelelectedTax = tableEditSelelectedTax;
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
	 * @return the listCardFlag
	 */
	public List<CardFlagDTO> getListCardFlag() {
		return listCardFlag;
	}

	/**
	 * @param listCardFlag the listCardFlag to set
	 */
	public void setListCardFlag(List<CardFlagDTO> listCardFlag) {
		this.listCardFlag = listCardFlag;
	}

	/**
	 * @return the listTransactionType
	 */
	public List<TransactionTypeDTO> getListTransactionType() {
		return listTransactionType;
	}

	/**
	 * @param listTransactionType the listTransactionType to set
	 */
	public void setListTransactionType(List<TransactionTypeDTO> listTransactionType) {
		this.listTransactionType = listTransactionType;
	}

	/**
	 * @return the filter
	 */
	public TaxDTO getFilter() {
		return filter;
	}

	/**
	 * @param filter the filter to set
	 */
	public void setFilter(TaxDTO filter) {
		this.filter = filter;
	}

	/**
	 * @return the editDTO
	 */
	public TaxDTO getEditDTO() {
		return editDTO;
	}

	/**
	 * @param editDTO the editDTO to set
	 */
	public void setEditDTO(TaxDTO editDTO) {
		this.editDTO = editDTO;
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
		return getMessageValues("tax_maintain.label.you_are_seeing", getCurrentPage(), 
				getlistSelectedTaxes() != null ? getlistSelectedTaxes().size() : 0);
		
	}

	/**
	 * Realiza a consistência dos dados informados
	 * @throws TechnicalException
	 */
	private void validate() throws TechnicalException {
		ValidateTax validate = new ValidateTax();
		validate.checkOperator();
		validate.checkTransactionType();
		validate.checkStartDate();
		validate.checkTaxValue();
	}
	
	/**
	 * Classe para validar informações da View
	 * @author 
	 */
	class ValidateTax {
		/**
		 * Validação da operadora
		 * @return true/false
		 */
		public void checkOperator() throws TechnicalException {
			if (getEditDTO().getOperator() == null) {
				throw new TechnicalException("message.select.required.operator");
			}
		}

		/**
		 * Validação da tipo de transação
		 * @return true/false
		 */
		public void checkTransactionType() throws TechnicalException {
			if (getEditDTO().getTransactionType() == null) {
				throw new TechnicalException("message.select.required.transaction.type");
			}
		}

		/**
		 * Validação da data inicial
		 * @return true/false
		 */
		public void checkStartDate() throws TechnicalException {
			if (getEditDTO().getStartDate() == null) {
				throw new TechnicalException("message.text.required.start.date");
			}
			if (DateUtil.firstHour(getEditDTO().getStartDate()).before(DateUtil.nowFirstHour())) {
				throw new TechnicalException("message.text.earlier_now.start.date");
			}
			if (getEditDTO().getFinalDate() != null) {
				if (DateUtil.firstHour(getEditDTO().getFinalDate()).before(DateUtil.firstHour(getEditDTO().getStartDate()))) {
					throw new TechnicalException("message.text.after_final.start.date");
				}
			}
		}

		/**
		 * Validação do valor da taxa
		 * @return true/false
		 */
		public void checkTaxValue() throws TechnicalException {
			if (getEditDTO().getTaxValue() == null) {
				throw new TechnicalException("message.text.required.taxValue");
			} else {
				if (getEditDTO().getTaxValue().doubleValue() <= 0) {
					throw new TechnicalException("message.value.less.than.or.eual.zero");
				}
			}
		}
	}
}
