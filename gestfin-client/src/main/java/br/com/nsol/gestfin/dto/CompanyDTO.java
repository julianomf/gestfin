package br.com.nsol.gestfin.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import br.com.nsol.gestfin.enums.KindOfPersonType;

public class CompanyDTO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5725753333733016938L;
	
	// Dados básicos da empresa
	private Integer id;
	private String kindOfPerson;
	private Long documentNumber;
	private Long stateRegistration;
	private boolean isFreeFromStateRegistration;
	private String socialName;
	private String fantasyName;
	private Long municipalRegistration;
	private TaxRegimeDTO taxRegime;
	private Integer cnae;
	private String sponsor;
	private Integer ownerId;
	private String status;
	private Date lastUpdateDate;
	private String ipAddress;
	private Date membershipDate;

	// 
	private List<CompanyAddressDTO> addresses;
	private List<CompanyContactDTO> contacts;
	private CompanyAccountancyDTO accountancy;
	private CompanyQuestionaireDTO questionaire;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the kindOfPerson
	 */
	public String getKindOfPerson() {
		return kindOfPerson;
	}

	/**
	 * @param kindOfPerson the kindOfPerson to set
	 */
	public void setKindOfPerson(String kindOfPerson) {
		this.kindOfPerson = kindOfPerson;
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
	 * @return the stateRegistration
	 */
	public Long getStateRegistration() {
		return stateRegistration;
	}

	/**
	 * @param stateRegistration the stateRegistration to set
	 */
	public void setStateRegistration(Long stateRegistration) {
		this.stateRegistration = stateRegistration;
	}

	/**
	 * @return the isFreeFromStateRegistration
	 */
	public boolean isFreeFromStateRegistration() {
		return isFreeFromStateRegistration;
	}

	/**
	 * @param isFreeFromStateRegistration the isFreeFromStateRegistration to set
	 */
	public void setFreeFromStateRegistration(boolean isFreeFromStateRegistration) {
		this.isFreeFromStateRegistration = isFreeFromStateRegistration;
	}

	/**
	 * @return the socialName
	 */
	public String getSocialName() {
		return socialName;
	}

	/**
	 * @param socialName the socialName to set
	 */
	public void setSocialName(String socialName) {
		this.socialName = socialName;
	}

	/**
	 * @return the fantasyName
	 */
	public String getFantasyName() {
		return fantasyName;
	}

	/**
	 * @param fantasyName the fantasyName to set
	 */
	public void setFantasyName(String fantasyName) {
		this.fantasyName = fantasyName;
	}

	/**
	 * @return the municipalRegistration
	 */
	public Long getMunicipalRegistration() {
		return municipalRegistration;
	}

	/**
	 * @param municipalRegistration the municipalRegistration to set
	 */
	public void setMunicipalRegistration(Long municipalRegistration) {
		this.municipalRegistration = municipalRegistration;
	}

	/**
	 * @return the taxRegime
	 */
	public TaxRegimeDTO getTaxRegime() {
		return taxRegime;
	}

	/**
	 * @param taxRegime the taxRegime to set
	 */
	public void setTaxRegime(TaxRegimeDTO taxRegime) {
		this.taxRegime = taxRegime;
	}

	/**
	 * @return the cnae
	 */
	public Integer getCnae() {
		return cnae;
	}

	/**
	 * @param cnae the cnae to set
	 */
	public void setCnae(Integer cnae) {
		this.cnae = cnae;
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
	 * @return the ownerId
	 */
	public Integer getOwnerId() {
		return ownerId;
	}

	/**
	 * @param ownerId the ownerId to set
	 */
	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
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
	 * @return the ipAddress
	 */
	public String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the membershipDate
	 */
	public Date getMembershipDate() {
		return membershipDate;
	}

	/**
	 * @param membershipDate the membershipDate to set
	 */
	public void setMembershipDate(Date membershipDate) {
		this.membershipDate = membershipDate;
	}

	/**
	 * @return the addresses
	 */
	public List<CompanyAddressDTO> getAddresses() {
		return addresses;
	}

	/**
	 * @param addresses the addresses to set
	 */
	public void setAddresses(List<CompanyAddressDTO> addresses) {
		this.addresses = addresses;
	}

	/**
	 * @return the contacts
	 */
	public List<CompanyContactDTO> getContacts() {
		return contacts;
	}

	/**
	 * @param contacts the contacts to set
	 */
	public void setContacts(List<CompanyContactDTO> contacts) {
		this.contacts = contacts;
	}

	/**
	 * @return the accountancy
	 */
	public CompanyAccountancyDTO getAccountancy() {
		return accountancy;
	}

	/**
	 * @param accountancy the accountancy to set
	 */
	public void setAccountancy(CompanyAccountancyDTO accountancy) {
		this.accountancy = accountancy;
	}

	/**
	 * @return the questionaire
	 */
	public CompanyQuestionaireDTO getQuestionaire() {
		return questionaire;
	}

	/**
	 * @param questionaire the questionaire to set
	 */
	public void setQuestionaire(CompanyQuestionaireDTO questionaire) {
		this.questionaire = questionaire;
	}

	/**
	 * Auxiliar para pegar a descrição do tipo de pessoa
	 * @return
	 */
	public String getKindOfPersonDescription() {
		for (KindOfPersonType kind: KindOfPersonType.values()) {
			if(kind.getValue().equals(kindOfPerson)) {
				return kind.getDescription();
			}
		}
		return null;
	}
	
	/**
	 * Clones the object
	 */
	public CompanyDTO clone() throws CloneNotSupportedException {
		return (CompanyDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", kindOfPerson=" + kindOfPerson + ", documentNumber=" + documentNumber
				+ ", stateRegistration=" + stateRegistration + ", isFreeFromStateRegistration="
				+ isFreeFromStateRegistration + ", socialName=" + socialName + ", fantasyName=" + fantasyName
				+ ", municipalRegistration=" + municipalRegistration + ", taxRegime=" + taxRegime + ", cnae=" + cnae
				+ ", sponsor=" + sponsor + ", ownerId=" + ownerId + ", status=" + status + ", lastUpdateDate="
				+ lastUpdateDate + ", ipAddress=" + ipAddress + ", membershipDate=" + membershipDate + ", addresses="
				+ addresses + ", contacts=" + contacts + ", accountancy=" + accountancy + ", questionaire="
				+ questionaire + "]";
	}

}
