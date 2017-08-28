package br.com.nsol.gestfin.exceptions;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

/**
 * Erro genérico usado para encapsular qualquer exceção que tenha instrução e
 * detalhe.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FaultType", propOrder = { "codigo", "mensagem", "instrucao", "detalhe" })
@XmlSeeAlso({ TechnicalException.class, BusinessException.class })
public class BaseException extends Exception {
	private static final long serialVersionUID = 3753376399380176308L;

	/**
	 * Codigo de identificacao do erro
	 */
	private String codigo;

	/**
	 * Mensagem explicativa da falha ou erro.
	 */
	private String mensagem;

	/**
	 * instrução para tratamento da falha ou erro.
	 */
	private String instrucao;

	/**
	 * Detalhe da falha ou erro.
	 */
	private String detalhe;

	/**
	 * Construtor para uma <code>BaseException</code> com o código de
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
	public BaseException(String codigo, String mensagem, String instrucao, String detalhe) {
		super(mensagem);
		this.codigo = codigo;
		this.mensagem = mensagem;
		this.instrucao = instrucao;
		this.detalhe = detalhe;
	}

	private String generetedUniqueCode() {
		return System.currentTimeMillis() + "-" + System.nanoTime();
	}

	/**
	 * Construtor para uma <code>BaseException</code> com a mensagem da falha ou
	 * erro.
	 * 
	 * @param mensagem
	 *            Mensagem explicativa da falha ou erro.
	 */
	public BaseException(String mensagem) {
		super(mensagem);
		this.codigo = generetedUniqueCode();
		this.mensagem = mensagem;
	}

	/**
	 * Construtor para uma <code>BaseException</code> com a causa da falha ou
	 * erro.
	 * 
	 * @param causa
	 *            A causa da falha ou erro.
	 */
	public BaseException(Throwable causa) {
		super(causa);
		this.codigo = generetedUniqueCode();
	}

	/**
	 * Construtor para uma <code>BaseException</code> com a mensagem e a causa
	 * da falha ou erro.
	 * 
	 * @param mensagem
	 *            Mensagem explicativa da falha ou erro.
	 * @param causa
	 *            A causa da falha ou erro. Constructs a
	 *            <code>BaseException</code> with the specified detail message
	 *            and the cause of the throwable.
	 */
	public BaseException(String mensagem, Throwable causa) {
		super(mensagem, causa);
		this.codigo = generetedUniqueCode();
		this.mensagem = mensagem;
	}

	// --------------------------------------------------------- Public Methods
	/**
	 * Obtém o código de identificação da falha ou erro.
	 * 
	 * @return O código de identificação da falha ou erro.
	 */
	public String getCodigo() {
		return codigo;
	}

	/**
	 * Atribui o código de identificação da falha ou erro.
	 * 
	 * @param codigo
	 *            O código de identificação da falha ou erro para atribuir.
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtém a mensagem explicativa da falha ou erro.
	 * 
	 * @return A mensagem explicativa da falha ou erro.
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * Atribui a mensagem explicativa da falha ou erro.
	 * 
	 * @param mensagem
	 *            A mensagem explicativa da falha ou erro para atribuir.
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * Obtém a instrução para tratamento da falha ou erro.
	 * 
	 * @return A instrução para tratamento da falha ou erro.
	 */
	public String getInstrucao() {
		return instrucao;
	}

	/**
	 * Atribui a instrução para tratamento da falha ou erro.
	 * 
	 * @param instrucao
	 *            A instrução para tratamento da falha ou erro para atribuir.
	 */
	public void setInstrucao(String instrucao) {
		this.instrucao = instrucao;
	}

	/**
	 * Obtém o detalhe da falha ou erro.
	 * 
	 * @return o detalhe da falha ou erro.
	 */
	public String getDetalhe() {
		return detalhe;
	}

	/**
	 * Atribui o detalhe da falha ou erro.
	 * 
	 * @param detalhe
	 *            O detalhe da falha o erro para atribuir.
	 */
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (this.codigo == null) {
			return super.toString();
		} else {
			StringBuilder msg = new StringBuilder("Error Code: [").append(this.codigo).append("] ");
			if (super.getMessage() != null) {
				msg.append("\tMensagem: ").append(super.getMessage());
			}
			if (this.getDetalhe() != null && !"".equals(this.getDetalhe())) {
				msg.append("\tDetalhe: ").append(this.getDetalhe());
			}
			if (this.getInstrucao() != null && !"".equals(this.getInstrucao())) {
				msg.append("\tInstrucao: ").append(this.getInstrucao());
			}
			return msg.toString();
		}
	}

}
