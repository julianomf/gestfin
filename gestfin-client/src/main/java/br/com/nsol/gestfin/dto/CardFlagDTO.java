package br.com.nsol.gestfin.dto;

public class CardFlagDTO implements BaseDTO, Cloneable {

	private static final long serialVersionUID = 4505615977793471770L;

	private Integer id;
	private String name;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
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
	public CardFlagDTO clone() throws CloneNotSupportedException {
		return (CardFlagDTO) super.clone();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CardFlagDTO [id=" + this.id + ", Name=" + this.name + "]";
	}

}
