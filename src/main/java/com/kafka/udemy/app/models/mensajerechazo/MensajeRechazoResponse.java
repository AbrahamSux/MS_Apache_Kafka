package com.kafka.udemy.app.models.mensajerechazo;

import com.google.gson.annotations.JsonAdapter;
import com.kafka.udemy.app.models.enums.CorresponsalesEnum;
import com.kafka.udemy.app.utils.LocalDateTimeTypeAdapter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Respuesta a la solicitud para el mensaje de rechazo recibido.
 */
public class MensajeRechazoResponse implements Serializable {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = 1045456397675618714L;


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
	@JsonAdapter(LocalDateTimeTypeAdapter.class)
	private LocalDateTime fechaEnvio;

	/**
	 * La solicitud recibida que se envía en el mensaje.
	 */
	private MensajeRechazoRequest mensajeRechazo;


	// MÉTODOS

	public MensajeRechazoResponse(String idConsumidor, CorresponsalesEnum corresponsal, LocalDateTime fechaEnvio,
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

	public LocalDateTime getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(LocalDateTime fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public MensajeRechazoRequest getMensajeRechazo() {
		return mensajeRechazo;
	}

	public void setMensajeRechazo(MensajeRechazoRequest mensajeRechazo) {
		this.mensajeRechazo = mensajeRechazo;
	}

}
