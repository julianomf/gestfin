package br.com.nsol.gestfin.exceptions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * Deve ser utilizada quando for preciso lançar uma exceção Técnica.
 * 
 * @author 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TechnicalFaultType")
public class TechnicalException extends BaseException {

	private static final long serialVersionUID = 8075275395338088829L;

	/**
	 * Construtor para uma <code>TechnicalException</code> com o código de
	 * identificação, mensagem, instrução e detalhe da falha ou erro.
	 * 
	 * @param codigo
	 *            código de identificação da falha ou erro.
	 * @param mensagem
	 *            Mensagem explicativa da falha ou erro.
	 * @param instrucao
	 *            instrução para tratamento da falha ou erro.
	 * @param detalhe
	 *            Detalhe da falha ou erro.
	 */
	public TechnicalException(String codigo, String mensagem, String instrucao, String detalhe) {
		super(codigo, mensagem, instrucao, detalhe);
	}

	/**
	 * Construtor para uma <code>TechnicalException</code> com o código de
	 * identificação, mensagem, instrução, detalhe e causa da falha ou erro.
	 * 
	 * @param codigo
	 *            código de identificação da falha ou erro.
	 * @param mensagem
	 *            Mensagem explicativa da falha ou erro.
	 * @param instrucao
	 *            instrução para tratamento da falha ou erro.
	 * @param causa
	 *            A causa da falha ou erro.
	 */
	public TechnicalException(String codigo, String mensagem, String instrucao, Throwable causa) { //NOSONAR
		super(codigo, mensagem, instrucao, "");
	}

	/**
	 * Construtor para uma <code>BaseException</code> com a mensagem da falha ou
	 * erro.
	 * 
	 * @param mensagem
	 *            Mensagem explicativa da falha ou erro.
	 */
	public TechnicalException(String mensagem) {
		super(mensagem);
	}

	/**
	 * Construtor para uma <code>TechnicalException</code> com a causa da falha
	 * ou erro.
	 * 
	 * @param causa
	 *            A causa da falha ou erro.
	 */
	public TechnicalException(Throwable causa) {
		super(causa);
	}

	/**
	 * Construtor para uma <code>TechnicalException</code> com a mensagem e a
	 * causa da falha ou erro.
	 * 
	 * @param mensagem
	 *            Mensagem explicativa da falha ou erro.
	 * @param causa
	 *            A causa da falha ou erro. Constructs a
	 *            <code>ServiceException</code> with the specified detail
	 *            message and the cause of the throwable.
	 */
	public TechnicalException(String mensagem, Throwable causa) {
		super(mensagem, causa);
	}

}
