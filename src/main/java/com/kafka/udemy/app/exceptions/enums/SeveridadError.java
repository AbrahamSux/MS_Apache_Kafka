package com.kafka.udemy.app.exceptions.enums;

public enum SeveridadError {

	INFORMATIONAL("1"),
	APPLICATION_ERROR("2"),
	CLIENT_ERROR("3"),
	SERVER_ERROR("4");


	/**
	 * La severidad del error.
	 */
	private final String severidad;


	// MÉTODOS

	/**
	 * Constructor.
	 */
	SeveridadError(String severidad) {
		this.severidad = severidad;
	}


	// GETTERS

	public String getSeveridad() {
		return severidad;
	}

}
