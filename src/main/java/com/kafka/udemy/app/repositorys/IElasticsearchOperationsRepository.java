package com.kafka.udemy.app.repositorys;

public interface IElasticsearchOperationsRepository {

	void guardarMensajeConfirmacion(String mensaje);

	void guardarMensajeRechazo(String mensaje);

}
