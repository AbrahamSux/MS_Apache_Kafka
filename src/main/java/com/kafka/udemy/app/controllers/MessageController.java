package com.kafka.udemy.app.controllers;

import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionRequest;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionResponse;
import com.kafka.udemy.app.services.IKafkaProducerMessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.kafka.udemy.app.constants.ConstantsMessages.MESSAGE_REQUIRED;

@RestController
@RequestMapping("api/v1/message")
public class MessageController {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);


	@Autowired
	private IKafkaProducerMessageService iKafkaProducerMessageService;


	@PostMapping(path = "/confirmacion",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> enviarMensajeConfirmacion(
			@Valid
			@NotNull(message = MESSAGE_REQUIRED)
			@RequestBody MensajeConfirmacionRequest mensajeConfirmacion,
			@RequestHeader HttpHeaders headers) {
		LOGGER.info("Mensaje Confirmacion: {}", mensajeConfirmacion);

		MensajeConfirmacionResponse response = iKafkaProducerMessageService.enviarMensaje(headers, mensajeConfirmacion);
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	@PostMapping("/rechazo")
	@ResponseStatus(value = HttpStatus.OK)
	public void enviarMensajeRechazo(@RequestBody MensajeConfirmacionRequest mensajeConfirmacion) {

		LOGGER.info("Mensaje Rechazo: {}", mensajeConfirmacion);
	}

}
