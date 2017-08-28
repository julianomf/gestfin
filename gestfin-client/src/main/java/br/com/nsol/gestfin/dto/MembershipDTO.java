package br.com.nsol.gestfin.dto;

import java.io.Serializable;

public class MembershipDTO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 23890938408511659L;

	private Integer id;
	private UserDTO user;
	private CompanyDTO company;

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
	 * @return the user
	 */
	public UserDTO getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(UserDTO user) {
		this.user = user;
	}

	/**
	 * @return the company
	 */
	public CompanyDTO getCompany() {
		return company;
	}

	/**
	 * @param company the company to set
	 */
	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	/**
	 * Clones the object
	 */
	public MembershipDTO clone() throws CloneNotSupportedException {
		return (MembershipDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MembershipDTO [id=" + id + ", user=" + user + ", company=" + company + "]";
	}

}
