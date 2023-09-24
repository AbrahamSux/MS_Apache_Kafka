package com.kafka.udemy.app.models.mensajeconfirmacion;

import com.google.gson.annotations.JsonAdapter;
import com.kafka.udemy.app.models.enums.CorresponsalesEnum;
import com.kafka.udemy.app.utils.ZonedDateTimeTypeAdapter;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * Respuesta a la solicitud para el mensaje de confirmación recibido.
 */
public class MensajeConfirmacionResponse implements Serializable {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = -6830731681849445244L;


	/**
	 * El identificador del consumidor.
	 */
	private String idConsumidor;

	/**
	 * La corresponsalía.
	 */
	private CorresponsalesEnum corresponsal;

	/**
	 * La fecha de envío del mensaje.
	 */
	@JsonAdapter(ZonedDateTimeTypeAdapter.class)
	private ZonedDateTime fechaEnvio;

	/**
	 * La solicitud recibida que se envía en el mensaje.
	 */
	private MensajeConfirmacionRequest mensajeConfirmacion;


	// MÉTODOS

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
