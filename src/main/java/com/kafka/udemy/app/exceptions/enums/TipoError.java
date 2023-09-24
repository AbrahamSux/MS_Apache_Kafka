package com.kafka.udemy.app.exceptions.enums;

public enum TipoError {

	SOLICITUD("Error en la solicitud"),
	APLICACION("Error en la aplicaci\u00f3n"),
	CONECTIVIDAD("Error de conectividad"),
	COMUNICACION("Error de Comunicaci\u00f3n");


	/**
	 * El tipo de eror.
	 */
	private final String tipo;


	// MÉTODOS

	/**
	 * Constructor.
	 */
	TipoError(String tipo) {
		this.tipo = tipo;
	}


	// GETTERS

	public String getTipo() {
		return tipo;
	}

}
