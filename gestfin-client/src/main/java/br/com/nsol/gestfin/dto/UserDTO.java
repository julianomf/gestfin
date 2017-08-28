package br.com.nsol.gestfin.dto;

import java.io.Serializable;
import java.util.Date;

public class UserDTO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 242200464342723564L;

	private Integer userId;
	private String userName;
	private String userMail;
	private String userPassword;
	private Integer profileId;
	private String isPasswordReseted;
	private String isActive;
	private Date lastUpdateDate;
	private String lastUpdatedByUser;

	private String profileName;
	private Integer companyId;

	/**
	 * @return the userId
	 */
	public Integer getUserId() {
		return userId;
	}

	/**
	 * @param userId
	 *            the userId to set
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName
	 *            the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the userMail
	 */
	public String getUserMail() {
		return userMail;
	}

	/**
	 * @param userMail
	 *            the userMail to set
	 */
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword
	 *            the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * @return the profileId
	 */
	public Integer getProfileId() {
		return profileId;
	}

	/**
	 * @param profileId
	 *            the profileId to set
	 */
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}

	/**
	 * @return the isPasswordReseted
	 */
	public String getIsPasswordReseted() {
		return isPasswordReseted;
	}

	/**
	 * @param isPasswordReseted
	 *            the isPasswordReseted to set
	 */
	public void setIsPasswordReseted(String isPasswordReseted) {
		this.isPasswordReseted = isPasswordReseted;
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
	 * @return the profileName
	 */
	public String getProfileName() {
		return profileName;
	}

	/**
	 * @param profileName
	 *            the profileName to set
	 */
	public void setProfileName(String profileName) {
		this.profileName = profileName;
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
	public UserDTO clone() throws CloneNotSupportedException {
		return (UserDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "UserDTO [userId=" + userId + ", userName=" + userName + ", userMail=" + userMail + ", userPassword="
				+ userPassword + ", profileId=" + profileId + ", isPasswordReseted=" + isPasswordReseted + ", isActive="
				+ isActive + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdatedByUser=" + lastUpdatedByUser
				+ ", profileName=" + profileName + ", companyId=" + companyId + "]";
	}

	public String getIsActiveBundle() {
		if ("A".equals(isActive)) {
			return "is-active-a";
		} else if ("I".equals(isActive)) {
			return "is-active-i";
		} else
			return null;
	}
}
