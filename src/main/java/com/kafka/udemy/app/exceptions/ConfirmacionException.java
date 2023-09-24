package com.kafka.udemy.app.exceptions;

import com.kafka.udemy.app.exceptions.enums.SeveridadError;
import com.kafka.udemy.app.exceptions.enums.TipoError;
import org.springframework.http.HttpStatus;

/**
 * Excepciones para la funcionalidad de envío de los mensajes de confirmación.
 *
 * @author Abraham Juárez de la cruz
 * @since 22/09/2023
 */
public class ConfirmacionException extends BaseException {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = -4520899707048181903L;


	/**
	 * Constructor.
	 */
	public ConfirmacionException(String codigoError, String descripcionError, String tipoError, String severidad, HttpStatus status) {
		super(codigoError, descripcionError, tipoError, severidad, status);
	}


	// PROPAGACIÓN DE EXCEPCIONES

	public static final ConfirmacionException ERR_ENVIO_MENSAJE_CONFIRMACION_KAFKA = new ConfirmacionException(
			"API-MESSAGES-001",
			"Error al momento de enviar el mensaje de confirmaci\u00f3n a tr\u00e9ves de Kafka.",
			TipoError.CONECTIVIDAD.getTipo(),
			SeveridadError.SERVER_ERROR.getSeveridad(),
			HttpStatus.INTERNAL_SERVER_ERROR
	);

}
