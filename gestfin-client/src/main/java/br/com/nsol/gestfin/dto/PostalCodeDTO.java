package br.com.nsol.gestfin.dto;

import java.io.Serializable;

public class PostalCodeDTO implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6527642717687018499L;

	private Integer postalCode;
	private String streetName;
	private String district;
	private String city;
	private String state;

	/**
	 * @return the postalCode
	 */
	public Integer getPostalCode() {
		return postalCode;
	}

	/**
	 * @param postalCode the postalCode to set
	 */
	public void setPostalCode(Integer postalCode) {
		this.postalCode = postalCode;
	}

	/**
	 * @return the streetName
	 */
	public String getStreetName() {
		return streetName;
	}

	/**
	 * @param streetName the streetName to set
	 */
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	/**
	 * @return the district
	 */
	public String getDistrict() {
		return district;
	}

	/**
	 * @param district the district to set
	 */
	public void setDistrict(String district) {
		this.district = district;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Clones the object
	 */
	public PostalCodeDTO clone() throws CloneNotSupportedException {
		return (PostalCodeDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PostalCodeDTO [postalCode=" + postalCode + ", streetName=" + streetName + ", district=" + district
				+ ", city=" + city + ", state=" + state + "]";
	}

}
