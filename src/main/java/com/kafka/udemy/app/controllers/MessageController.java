package com.kafka.udemy.app.controllers;

import com.kafka.udemy.app.exceptions.ConfirmacionException;
import com.kafka.udemy.app.exceptions.RechazoException;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionRequest;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionResponse;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoRequest;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoResponse;
import com.kafka.udemy.app.services.IKafkaProducerMessageService;
import com.kafka.udemy.app.services.IRejectionMessageService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


	private final IRejectionMessageService iRejectionMessageService;
	private final IKafkaProducerMessageService iKafkaProducerMessageService;


	// CONSTRUCTOR

	/**
	 * Inyección por constructor.
	 */
	public MessageController(final IRejectionMessageService iRejectionMessageService,
							 final IKafkaProducerMessageService iKafkaProducerMessageService) {
		this.iRejectionMessageService = iRejectionMessageService;
		this.iKafkaProducerMessageService = iKafkaProducerMessageService;
	}


	// SERVICIOS DISPONIBLES DEL API.

	@PostMapping(path = "/confirmacion",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> enviarMensajeConfirmacion(
			@Valid
			@NotNull(message = MESSAGE_REQUIRED)
			@RequestBody MensajeConfirmacionRequest mensajeConfirmacion,
			@RequestHeader HttpHeaders headers) throws ConfirmacionException {
		LOGGER.info("Mensaje Confirmacion: {}", mensajeConfirmacion);

		MensajeConfirmacionResponse response = iKafkaProducerMessageService.enviarMensajeConfirmacion(headers, mensajeConfirmacion);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping(path = "/rechazo",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> enviarMensajeRechazo(
			@Valid
			@NotNull(message = MESSAGE_REQUIRED)
			@RequestBody MensajeRechazoRequest mensajeRechazo,
			@RequestHeader HttpHeaders headers) throws RechazoException {
		LOGGER.info("Mensaje Rechazo: {}", mensajeRechazo);

		MensajeRechazoResponse response = iKafkaProducerMessageService.enviarMensajeRechazo(headers, mensajeRechazo);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@GetMapping(path = "/rechazo/document",
			produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> obtenerMensajeRechazoPorId(
			@RequestParam(value = "idDocument") String idDocument) throws RechazoException {
		LOGGER.info(">> obtenerMensajeRechazoPorId( {} )", idDocument);

		return iRejectionMessageService.getMensajeRechazoPorId(idDocument);
	}

	@GetMapping(path = "/rechazo",
			produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ResponseEntity<?> obtenerMensajeRechazoPorOrigen(
			@RequestParam(value = "origen") String origen,
			@RequestParam(value = "tipoNotificacion", required = false) String tipoNotificacion) throws RechazoException {
		LOGGER.info(">> obtenerMensajeRechazoPorOrigen( {}, {} )", origen, tipoNotificacion);

		return iRejectionMessageService.getMensajeRechazoPorOrigenAndTipoNotificacion(origen, tipoNotificacion);
	}

}
