package br.com.nsol.gestfin.exceptions;

import br.com.nsol.gestfin.enums.BusinessExceptionType;

/**
 * Deve ser utilizada quando for preciso lançar uma exceção de Negócio.
 * 
 * @author 
 * 
 */
public class BusinessException extends BaseException {
	private static final long serialVersionUID = -5514145915413248341L;
	
	private BusinessExceptionType type;
	private String message;

	public BusinessExceptionType getType() {
		return type;
	}

	public void setType(BusinessExceptionType type) {
		this.type = type;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Construtor para uma <code>BusinessException</code> com o tipo da falha ou erro.
	 * 
	 * @param type O tipo da falha ou erro.
	 */
	public BusinessException(BusinessExceptionType type) {
		super(type.getMessage());
		this.message = null;
		this.type = type;
	}
	
	/**
	 * Construtor para uma <code>BusinessException</code> com apenas uma mensagem de erro.
	 * @param message
	 */
	public BusinessException(String message) {
		super(message);
		this.message = message;
		this.type = BusinessExceptionType.CUSTOM_MESSAGE_EXCEPTION;
	}
	
}
