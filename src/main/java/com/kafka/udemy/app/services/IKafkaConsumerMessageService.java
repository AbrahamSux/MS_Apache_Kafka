package com.kafka.udemy.app.services;

import java.util.List;

public interface IKafkaConsumerMessageService {

	/**
	 * Para utilizar el listener y obtener los mensajes.
	 *
	 * @param message El mensaje obtenido.
	 */
	@SuppressWarnings("unused")
	void obtenerMensaje(String message);

	/**
	 * Para utilizar el listener y obtener los mensajes por lotes.
	 *
	 * @param messages Los mensajes obtenidos.
	 */
	@SuppressWarnings("unused")
	void obtenerMensaje(List<String> messages);

}
