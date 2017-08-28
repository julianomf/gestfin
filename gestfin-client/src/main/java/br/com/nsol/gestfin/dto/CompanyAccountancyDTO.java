package br.com.nsol.gestfin.dto;

import java.io.Serializable;

public class CompanyAccountancyDTO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3134434680370357322L;
	
	private String name;
	private Long documentNumber;
	private String sponsor;
	private Long phoneNumber;
	private String mail;
	private Integer companyId;	

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the documentNumber
	 */
	public Long getDocumentNumber() {
		return documentNumber;
	}

	/**
	 * @param documentNumber the documentNumber to set
	 */
	public void setDocumentNumber(Long documentNumber) {
		this.documentNumber = documentNumber;
	}

	/**
	 * @return the sponsor
	 */
	public String getSponsor() {
		return sponsor;
	}

	/**
	 * @param sponsor the sponsor to set
	 */
	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	/**
	 * @return the phoneNumber
	 */
	public Long getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
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
	public CompanyAccountancyDTO clone() throws CloneNotSupportedException {
		return (CompanyAccountancyDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CompanyAccountancyDTO [name=" + name + ", documentNumber=" + documentNumber + ", sponsor=" + sponsor
				+ ", phoneNumber=" + phoneNumber + ", mail=" + mail + ", companyId=" + companyId + "]";
	}

}
