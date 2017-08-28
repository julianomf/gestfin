package br.com.nsol.gestfin.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SystemParameterDTO implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1287836574620859657L;

	private Integer parameterId;
	private String parameterName;
	private String parameterDescription;
	private String parameterInstruction;
	private String stringValue;
	private BigDecimal numericValue;
	private Date lastUpdateDate;
	private String lastUpdatedByUser;
	
	/**
	 * @return the parameterId
	 */
	public Integer getParameterId() {
		return parameterId;
	}
	/**
	 * @param parameterId the parameterId to set
	 */
	public void setParameterId(Integer parameterId) {
		this.parameterId = parameterId;
	}
	/**
	 * @return the parameterName
	 */
	public String getParameterName() {
		return parameterName;
	}
	/**
	 * @param parameterName the parameterName to set
	 */
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	/**
	 * @return the parameterDescription
	 */
	public String getParameterDescription() {
		return parameterDescription;
	}
	/**
	 * @param parameterDescription the parameterDescription to set
	 */
	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}
	/**
	 * @return the parameterInstruction
	 */
	public String getParameterInstruction() {
		return parameterInstruction;
	}
	/**
	 * @param parameterInstruction the parameterInstruction to set
	 */
	public void setParameterInstruction(String parameterInstruction) {
		this.parameterInstruction = parameterInstruction;
	}
	/**
	 * @return the stringValue
	 */
	public String getStringValue() {
		return stringValue;
	}
	/**
	 * @param stringValue the stringValue to set
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}
	/**
	 * @return the numericValue
	 */
	public BigDecimal getNumericValue() {
		return numericValue;
	}
	/**
	 * @param numericValue the numericValue to set
	 */
	public void setNumericValue(BigDecimal numericValue) {
		this.numericValue = numericValue;
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
	public SystemParameterDTO clone() throws CloneNotSupportedException {
		return (SystemParameterDTO) super.clone();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SystemParameterDTO [parameterId=" + parameterId + ", parameterName=" + parameterName
				+ ", parameterDescription=" + parameterDescription + ", parameterInstruction=" + parameterInstruction
				+ ", stringValue=" + stringValue + ", numericValue=" + numericValue + ", lastUpdateDate="
				+ lastUpdateDate + ", lastUpdatedByUser=" + lastUpdatedByUser + "]";
	}

}
