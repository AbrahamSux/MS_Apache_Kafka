package com.kafka.udemy.app.models.elk;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.ZonedDateTime;

@SuppressWarnings("unused")
@TypeAlias("IndexConfirmationMessages")
@Document(indexName = "mensajes-confirmacion")
public class MensajeConfirmacion {

	@Id
	private String id;
	private Long cliente;
	private String mensaje;
	private String plataformaOrigen;
	private String plataformaDestino;

	@Field(type = FieldType.Date, format = DateFormat.date_time)
	private ZonedDateTime fechaCreacion;


	// MÉTODOS

	public MensajeConfirmacion(String id, Long cliente, String mensaje, String plataformaOrigen, String plataformaDestino, ZonedDateTime fechaCreacion) {
		this.id = id;
		this.cliente = cliente;
		this.mensaje = mensaje;
		this.plataformaOrigen = plataformaOrigen;
		this.plataformaDestino = plataformaDestino;
		this.fechaCreacion = fechaCreacion;
	}


	// GETTER Y SETTERS

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public ZonedDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(ZonedDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

}
