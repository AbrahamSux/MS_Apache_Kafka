package com.kafka.udemy.app.repositories;

public interface IElasticsearchOperationsRepository {

	void guardarMensajeConfirmacion(String mensaje);

	void guardarMensajeRechazo(String mensaje);

}
