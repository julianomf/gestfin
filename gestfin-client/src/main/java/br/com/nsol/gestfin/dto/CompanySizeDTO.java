package br.com.nsol.gestfin.dto;

import java.io.Serializable;

public class CompanySizeDTO implements Serializable, Cloneable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4650046179182070665L;
	
	private Integer id;
	private String name;
	private String description;
	private String iconType;
	private String iconReference;
	private String buttonClass;

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
	 * @return the iconType
	 */
	public String getIconType() {
		return iconType;
	}

	/**
	 * @param iconType the iconType to set
	 */
	public void setIconType(String iconType) {
		this.iconType = iconType;
	}

	/**
	 * @return the iconReference
	 */
	public String getIconReference() {
		return iconReference;
	}

	/**
	 * @param iconReference the iconReference to set
	 */
	public void setIconReference(String iconReference) {
		this.iconReference = iconReference;
	}

	/**
	 * @return the buttonClass
	 */
	public String getButtonClass() {
		return buttonClass;
	}

	/**
	 * @param buttonClass the buttonClass to set
	 */
	public void setButtonClass(String buttonClass) {
		this.buttonClass = buttonClass;
	}

	/**
	 * Clones the object
	 */
	public CompanySizeDTO clone() throws CloneNotSupportedException {
		return (CompanySizeDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CompanySizeDTO [id=" + id + ", name=" + name + ", description=" + description + ", iconType=" + iconType
				+ ", iconReference=" + iconReference + ", buttonClass=" + buttonClass + "]";
	}

}
