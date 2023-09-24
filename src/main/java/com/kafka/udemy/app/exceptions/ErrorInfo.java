package com.kafka.udemy.app.exceptions;

import java.time.ZonedDateTime;

/**
 * Plantilla para la información del error.
 *
 * @noinspection unused
 */
public class ErrorInfo {

	private final String codigo;
	private final Object descripcion;
	private final String tipo;
	private final String severidad;
	private final String path;
	private final ZonedDateTime timestamp;


	// CONSTRUCTOR

	public ErrorInfo(String codigo, Object descripcion, String tipo, String severidad, String path, ZonedDateTime timestamp) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.tipo = tipo;
		this.severidad = severidad;
		this.path = path;
		this.timestamp = timestamp;
	}


	// GETTERS

	public String getCodigo() {
		return codigo;
	}

	public Object getDescripcion() {
		return descripcion;
	}

	public String getTipo() {
		return tipo;
	}

	public String getSeveridad() {
		return severidad;
	}

	public String getPath() {
		return path;
	}

	public ZonedDateTime getTimestamp() {
		return timestamp;
	}

}
