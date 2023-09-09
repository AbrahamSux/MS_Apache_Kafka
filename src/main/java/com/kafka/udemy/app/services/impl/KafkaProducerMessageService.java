package com.kafka.udemy.app.services.impl;

import com.google.gson.Gson;
import com.kafka.udemy.app.models.enums.CorresponsalesEnum;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionRequest;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionResponse;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoRequest;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoResponse;
import com.kafka.udemy.app.services.IKafkaProducerMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("DuplicatedCode")
@Service
public class KafkaProducerMessageService implements IKafkaProducerMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerMessageService.class);


	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * Topics Kafka.
	 */
	private static final String TOPIC_CONFIRMACION = "confirmacion-topic";
	private static final String TOPIC_RECHAZO = "rechazo-topic";

	/**
	 * Zona horaria
	 */
	@Value("${TZ}")
	private String zoneId;


	/**
	 * {@inheritDoc}
	 */
	@Override
	public MensajeConfirmacionResponse enviarMensajeConfirmacion(HttpHeaders headers, MensajeConfirmacionRequest mensajeConfirmacion) {
		String message = new Gson().toJson(mensajeConfirmacion);

		String idConsumidor = Objects.requireNonNull(headers.get("idConsumidor")).get(0);
		String corresponsal = Objects.requireNonNull(headers.get("corresponsal")).get(0);

		LOGGER.info("Enviando mensaje a kafka: {}", message);
		kafkaTemplate.send(TOPIC_CONFIRMACION, message);

		LocalDateTime currentLocalDateTime = LocalDateTime.now(Clock.system(ZoneId.of(zoneId)));
		ZonedDateTime currentZoneDateTime = ZonedDateTime.now(Clock.system(ZoneId.of(zoneId)));
		OffsetDateTime currentOffsetDateTime = OffsetDateTime.now( Clock.system(ZoneId.of(zoneId)));

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MensajeRechazoResponse enviarMensajeRechazo(HttpHeaders headers, MensajeRechazoRequest mensajeRechazo) {
		String message = new Gson().toJson(mensajeRechazo);

		String idConsumidor = Objects.requireNonNull(headers.get("idConsumidor")).get(0);
		String corresponsal = Objects.requireNonNull(headers.get("corresponsal")).get(0);

		ZonedDateTime currentZoneDateTime = ZonedDateTime.now(Clock.system(ZoneId.of(zoneId)));

		LOGGER.info("Enviando mensaje a kafka: {}", message);
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(TOPIC_RECHAZO, message);

		future.whenComplete((result, ex) -> {
			if (ex == null) {
				LOGGER.info("Mensaje enviado [" + message + "] con offset: [" + result.getRecordMetadata().offset() + "].");
			} else {
				LOGGER.error("No se puede enviar el mensaje [" + message + "] debido a : " + ex.getMessage());
			}
		});

		return new MensajeRechazoResponse(
				idConsumidor,
				CorresponsalesEnum.getCorresponsal(corresponsal),
				currentZoneDateTime,
				mensajeRechazo
		);
	}

}
