package com.kafka.udemy.app.services.impl;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.kafka.udemy.app.exceptions.ConfirmacionException;
import com.kafka.udemy.app.models.enums.CorresponsalesEnum;
import com.kafka.udemy.app.models.enums.Notificacion;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionRequest;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionResponse;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoRequest;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoResponse;
import com.kafka.udemy.app.services.IKafkaProducerMessageService;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.kafka.udemy.app.constants.ConstantsKafka.TOPIC_CONFIRMACION;
import static com.kafka.udemy.app.constants.ConstantsKafka.TOPIC_RECHAZO;
import static com.kafka.udemy.app.constants.ConstantsKafka.MSJ_CONFIRMACION_KEY;
import static com.kafka.udemy.app.constants.ConstantsKafka.MSJ_RECHAZO_KEY;

@Service
public class KafkaProducerMessageService implements IKafkaProducerMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerMessageService.class);


	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	/**
	 * Zona horaria
	 */
	@Value("${TZ}")
	private String zoneId;



	// MÉTODOS PÚBLICOS

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MensajeConfirmacionResponse enviarMensajeConfirmacion(HttpHeaders headers, MensajeConfirmacionRequest mensajeConfirmacion) throws ConfirmacionException {

		MensajeConfirmacionResponse confirmacionResponse = (MensajeConfirmacionResponse) buildMessageResponse(headers, mensajeConfirmacion);

		String message = new Gson().toJson(confirmacionResponse);

		try {
			LOGGER.info(">>>>> Enviando mensaje a kafka: {}", message);

			ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_CONFIRMACION, MSJ_CONFIRMACION_KEY, message);
			kafkaTemplate.send(producerRecord).whenComplete((result, ex) -> {
				if (ex == null) {
					LOGGER.info("Mensaje enviado [{}] con offset: [{}].", message, result.getRecordMetadata().offset());
				} else {
					LOGGER.error("No se puede enviar el mensaje [{}] debido a : {}", message, ex.getMessage());
				}
			});

		} catch (KafkaException ke) {
			LOGGER.error("Se produjo un error al momento de enviar el mensaje de Confirmacion. {}", ke.getMessage(), ke);
			throw ConfirmacionException.ERR_ENVIO_MENSAJE_CONFIRMACION_KAFKA;
		}

		return confirmacionResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MensajeRechazoResponse enviarMensajeRechazo(HttpHeaders headers, MensajeRechazoRequest mensajeRechazo) {

		MensajeRechazoResponse rechazoResponse = (MensajeRechazoResponse) buildMessageResponse(headers, mensajeRechazo);
		String message = new Gson().toJson(rechazoResponse);

		try {
			LOGGER.info(">>>>> Enviando mensaje a kafka: {}", message);

			ProducerRecord<String, String> producerRecord = new ProducerRecord<>(TOPIC_RECHAZO, MSJ_RECHAZO_KEY, message);
			CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(producerRecord);

			future.whenComplete((result, ex) -> {
				if (ex == null) {
					LOGGER.info("Mensaje enviado [{}] con offset: [{}].", message, result.getRecordMetadata().offset());
				} else {
					LOGGER.error("No se puede enviar el mensaje [{}] debido a : {}", message, ex.getMessage());
				}
			});

		} catch (Exception e) {
			LOGGER.error("Ocurrio un error al momento de enviar el mensaje de Rechazo. ", e);

		}

		return rechazoResponse;
	}



	// MÉTODOS PRIVADOS

	/**
	 * Método auxiliar para construir el Mensaje de Confirmación|Rechazo de Respuesta, y que también se enviará a través de Kafka.
	 *
	 * @param headers Los encabezados de la solicitud.
	 * @param messageRequest El mensaje de confirmación|rechazo a enviar.
	 * @return El mensaje de respuesta construido.
	 */
	private <T> Object buildMessageResponse(HttpHeaders headers, T messageRequest) {

		String idConsumidor = Objects.requireNonNull(headers.get("idConsumidor")).get(0);
		String corresponsal = Objects.requireNonNull(headers.get("corresponsal")).get(0);
		String tipoDeNotificacion = Objects.requireNonNull(headers.get("tipoDeNotificacion")).get(0);

		LocalDateTime currentLocalDateTime = LocalDateTime.now(Clock.system(ZoneId.of(zoneId)));
		ZonedDateTime currentZoneDateTime = ZonedDateTime.now(Clock.system(ZoneId.of(zoneId)));

		Notificacion.TipoDeNotificacion tipo = Notificacion.getTipoDeNotificacion(tipoDeNotificacion);

		// MENSAJE DE CONFIRMACIÓN
		if (messageRequest instanceof MensajeConfirmacionRequest) {
			MensajeConfirmacionRequest mensajeConfirmacion = (MensajeConfirmacionRequest) messageRequest;
			mensajeConfirmacion.setTipoDeNotificacion(tipo);

			return new MensajeConfirmacionResponse(
					idConsumidor,
					CorresponsalesEnum.getCorresponsal(corresponsal),
					currentZoneDateTime,
					mensajeConfirmacion
			);
		}
		// MENSAJE DE RECHAZO
		else {
			MensajeRechazoRequest mensajeRechazo = (MensajeRechazoRequest) messageRequest;
			mensajeRechazo.setTipoDeNotificacion(tipo);

			return new MensajeRechazoResponse(
					idConsumidor,
					CorresponsalesEnum.getCorresponsal(corresponsal),
					currentLocalDateTime,
					mensajeRechazo
			);
		}
	}

}
