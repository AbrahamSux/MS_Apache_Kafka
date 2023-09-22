package com.kafka.udemy.app.services.impl;

import com.google.gson.Gson;
import com.kafka.udemy.app.models.enums.CorresponsalesEnum;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionRequest;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionResponse;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoRequest;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoResponse;
import com.kafka.udemy.app.services.IKafkaProducerMessageService;
import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@SuppressWarnings("DuplicatedCode")
@Service
public class KafkaProducerMessageService implements IKafkaProducerMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerMessageService.class);


	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Autowired
	private KafkaListenerEndpointRegistry registry;

	@Autowired
	private MeterRegistry meterRegistry;

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


	@Scheduled(fixedDelay = 15000, initialDelay = 3000)
	public void consumirMensajes() {

		LOGGER.info("Empezando a consumir mensajes . . .");
		/*registry.getListenerContainer("autoStartup").start();
		realizarTiempoMuerto(10000L);
		registry.getListenerContainer("autoStartup").stop();*/
		LOGGER.info("Termina el consumo de mensajes.");
	}

	/**
	 * Mostrar métricas.
	 */
	//@Scheduled(fixedDelay = 10000, initialDelay = 5000)
	public void messageCountMetric() {

		List<Meter> meters = meterRegistry.getMeters();
		LOGGER.info("Meters Size : {}", meters.size());

		for (Meter meter : meters) {
			LOGGER.info("Metric {} ", meter.getId());
		}

		//double count = meterRegistry.get("kafka.producer.record.send.total").functionCounter().count();
		//LOGGER.info("Count {} ",count);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MensajeConfirmacionResponse enviarMensajeConfirmacion(HttpHeaders headers, MensajeConfirmacionRequest mensajeConfirmacion) {
		String message = new Gson().toJson(mensajeConfirmacion);

		String idConsumidor = Objects.requireNonNull(headers.get("idConsumidor")).get(0);
		String corresponsal = Objects.requireNonNull(headers.get("corresponsal")).get(0);

		LOGGER.info("Enviando mensaje a kafka: {}", message);
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_CONFIRMACION, "mensajes-confirmacion", message);
		kafkaTemplate.send(producerRecord);
		realizarTiempoMuerto(1000L);


		ZonedDateTime currentZoneDateTime = ZonedDateTime.now(Clock.system(ZoneId.of(zoneId)));
		LOGGER.info("ZonedDateTime : {}", currentZoneDateTime);

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
		ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_RECHAZO, "mensajes-rechazo", message);
		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(producerRecord);

		future.whenComplete((result, ex) -> {
			if (ex == null) {
				LOGGER.info("Mensaje enviado [{}] con offset: [{}].", message, result.getRecordMetadata().offset());
			} else {
				LOGGER.error("No se puede enviar el mensaje [{}] debido a : {}", message, ex.getMessage());
			}
		});

		return new MensajeRechazoResponse(
				idConsumidor,
				CorresponsalesEnum.getCorresponsal(corresponsal),
				currentZoneDateTime,
				mensajeRechazo
		);
	}


	// MÉTODOS PRIVADOS

	/**
	 * Método auxiliar utilizado para realizar un tiempo muerto.
	 *
	 * @param milliseconds El tiempo muerto a realizar en mili-segundos.
	 */
	private void realizarTiempoMuerto(Long milliseconds) {

		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException ie) {
			LOGGER.error("Error al realizar el tiempo muerto. ", ie);
			// Restaurar estado interrumpido...
			Thread.currentThread().interrupt();
		}
	}

}
