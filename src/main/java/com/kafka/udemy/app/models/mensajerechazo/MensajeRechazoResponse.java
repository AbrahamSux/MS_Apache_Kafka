package com.kafka.udemy.app.models.mensajerechazo;

import com.kafka.udemy.app.models.enums.CorresponsalesEnum;

import java.io.Serializable;
import java.time.ZonedDateTime;

public class MensajeRechazoResponse implements Serializable {

	private String idConsumidor;
	private CorresponsalesEnum corresponsal;
	private ZonedDateTime fechaEnvio;
	private MensajeRechazoRequest mensajeRechazo;


	// MÉTODOS

	public MensajeRechazoResponse(String idConsumidor, CorresponsalesEnum corresponsal, ZonedDateTime fechaEnvio,
								  MensajeRechazoRequest mensajeRechazo) {
		this.idConsumidor = idConsumidor;
		this.corresponsal = corresponsal;
		this.fechaEnvio = fechaEnvio;
		this.mensajeRechazo = mensajeRechazo;
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

	public MensajeRechazoRequest getMensajeRechazo() {
		return mensajeRechazo;
	}

	public void setMensajeRechazo(MensajeRechazoRequest mensajeRechazo) {
		this.mensajeRechazo = mensajeRechazo;
	}

}
