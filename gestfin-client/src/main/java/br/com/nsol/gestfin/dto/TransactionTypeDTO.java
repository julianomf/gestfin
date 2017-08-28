package br.com.nsol.gestfin.dto;

public class TransactionTypeDTO implements BaseDTO, Cloneable {

	private static final long serialVersionUID = -5616296395472346345L;

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
		return this.name;
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
	public TransactionTypeDTO clone() throws CloneNotSupportedException {
		return (TransactionTypeDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TransactionTypeDTO [id=" + this.id + ", Name=" + this.name + "]";
	}

}
