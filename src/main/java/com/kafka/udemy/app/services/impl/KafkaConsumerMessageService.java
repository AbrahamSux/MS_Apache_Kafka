package com.kafka.udemy.app.services.impl;

import com.kafka.udemy.app.repositories.IMessageRepository;
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
	IMessageRepository messageRepository;



	// MÉTODOS PRINCIPALES

	/**
	 * {@inheritDoc}
	 */
	@Override
	//@KafkaListener(topics = {"dev-topic"}, groupId = "consumer")
	public void obtenerMensajeRechazo(String message) {
		LOGGER.info(">> obtenerMensaje( ... ) ");

		LOGGER.info("Received Message: " + message);
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

			messageRepository.guardarMensajeConfirmacion(message.value());
		}
		LOGGER.info("Batch complete.");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	@KafkaListener(
			topics = {"rechazo-topic"},
			containerFactory = "kafkaListenerContainerFactory",
			groupId = "consumer"
	)
	public void obtenerMensajeRechazo(List<ConsumerRecord<String, String>> messages) {
		LOGGER.info(">> obtenerMensajeRechazo( ... )");

		for (ConsumerRecord<String, String> message: messages) {
			LOGGER.info("Rejection message received => Offset= {} Partition= {} Key= {} Value= {}", message.offset(), message.partition(), message.key(), message.value());

			messageRepository.guardarMensajeRechazo(message.value());
		}
		LOGGER.info("Batch complete.");
	}
}
