package br.com.nsol.gestfin.dto;

import java.io.Serializable;

public class CompanyIssueDTO implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4439803060710229243L;
	
	private Integer id;
	private String name;
	private String description;
	
	private boolean selected;

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
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Clones the object
	 */
	public CompanyIssueDTO clone() throws CloneNotSupportedException {
		return (CompanyIssueDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CompanyIssueDTO [id=" + id + ", name=" + name + ", description=" + description + ", isSelected="
				+ selected + "]";
	}

}
