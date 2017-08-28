package br.com.nsol.gestfin.dto;

import java.io.Serializable;
import java.util.Date;

public class ProfileDTO implements Serializable, Cloneable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5425623259206818219L;

	private Integer profileId;
	private String profileName;
	private String profileDescription;
	private String isActive;
	private Date lastUpdateDate;
	private String lastUpdatedByUser;

	/**
	 * @return the profileId
	 */
	public Integer getProfileId() {
		return profileId;
	}
	
	/**
	 * @param profileId the profileId to set
	 */
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	
	/**
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}
	
	/**
	 * @param profileName the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	/**
	 * @return the profileDescription
	 */
	public String getProfileDescription() {
		return profileDescription;
	}
	
	/**
	 * @param profileDescription the profileDescription to set
	 */
	public void setProfileDescription(String profileDescription) {
		this.profileDescription = profileDescription;
	}
	
	/**
	 * @return the isActive
	 */
	public String getIsActive() {
		return isActive;
	}
	
	/**
	 * @param isActive the isActive to set
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
	public ProfileDTO clone() throws CloneNotSupportedException {
		return (ProfileDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ProfileDTO [profileId=" + profileId + ", profileName=" + profileName + ", profileDescription="
				+ profileDescription + ", isActive=" + isActive + ", lastUpdateDate=" + lastUpdateDate 
				+ ", lastUpdatedByUser=" + lastUpdatedByUser + "]";
	}

}
