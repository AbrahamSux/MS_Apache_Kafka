package com.kafka.udemy.app.models.mensajerechazo;

import java.io.Serializable;

public class MensajeRechazoRequest implements Serializable {

	private Long cliente;
	private String motivo;
	private String plataformaOrigen;
	private String plataformaDestino;


	// GETTERS Y SETTERS

	public Long getCliente() {
		return cliente;
	}

	public void setCliente(Long cliente) {
		this.cliente = cliente;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
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
		return "MensajeRechazoRequest{" +
				"cliente=" + cliente +
				", motivo='" + motivo + '\'' +
				", plataformaOrigen='" + plataformaOrigen + '\'' +
				", plataformaDestino='" + plataformaDestino + '\'' +
				'}';
	}
}
