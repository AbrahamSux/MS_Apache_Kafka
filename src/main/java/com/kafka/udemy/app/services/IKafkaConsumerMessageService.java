package com.kafka.udemy.app.services;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.List;

public interface IKafkaConsumerMessageService {

	/**
	 * Para utilizar el listener y obtener los mensajes.
	 *
	 * @param message El mensaje obtenido.
	 */
	@SuppressWarnings("unused")
	void obtenerMensajeRechazo(String message);

	/**
	 * Utilizado para obtener los mensajes de confirmación (por lotes) de la cola de mensajes.
	 *
	 * @param messages Los mensajes obtenidos.
	 * @noinspection unused
	 */
	void obtenerMensajeConfirmacion(List<ConsumerRecord<String, String>> messages);

	/**
	 * Para utilizar el listener y obtener los mensajes por lotes.
	 * <br>
	 * <br> - max.poll.interval.ms: = Define el tiempo entre una ejecución y otra (para el método pool).
	 * <br> - max.poll.records: Define el máximo número de registros que se traerán en una ejecución (a devolver por el método pool).
	 *
	 * @param messages Los mensajes obtenidos.
	 * @noinspection unused
	 */
	void obtenerMensajeRechazo(List<ConsumerRecord<String, String>> messages);

}
