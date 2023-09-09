package com.kafka.udemy.app.services.impl;

import com.kafka.udemy.app.services.IKafkaConsumerMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerMessageService implements IKafkaConsumerMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumerMessageService.class);


	/**
	 * {@inheritDoc}
	 */
	@Override
	@KafkaListener(topics = "dev-topic", groupId = "consumer")
	public void obtenerMensaje(String message) {

		LOGGER.info("Received Message in group foo: " + message);
	}

}
