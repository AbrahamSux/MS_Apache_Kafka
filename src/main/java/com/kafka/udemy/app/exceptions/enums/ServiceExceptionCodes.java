package com.kafka.udemy.app.exceptions.enums;

public enum ServiceExceptionCodes {

	API_MESSAGES_400("API-MESSAGES-400", "Petición mal formada."),
	API_MESSAGES_500("API-MESSAGES-500", "Error Interno del Servidor.");


	/**
	 * Código de error de la excepción.
	 */
	private String codigoError;

	/**
	 * Mensaje de error de la excepción.
	 */
	private String mensaje;


	// MÉTODOS

	/**
	 * Constructor.
	 *
	 * @param codigoError El código de error de la excepción.
	 * @param mensaje El mensaje de error de la excepción.
	 */
	ServiceExceptionCodes(String codigoError, String mensaje) {
		this.codigoError = codigoError;
		this.mensaje = mensaje;
	}


	// GETTERS Y SETTERS

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

}
