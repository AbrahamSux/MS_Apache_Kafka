package com.kafka.udemy.app.services;

import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionRequest;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionResponse;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoRequest;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoResponse;
import org.springframework.http.HttpHeaders;

public interface IKafkaProducerMessageService {

	/**
	 * Utilizado para enviar el mensaje de confirmación.
	 *
	 * @param headers Los encabezados de la solicitud.
	 * @param mensajeConfirmacion El mensaje de confirmación a enviar.
	 * @return La respuesta a la solicitud.
	 */
	MensajeConfirmacionResponse enviarMensajeConfirmacion(HttpHeaders headers, MensajeConfirmacionRequest mensajeConfirmacion);

	/**
	 * Utilizado para enviar el mensaje de rechazo.
	 *
	 * @param headers Los encabezados de la solicitud.
	 * @param mensajeRechazo El mensaje de rechazo a enviar.
	 * @return La respuesta a la solicitud.
	 */
	MensajeRechazoResponse enviarMensajeRechazo(HttpHeaders headers, MensajeRechazoRequest mensajeRechazo);

}
