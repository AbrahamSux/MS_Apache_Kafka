package com.kafka.udemy.app.models.mensajeconfirmacion;

import com.kafka.udemy.app.models.enums.CorresponsalesEnum;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;

public class MensajeConfirmacionResponse implements Serializable {

	private String idConsumidor;
	private CorresponsalesEnum corresponsal;
	private ZonedDateTime fechaEnvio;
	private MensajeConfirmacionRequest mensajeConfirmacion;


	// MÉTODOS

	public MensajeConfirmacionResponse() {
	}

	public MensajeConfirmacionResponse(String idConsumidor, CorresponsalesEnum corresponsal, ZonedDateTime fechaEnvio,
									   MensajeConfirmacionRequest mensajeConfirmacion) {
		this.idConsumidor = idConsumidor;
		this.corresponsal = corresponsal;
		this.fechaEnvio = fechaEnvio;
		this.mensajeConfirmacion = mensajeConfirmacion;
	}


	// GETTERS Y SETTERS

	public String getIdConsumidor() {
		return idConsumidor;
	}

	public void setIdConsumidor(String idConsumidor) {
		this.idConsumidor = idConsumidor;
	}

	public CorresponsalesEnum getCorresponsal() {
		return corresponsal;
	}

	public void setCorresponsal(CorresponsalesEnum corresponsal) {
		this.corresponsal = corresponsal;
	}

	public ZonedDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(ZonedDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public MensajeConfirmacionRequest getMensajeConfirmacion() {
		return mensajeConfirmacion;
	}

	public void setMensajeConfirmacion(MensajeConfirmacionRequest mensajeConfirmacion) {
		this.mensajeConfirmacion = mensajeConfirmacion;
	}

}
