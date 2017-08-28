package br.com.nsol.gestfin.dto;

import java.io.Serializable;

public class DomainDTO implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8029013467769988021L;
	
	private String value;
	private String description;

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
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
	 * Clones the object
	 */
	public DomainDTO clone() throws CloneNotSupportedException {
		return (DomainDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DomainDTO [value=" + value + ", description=" + description + "]";
	}

}
