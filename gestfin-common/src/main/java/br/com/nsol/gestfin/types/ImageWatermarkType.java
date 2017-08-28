package br.com.nsol.gestfin.types;

public enum ImageWatermarkType {
	BULLET(0, "Ponto de marcação circular"), 
	TEXT(1, "Texto");

	private final Integer code;
	private final String message;

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * @param text
	 */
	private ImageWatermarkType(final Integer code, final String message) {
		this.code = code;
		this.message = message;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return code + " - " + message;
	}
}
