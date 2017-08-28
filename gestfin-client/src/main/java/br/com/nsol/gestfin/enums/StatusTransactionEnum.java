package br.com.nsol.gestfin.enums;

/**
 * Enum para identificar o status da transacao
 * @author 
 */
public enum StatusTransactionEnum {
	SUCCESS("S"),
	ERROR("E");
	
	private String value;
	StatusTransactionEnum(String status) {
		this.value = status;
	}
	
	public String getValue() {
		return this.value;
	}
}
