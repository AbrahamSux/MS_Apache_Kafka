package com.kafka.udemy.app.models.elk;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;

/** @noinspection unused*/
@TypeAlias("IndexRejectionMessages")
@Document(indexName = "mensajes-rechazo")
public class MensajeRechazo {

	@Id
	private String id;
	private String idConsumidor;
	private String corresponsal;
	private Long cliente;
	private String motivo;
	private String plataformaOrigen;
	private String plataformaDestino;
	private String correoElectronico;
	private String numeroTelefono;
	private String notificacion;
	private String tipoDeNotificacion;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis)
	private LocalDateTime fechaCreacion;


	// MÉTODOS

	public MensajeRechazo(String id, String idConsumidor, String corresponsal, Long cliente, String motivo,
						  String plataformaOrigen, String plataformaDestino, String correoElectronico, String numeroTelefono,
						  String notificacion, String tipoDeNotificacion, LocalDateTime fechaCreacion) {
		this.id = id;
		this.idConsumidor = idConsumidor;
		this.corresponsal = corresponsal;
		this.cliente = cliente;
		this.motivo = motivo;
		this.plataformaOrigen = plataformaOrigen;
		this.plataformaDestino = plataformaDestino;
		this.correoElectronico = correoElectronico;
		this.numeroTelefono = numeroTelefono;
		this.notificacion = notificacion;
		this.tipoDeNotificacion = tipoDeNotificacion;
		this.fechaCreacion = fechaCreacion;
	}


	// GETTER Y SETTERS

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdConsumidor() {
		return idConsumidor;
	}

	public void setIdConsumidor(String idConsumidor) {
		this.idConsumidor = idConsumidor;
	}

	public String getCorresponsal() {
		return corresponsal;
	}

	public void setCorresponsal(String corresponsal) {
		this.corresponsal = corresponsal;
	}

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

	public String getNotificacion() {
		return notificacion;
	}

	public void setNotificacion(String notificacion) {
		this.notificacion = notificacion;
	}

	public String getTipoDeNotificacion() {
		return tipoDeNotificacion;
	}

	public void setTipoDeNotificacion(String tipoDeNotificacion) {
		this.tipoDeNotificacion = tipoDeNotificacion;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
