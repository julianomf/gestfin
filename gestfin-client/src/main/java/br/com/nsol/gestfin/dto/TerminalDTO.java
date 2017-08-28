package br.com.nsol.gestfin.dto;

import java.util.Date;

public class TerminalDTO implements BaseDTO, Cloneable {

	private static final long serialVersionUID = -2830762430301186829L;
	
	private Integer terminalId;
	private String terminalNumber;
	private String description;
	private OperatorDTO operator;
	private EstablishmentDTO establishment;
	private String isActive;
	private Date lastUpdateDate;
	private String lastUpdatedByUser;

	/**
	 * @return the terminalId
	 */
	public Integer getId() {
		return terminalId;
	}

	/**
	 * @return the terminalId
	 */
	public Integer getTerminalId() {
		return terminalId;
	}

	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId(Integer terminalId) {
		this.terminalId = terminalId;
	}

	/**
	 * @return the terminalNumber
	 */
	public String getTerminalNumber() {
		return terminalNumber;
	}

	/**
	 * @param terminalNumber the terminalNumber to set
	 */
	public void setTerminalNumber(String terminalNumber) {
		this.terminalNumber = terminalNumber;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}

	/**
	 * @param isActive
	 *            the isActive to set
	 */
	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	/**
	 * @return the lastUpdateDate
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * @param lastUpdateDate
	 *            the lastUpdateDate to set
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
	 * @param lastUpdatedByUser
	 *            the lastUpdatedByUser to set
	 */
	public void setLastUpdatedByUser(String lastUpdatedByUser) {
		this.lastUpdatedByUser = lastUpdatedByUser;
	}

	/**
	 * Clones the object
	 */
	public TerminalDTO clone() throws CloneNotSupportedException {
		return (TerminalDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TerminalDTO [terminalId=" + terminalId + ", terminalNumber=" + terminalNumber + ", description="
				+ description + ", operator=" + operator + ", establishment=" + establishment + ", isActive=" + isActive
				+ ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatedByUser=" + lastUpdatedByUser + "]";
	}

	public String getIsActiveBundle() {
		if ("A".equals(isActive)) {
			return "is-active-a";
		} else if ("I".equals(isActive)) {
			return "is-active-i";
		} else {
			return null;
		}
	}
}
