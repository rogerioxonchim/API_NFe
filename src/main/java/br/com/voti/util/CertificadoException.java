package br.com.voti.util;

/**
 * Exceção a ser lançada na ocorrência de falhas provenientes do Certificado.
 * 
 * @author Samuel Oliveira - samuk.exe@hotmail.com - www.samuelweb.com.br
 */
public class CertificadoException extends Exception {

	private static final long serialVersionUID = -5054900660251852366L;
	
	String message;
	
	/**
	 * Construtor da classe.
	 * 
	 * @param e
	 */
	public CertificadoException(Throwable e) {
		super(e);
	}

	
	/**
	 * Construtor da classe.
	 * 
	 * @param message
	 */
	public CertificadoException(String message) {
		this((Throwable) null);
		this.message = message;
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

	
}