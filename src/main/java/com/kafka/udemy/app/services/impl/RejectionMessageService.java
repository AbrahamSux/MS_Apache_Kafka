package com.kafka.udemy.app.services.impl;

import com.kafka.udemy.app.exceptions.RechazoException;
import com.kafka.udemy.app.models.elk.MensajeRechazo;
import com.kafka.udemy.app.repositories.RejectionMessageRepository;
import com.kafka.udemy.app.services.IRejectionMessageService;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class RejectionMessageService implements IRejectionMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RejectionMessageService.class);

	@Autowired
	RejectionMessageRepository rejectionMessageRepository;


	// MÉTODOS PRINCIPALES

	@Override
	public ResponseEntity<?> getMensajeRechazoPorId(String idDocument) throws RechazoException {

		try {
			Optional<MensajeRechazo> mensajeRechazo = rejectionMessageRepository.findById(idDocument);

			if (mensajeRechazo.isPresent()) {
				return ResponseEntity.status(HttpStatus.OK).body(mensajeRechazo);
			}

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					Collections.singletonMap("mensaje", "No se encontr\u00f3 registro para el ID: [ " + idDocument + " ].")
			);
		} catch (Exception exception) {
			LOGGER.error("Se produjo un error al recuperar el Mensaje de Rechazo para el ID: [{}]. ", idDocument, exception);
			throw RechazoException.ERR_OBTENER_MENSAJE_RECHAZO_POR_ID_ELK;
		}
	}

	@Override
	public ResponseEntity<?> getMensajeRechazoPorOrigenAndTipoNotificacion(String plataformaOrigen, String tipoDeNotificacion) throws RechazoException {

		List<MensajeRechazo> mensajeRechazoList;
		try {
			if (Strings.isNotBlank(tipoDeNotificacion)) {
				mensajeRechazoList = rejectionMessageRepository.findByPlataformaOrigenAndTipoDeNotificacion(plataformaOrigen, tipoDeNotificacion);
			} else {
				mensajeRechazoList = rejectionMessageRepository.findByPlataformaOrigen(plataformaOrigen);
			}

			if (mensajeRechazoList != null && !mensajeRechazoList.isEmpty()) {
				return ResponseEntity.status(HttpStatus.OK).body(mensajeRechazoList);
			}

			String msgValue = "No se encontr\u00f3 registro para el Origen: [ " + plataformaOrigen + " ] " +
					"y Tipo de Notificaci\u00f3n: [ " + tipoDeNotificacion + " ].";

			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("mensaje", msgValue));
		} catch (Exception exception) {
			LOGGER.error("Se produjo un error al recuperar el Mensaje de Rechazo para el Origen: [{}]. ", plataformaOrigen, exception);
			throw RechazoException.ERR_OBTENER_MENSAJE_RECHAZO_POR_ORIGEN_ELK;
		}
	}

}
