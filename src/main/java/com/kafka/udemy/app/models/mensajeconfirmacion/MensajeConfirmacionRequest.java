package com.kafka.udemy.app.models.mensajeconfirmacion;

import java.io.Serializable;

public class MensajeConfirmacionRequest implements Serializable {

	private Long cliente;
	private String mensaje;
	private String plataformaOrigen;
	private String plataformaDestino;


	// GETTERS Y SETTERS

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public String getPlataformaOrigen() {
		return plataformaOrigen;
	}

	public void setPlataformaOrigen(String plataformaOrigen) {
		this.plataformaOrigen = plataformaOrigen;
	}

	public String getPlataformaDestino() {
		return plataformaDestino;
	}

	public void setPlataformaDestino(String plataformaDestino) {
		this.plataformaDestino = plataformaDestino;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return "MensajeConfirmacionRequest{" +
				"cliente=" + cliente +
				", mensaje='" + mensaje + '\'' +
				", plataformaOrigen='" + plataformaOrigen + '\'' +
				", plataformaDestino='" + plataformaDestino + '\'' +
				'}';
	}

}
