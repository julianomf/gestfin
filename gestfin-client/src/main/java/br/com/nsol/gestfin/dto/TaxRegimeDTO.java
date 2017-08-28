package br.com.nsol.gestfin.dto;

import java.io.Serializable;

public class TaxRegimeDTO implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4888327409498666496L;
	
	private Integer id;
	private String name;

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
	 * Clones the object
	 */
	public TaxRegimeDTO clone() throws CloneNotSupportedException {
		return (TaxRegimeDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TaxRegimeDTO [id=" + id + ", name=" + name + "]";
	}

}
