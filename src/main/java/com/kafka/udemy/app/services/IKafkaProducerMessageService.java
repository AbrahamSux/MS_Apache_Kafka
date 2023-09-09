package com.kafka.udemy.app.services;

import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionRequest;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionResponse;
import org.springframework.http.HttpHeaders;

public interface IKafkaProducerMessageService {

	/**
	 * Utilizado para enviar el mensaje de confirmación.
	 *
	 * @param headers Los encabezados de la solicitud.
	 * @param mensajeConfirmacion El mensaje de confirmación a enviar.
	 * @return La respuesta a la solicitud.
	 */
	MensajeConfirmacionResponse enviarMensaje(HttpHeaders headers, MensajeConfirmacionRequest mensajeConfirmacion);

}
