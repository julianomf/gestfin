package br.com.nsol.gestfin.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TaxDTO implements BaseDTO, Cloneable {

	private static final long serialVersionUID = -8682628054658565315L;

	private Integer taxId;
	private EstablishmentDTO establishment;
	private OperatorDTO operator;
	private CardFlagDTO cardFlag;
	private TransactionTypeDTO transactionType;
	private Date startDate;
	private Date finalDate;
	private Integer portionMin;
	private Integer portionMax;
	private BigDecimal taxValue;
	private Date lastUpdateDate;
	private String lastUpdatedByUser;

	/**
	 * @return the taxId
	 */
	public Integer getId() {
		return taxId;
	}

	/**
	 * @return the taxId
	 */
	public Integer getTaxId() {
		return taxId;
	}

	/**
	 * @param taxId the taxId to set
	 */
	public void setTaxId(Integer taxId) {
		this.taxId = taxId;
	}

	/**
	 * @return the establishment
	 */
	public EstablishmentDTO getEstablishment() {
		return establishment;
	}

	/**
	 * @param establishment the establishment to set
	 */
	public void setEstablishment(EstablishmentDTO establishment) {
		this.establishment = establishment;
	}

	/**
	 * @return the operator
	 */
	public OperatorDTO getOperator() {
		return operator;
	}

	/**
	 * @param operator the operator to set
	 */
	public void setOperator(OperatorDTO operator) {
		this.operator = operator;
	}

	/**
	 * @return the cardFlag
	 */
	public CardFlagDTO getCardFlag() {
		return cardFlag;
	}

	/**
	 * @param cardFlag the cardFlag to set
	 */
	public void setCardFlag(CardFlagDTO cardFlag) {
		this.cardFlag = cardFlag;
	}

	/**
	 * @return the transactionType
	 */
	public TransactionTypeDTO getTransactionType() {
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType(TransactionTypeDTO transactionType) {
		this.transactionType = transactionType;
	}


	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the finalDate
	 */
	public Date getFinalDate() {
		return finalDate;
	}

	/**
	 * @param finalDate the finalDate to set
	 */
	public void setFinalDate(Date finalDate) {
		this.finalDate = finalDate;
	}

	/**
	 * @return the portionMin
	 */
	public Integer getPortionMin() {
		return portionMin;
	}

	/**
	 * @param portionMin the portionMin to set
	 */
	public void setPortionMin(Integer portionMin) {
		this.portionMin = portionMin;
	}

	/**
	 * @return the portionMax
	 */
	public Integer getPortionMax() {
		return portionMax;
	}

	/**
	 * @param portionMax the portionMax to set
	 */
	public void setPortionMax(Integer portionMax) {
		this.portionMax = portionMax;
	}

	/**
	 * @return the taxValue
	 */
	public BigDecimal getTaxValue() {
		return taxValue;
	}

	/**
	 * @param taxValue the taxValue to set
	 */
	public void setTaxValue(BigDecimal taxValue) {
		this.taxValue = taxValue;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate the lastUpdateDate to set
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return the lastUpdatedByUser
	 */
	public String getLastUpdatedByUser() {
		return lastUpdatedByUser;
	}

	/**
	 * @param lastUpdatedByUser the lastUpdatedByUser to set
	 */
	public void setLastUpdatedByUser(String lastUpdatedByUser) {
		this.lastUpdatedByUser = lastUpdatedByUser;
	}

	/**
	 * Clones the object
	 */
	public TaxDTO clone() throws CloneNotSupportedException {
		return (TaxDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaxDTO [taxId=" + taxId + ", establishment=" + establishment + ", operator=" + operator + ", cardFlag="
				+ cardFlag + ", transactionType=" + transactionType + ", startDate=" + startDate + ", finalDate="
				+ finalDate + ", portionMin=" + portionMin + ", portionMax=" + portionMax + ", taxValue=" + taxValue
				+ ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatedByUser="
				+ lastUpdatedByUser + "]";
	}

}
