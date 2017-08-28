package br.com.nsol.gestfin.enums;

/**
 * Enum para identificar o status da empresa
 * @author 
 */
public enum CompanyStatusEnum {
	UNFINISHED_REGISTRATION("U"),
	REGISTRATION_COMPLETED("C");
	
	private String value;
	CompanyStatusEnum(String status) {
		this.value = status;
	}
	
	public String getValue() {
		return this.value;
	}
}
