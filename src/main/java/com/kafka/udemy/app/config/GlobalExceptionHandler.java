package com.kafka.udemy.app.config;

import com.kafka.udemy.app.exceptions.BaseException;
import com.kafka.udemy.app.exceptions.ConfirmacionException;
import com.kafka.udemy.app.exceptions.ErrorInfo;
import com.kafka.udemy.app.exceptions.RechazoException;
import com.kafka.udemy.app.exceptions.enums.SeveridadError;
import com.kafka.udemy.app.exceptions.enums.TipoError;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.kafka.udemy.app.exceptions.enums.ServiceExceptionCodes.API_MESSAGES_400;
import static com.kafka.udemy.app.exceptions.enums.ServiceExceptionCodes.API_MESSAGES_500;

/**
 * Controlador de excepciones global de la aplicación.
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	/**
	 * Zona horaria
	 */
	@Value("${TZ}")
	private String zoneId;


	// MANEJADORES DE EXCEPCIONES

	/**
	 * Utilizado para los errores de campo de las solicitudes que se reciben.
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		LOGGER.error(">> handleMethodArgumentNotValid( ... )");

		Map<String, String> errors = getErrorsMap(ex.getBindingResult());

		ErrorInfo errorInfo = new ErrorInfo(
				API_MESSAGES_400.getCodigoError(), errors, TipoError.SOLICITUD.getTipo(),
				SeveridadError.INFORMATIONAL.getSeveridad(), request.getDescription(false), ZonedDateTime.now(ZoneId.of(zoneId))
		);

		return new ResponseEntity<>(errorInfo, status);
	}

	/**
	 * Utilizado para manejar la excepciones empresariales.
	 */
	@ExceptionHandler({ConfirmacionException.class, RechazoException.class})
	public ResponseEntity<Object> handleBusinessException(HttpServletResponse response, Exception exception) {
		LOGGER.error(">> handleBusinessException( ... )");

		BaseException baseException = (BaseException) exception;

		ErrorInfo errorInfo = new ErrorInfo(
				baseException.getCodigoError(), baseException.getDescripcionError(), baseException.getTipoError(),
				baseException.getSeveridad(), "", ZonedDateTime.now(ZoneId.of(zoneId))
		);

		response.setStatus(baseException.getStatus().value());
		return new ResponseEntity<>(errorInfo, baseException.getStatus());
	}

	/**
	 * Utilizado para los errores no controlados que puedan surgir en la aplicación.
	 */
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleExceptionInternal(Exception ex) {
		LOGGER.error("Se produjo un error desconocido: [ {} ] ", ex.toString(), ex);

		String msgErr = "Error desconocido. Por favor notifique al administrador.";
		ErrorInfo errorInfo = new ErrorInfo(
				API_MESSAGES_500.getCodigoError(), msgErr, TipoError.APLICACION.getTipo(),
				SeveridadError.SERVER_ERROR.getSeveridad(), "", ZonedDateTime.now(ZoneId.of(zoneId))
		);

		return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	/**
	 * Utilizado para los errores no controlados que puedan surgir en el tiempo de ejecución de la aplicación.
	 */
	@ExceptionHandler(RuntimeException.class)
	public final ResponseEntity<Object> handleRuntimeExceptions(RuntimeException ex) {
		LOGGER.error("Se produjo un error desconocido en tiempo de ejecucion: [ {} ] ", ex.toString(), ex);

		String msgErr = "Error desconocido en tiempo de ejecuci\u00f3n. Por favor notifique al administrador.";
		ErrorInfo errorInfo = new ErrorInfo(
				API_MESSAGES_500.getCodigoError(), msgErr, TipoError.APLICACION.getTipo(),
				SeveridadError.SERVER_ERROR.getSeveridad(), "", ZonedDateTime.now(ZoneId.of(zoneId))
		);

		return new ResponseEntity<>(errorInfo, HttpStatus.INTERNAL_SERVER_ERROR);
	}



	// MÉTODOS PRIVADOS

	/**
	 * Método auxiliar utilizado para obtener los errores.
	 *
	 * @param bindingResult Resultado de los errores de campo.
	 * @return Mapa con los errores.
	 */
	private Map<String, String> getErrorsMap(BindingResult bindingResult) {

		Map<String, String> errors = new LinkedHashMap<>();
		bindingResult.getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String message = error.getDefaultMessage();
			errors.put(fieldName, message);
		});
		return errors;
	}

}
