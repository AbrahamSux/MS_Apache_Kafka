package com.kafka.udemy.app.repositories;

public interface IMessageRepository {

	void guardarMensajeConfirmacion(String mensaje);

	void guardarMensajeRechazo(String mensaje);

}
