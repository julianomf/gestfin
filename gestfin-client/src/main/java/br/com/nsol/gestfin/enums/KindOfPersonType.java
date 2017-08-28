package br.com.nsol.gestfin.enums;

public enum KindOfPersonType {
	PF("F", "kind-of-person-f"), 
	PJ("J", "kind-of-person-j");

	private final String value;
	private final String description;

	public String getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	/**
	 * @param text
	 */
	private KindOfPersonType(final String value, final String description) {
		this.value = value;
		this.description = description;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return value + " - " + description;
	}
}
