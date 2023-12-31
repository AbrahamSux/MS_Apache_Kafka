package com.kafka.udemy.app.exceptions;

import com.kafka.udemy.app.exceptions.enums.SeveridadError;
import com.kafka.udemy.app.exceptions.enums.TipoError;
import org.springframework.http.HttpStatus;

/**
 * Excepciones para la funcionalidad de envío de los mensajes de rechazo.
 *
 * @author Abraham Juárez de la cruz
 * @since 26/09/2023
 */
public class RechazoException extends BaseException {

	/**
	 * Versión de serialización.
	 */
	private static final long serialVersionUID = -7859398822402368960L;


	/**
	 * Constructor para la respuesta de error en la aplicación referente al proceso de Mensajes de Rechazo.
	 */
	protected RechazoException(String codigoError, String descripcionError, String tipoError, String severidad, HttpStatus status) {
		super(codigoError, descripcionError, tipoError, severidad, status);
	}


	// PROPAGACIÓN DE EXCEPCIONES

	public static final RechazoException ERR_ENVIO_MENSAJE_RECHAZO_KAFKA = new RechazoException(
			"API-MESSAGES-020",
			"Error al momento de enviar el mensaje de rechazo a tr\u00e9ves de Kafka.",
			TipoError.CONECTIVIDAD.getTipo(),
			SeveridadError.SERVER_ERROR.getSeveridad(),
			HttpStatus.INTERNAL_SERVER_ERROR
	);

	public static final RechazoException ERR_OBTENER_MENSAJE_RECHAZO_POR_ID_ELK = new RechazoException(
			"API-MESSAGES-021",
			"Error al momento de obtener el Mensaje de Rechazo por ID de ELK.",
			TipoError.CONECTIVIDAD.getTipo(),
			SeveridadError.CLIENT_ERROR.getSeveridad(),
			HttpStatus.INTERNAL_SERVER_ERROR
	);

	public static final RechazoException ERR_OBTENER_MENSAJE_RECHAZO_POR_ORIGEN_ELK = new RechazoException(
			"API-MESSAGES-022",
			"Error al momento de obtener el Mensaje de Rechazo por Origen y Tipo de Notificaci\u00f3n de ELK.",
			TipoError.CONECTIVIDAD.getTipo(),
			SeveridadError.CLIENT_ERROR.getSeveridad(),
			HttpStatus.INTERNAL_SERVER_ERROR
	);

}
