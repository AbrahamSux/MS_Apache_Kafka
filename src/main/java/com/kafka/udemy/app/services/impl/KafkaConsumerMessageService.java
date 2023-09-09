package com.kafka.udemy.app.services.impl;

import com.kafka.udemy.app.services.IKafkaConsumerMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumerMessageService implements IKafkaConsumerMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerMessageService.class);


	// - max.poll.interval.ms: = Define el tiempo entre una ejecución y otra para el método pool.
	// - max.poll.records: Define el máximo número de registros a devolver por el método pool.


	/**
	 * {@inheritDoc}
	 */
	@Override
	//@KafkaListener(topics = {"dev-topic", "confirmacion-topic", "rechazo-topic"}, groupId = "consumer")
	public void obtenerMensaje(String message) {
		// Intentionally empty
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@KafkaListener(
			topics = {"dev-topic", "confirmacion-topic", "rechazo-topic"},
			containerFactory = "kafkaListenerContainerFactory",
			groupId = "consumer",
			properties = {
					"max.poll.interval.ms:400",
					"max.poll.records:5"
	})
	public void obtenerMensaje(List<String> messages) {

		for (String message: messages) {
			LOGGER.info("Received Message : {}", message);
		}
		LOGGER.info("Batch complete.");
	}

}
