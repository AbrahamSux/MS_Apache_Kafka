package com.kafka.udemy.app.services.impl;

import com.google.gson.Gson;
import com.kafka.udemy.app.models.enums.CorresponsalesEnum;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionRequest;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionResponse;
import com.kafka.udemy.app.services.IKafkaProducerMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;
import java.util.Objects;

@Service
public class KafkaProducerMessageService implements IKafkaProducerMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerMessageService.class);


	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * Topic Kafka.
	 */
	private static final String TOPIC_NAME = "dev-topic";

	/**
	 * Zona horaria
	 */
	@Value("${TZ}")
	private String zoneId;


	/**
	 * {@inheritDoc}
	 */
	@Override
	public MensajeConfirmacionResponse enviarMensaje(HttpHeaders headers, MensajeConfirmacionRequest mensajeConfirmacion) {
		String message = new Gson().toJson(mensajeConfirmacion);

		String idConsumidor = Objects.requireNonNull(headers.get("idConsumidor")).get(0);
		String corresponsal = Objects.requireNonNull(headers.get("corresponsal")).get(0);

		LOGGER.info("Enviando mensaje a kafka: {}", message);
		kafkaTemplate.send(TOPIC_NAME, message);

		LocalDateTime currentLocalDateTime = LocalDateTime.now(Clock.system(ZoneId.of("America/Mexico_City")));
		ZonedDateTime currentZoneDateTime = ZonedDateTime.now(Clock.system(ZoneId.of("America/Mexico_City")));
		OffsetDateTime currentOffsetDateTime = OffsetDateTime.now( Clock.system(ZoneId.of("America/Mexico_City")));

		LOGGER.info("LocalDateTime : " + currentLocalDateTime);
		LOGGER.info("ZonedDateTime : " + currentZoneDateTime);
		LOGGER.info("OffsetDateTime : " + currentOffsetDateTime);

		return new MensajeConfirmacionResponse(
				idConsumidor,
				CorresponsalesEnum.getCorresponsal(corresponsal),
				currentZoneDateTime,
				mensajeConfirmacion
		);
	}

}
