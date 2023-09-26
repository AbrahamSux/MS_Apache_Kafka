package com.kafka.udemy.app.services.impl;

import com.kafka.udemy.app.repositories.IElasticsearchOperationsRepository;
import com.kafka.udemy.app.services.IKafkaConsumerMessageService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KafkaConsumerMessageService implements IKafkaConsumerMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerMessageService.class);


	@Autowired
	IElasticsearchOperationsRepository elasticsearchOperationsService;


	// CONSTANTES
	private static final String MENSAJE_CONFIRMACION = "mensajes-confirmacion";


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
			topics = {"confirmacion-topic"},
			containerFactory = "kafkaListenerContainerFactory",
			groupId = "consumer"
	)
	public void obtenerMensajeConfirmacion(List<ConsumerRecord<String, String>> messages) {
		LOGGER.info(">> obtenerMensajeConfirmacion( ... )");

		for (ConsumerRecord<String, String> message: messages) {
			LOGGER.info("Confirmation message received => Offset= {} Partition= {} Key= {} Value= {}", message.offset(), message.partition(), message.key(), message.value());

			elasticsearchOperationsService.guardarMensajeConfirmacion(message.value());
		}
		LOGGER.info("Batch complete.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@KafkaListener(
			id ="autoStartup", autoStartup ="true",
			topics = {"dev-topic", "rechazo-topic"},
			containerFactory = "kafkaListenerContainerFactory",
			groupId = "consumer",
			properties = {
					"max.poll.interval.ms:400",
					"max.poll.records:5"
			}
	)
	public void obtenerMensaje(List<ConsumerRecord<String, String>> messages) {
		LOGGER.info("Start reading messages");

		for (ConsumerRecord<String, String> message: messages) {
			LOGGER.info("Received Message => Offset= {} Partition= {} Key= {} Value= {}", message.offset(), message.partition(), message.key(), message.value());

			if (MENSAJE_CONFIRMACION.equals(message.key())) {
				//elasticsearchOperationsService.guardarMensajeConfirmacion(message.value());
			}

		}
		LOGGER.info("Batch complete.");
	}

}
