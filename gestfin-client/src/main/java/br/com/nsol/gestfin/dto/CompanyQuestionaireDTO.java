package br.com.nsol.gestfin.dto;

import java.io.Serializable;
import java.util.List;

public class CompanyQuestionaireDTO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5746267378587029818L;
	
	private CompanySizeDTO size;
	private CompanyEmployeeDTO employee;
	private CompanySellDTO sell;
	private CompanyBusinessDTO business;
	private Boolean hasIssuedInvoices;
	private List<CompanyIssueDTO> issues;
	private Integer companyId;

	/**
	 * @return the size
	 */
	public CompanySizeDTO getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(CompanySizeDTO size) {
		this.size = size;
	}

	/**
	 * @return the employee
	 */
	public CompanyEmployeeDTO getEmployee() {
		return employee;
	}

	/**
	 * @param employee the employee to set
	 */
	public void setEmployee(CompanyEmployeeDTO employee) {
		this.employee = employee;
	}

	/**
	 * @return the sell
	 */
	public CompanySellDTO getSell() {
		return sell;
	}

	/**
	 * @param sell the sell to set
	 */
	public void setSell(CompanySellDTO sell) {
		this.sell = sell;
	}

	/**
	 * @return the business
	 */
	public CompanyBusinessDTO getBusiness() {
		return business;
	}

	/**
	 * @param business the business to set
	 */
	public void setBusiness(CompanyBusinessDTO business) {
		this.business = business;
	}

	/**
	 * @return the hasIssuedInvoices
	 */
	public Boolean isHasIssuedInvoices() {
		return hasIssuedInvoices;
	}

	/**
	 * @param hasIssuedInvoices the hasIssuedInvoices to set
	 */
	public void setHasIssuedInvoices(Boolean hasIssuedInvoices) {
		this.hasIssuedInvoices = hasIssuedInvoices;
	}

	/**
	 * @return the issues
	 */
	public List<CompanyIssueDTO> getIssues() {
		return issues;
	}

	/**
	 * @param issues the issues to set
	 */
	public void setIssues(List<CompanyIssueDTO> issues) {
		this.issues = issues;
	}

	/**
	 * @return the companyId
	 */
	public Integer getCompanyId() {
		return companyId;
	}

	/**
	 * @param companyId the companyId to set
	 */
	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	/**
	 * Clones the object
	 */
	public CompanyQuestionaireDTO clone() throws CloneNotSupportedException {
		return (CompanyQuestionaireDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CompanyQuestionaireDTO [size=" + size + ", employee=" + employee + ", sell=" + sell + ", business="
				+ business + ", hasIssuedInvoices=" + hasIssuedInvoices + ", issues=" + issues + ", companyId="
				+ companyId + "]";
	}

}
