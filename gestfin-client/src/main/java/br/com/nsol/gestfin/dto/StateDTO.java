package br.com.nsol.gestfin.dto;

import java.io.Serializable;

public class StateDTO implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 366574435412092952L;
	
	private String id;
	private String name;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
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
	public StateDTO clone() throws CloneNotSupportedException {
		return (StateDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StateDTO [id=" + id + ", name=" + name + "]";
	}

}
