package com.kafka.udemy.app.services;

public interface IKafkaConsumerMessageService {

	/**
	 * Para utilizar el listener y obtener los mensajes.
	 *
	 * @param message El mensaje obtenido.
	 */
	@SuppressWarnings("unused")
	void obtenerMensaje(String message);

}
