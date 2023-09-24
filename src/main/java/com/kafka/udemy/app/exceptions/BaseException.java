package com.kafka.udemy.app.exceptions;

import org.springframework.http.HttpStatus;

/**
 * Base para la propagación de las excepciones en la aplicación.
 */
public abstract class BaseException extends Exception {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = 5919963011345853555L;


	/**
	 * El código asociado al error.
	 */
	protected final String codigoError;

	/**
	 * La descripción del error.
	 */
	protected final String descripcionError;

	/**
	 * El tipo de error.
	 */
	protected final String tipoError;

	/**
	 * La severidad del error.
	 */
	protected final String severidad;

	/**
	 * El estatus de error.
	 */
	protected final HttpStatus status;


	// MÉTODOS

	/**
	 * Constructor para la respuesta de error en la aplicación.
	 */
	protected BaseException(String codigoError, String descripcionError, String tipoError, String severidad, HttpStatus status) {
		this.codigoError = codigoError;
		this.descripcionError = descripcionError;
		this.tipoError = tipoError;
		this.severidad = severidad;
		this.status = status;
	}


	// GETTERS

	public String getCodigoError() {
		return codigoError;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public String getTipoError() {
		return tipoError;
	}

	public String getSeveridad() {
		return severidad;
	}

	public HttpStatus getStatus() {
		return status;
	}

}
