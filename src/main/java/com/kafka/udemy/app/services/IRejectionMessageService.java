package com.kafka.udemy.app.services;

import com.kafka.udemy.app.exceptions.RechazoException;
import org.springframework.http.ResponseEntity;

public interface IRejectionMessageService {

	ResponseEntity<?> getMensajeRechazoPorId(String idDocument) throws RechazoException;

	ResponseEntity<?> getMensajeRechazoPorOrigenAndTipoNotificacion(String plataformaOrigen, String tipoDeNotificacion) throws RechazoException;

}
