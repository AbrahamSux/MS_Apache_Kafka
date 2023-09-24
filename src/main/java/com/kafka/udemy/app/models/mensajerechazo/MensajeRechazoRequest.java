package com.kafka.udemy.app.models.mensajerechazo;

import com.kafka.udemy.app.models.enums.Notificacion;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

/**
 * Solicitud para un mensaje de rechazo.
 */
public class MensajeRechazoRequest implements Serializable {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = -4481752651561850824L;


	/**
	 * El identificador del cliente.
	 */
	@NotNull
	private Long cliente;

	/**
	 * La descripción del mensaje.
	 */
	@NotNull
	@NotBlank
	private String motivo;

	/**
	 * La plataforma de origen.
	 */
	@NotNull
	@NotBlank
	private String plataformaOrigen;

	/**
	 * La plataforma de destino.
	 */
	@NotNull
	@NotBlank
	private String plataformaDestino;

	/**
	 * El correo electrónico del cliente.
	 */
	@NotNull
	@NotBlank
	@Email
	private String correoElectronico;

	/**
	 * El número telefónico del cliente.
	 */
	@NotNull
	@NotBlank
	private String numeroTelefono;

	/**
	 * La notificación que se realiza.
	 */
	private Notificacion notificacion;

	/**
	 * El tipo de notificación que se realizará.
	 */
	private Notificacion.TipoDeNotificacion tipoDeNotificacion;


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

	public String getCorreoElectronico() {
		return correoElectronico;
	}

	public void setCorreoElectronico(String correoElectronico) {
		this.correoElectronico = correoElectronico;
	}

	public String getNumeroTelefono() {
		return numeroTelefono;
	}

	public void setNumeroTelefono(String numeroTelefono) {
		this.numeroTelefono = numeroTelefono;
	}

	public Notificacion getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(Notificacion notificacion) {
		this.notificacion = notificacion;
	}

	public Notificacion.TipoDeNotificacion getTipoDeNotificacion() {
		return tipoDeNotificacion;
	}

	public void setTipoDeNotificacion(Notificacion.TipoDeNotificacion tipoDeNotificacion) {
		this.tipoDeNotificacion = tipoDeNotificacion;
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
				", correoElectronico='" + correoElectronico + '\'' +
				", numeroTelefono='" + numeroTelefono + '\'' +
				", notificacion=" + notificacion +
				", tipoDeNotificacion=" + tipoDeNotificacion +
				'}';
	}

}
