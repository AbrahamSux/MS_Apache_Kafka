package com.kafka.udemy.app.repositories.impl;

import com.google.gson.Gson;
import com.kafka.udemy.app.models.elk.MensajeConfirmacion;
import com.kafka.udemy.app.models.elk.MensajeRechazo;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionResponse;
import com.kafka.udemy.app.models.mensajerechazo.MensajeRechazoResponse;
import com.kafka.udemy.app.repositories.IMessageRepository;
import com.kafka.udemy.app.repositories.RejectionMessageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class MessageRepository implements IMessageRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageRepository.class);

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	RejectionMessageRepository rejectionMessageRepository;


	/**
	 * Zona horaria
	 */
	@Value("${TZ}")
	private String zoneId;



	// MÉTODOS PÚBLICOS

	@Override
	public void guardarMensajeConfirmacion(String mensaje) {

		try {
			MensajeConfirmacionResponse mensajeConfirmacion = new Gson().fromJson(mensaje, MensajeConfirmacionResponse.class);
			LOGGER.info("MENSAJE DE CONFIRMACION : {}", mensajeConfirmacion);

			MensajeConfirmacion savedConfirmationMessage =  elasticsearchOperations.save(
					buildMensajeConfirmacion(mensajeConfirmacion)
			);

			LOGGER.info("SAVED CONFIRMATION MESSAGE: {}", savedConfirmationMessage.getId());
		} catch (Exception exception) {
			LOGGER.error("Se produjo un error inesperado al guardar el mensaje de Confirmacion en el indice de ELK. ", exception);
		}
	}

	@Override
	public void guardarMensajeRechazo(String mensaje) {

		try {
			MensajeRechazoResponse mensajeRechazo = new Gson().fromJson(mensaje, MensajeRechazoResponse.class);
			LOGGER.info("MENSAJE DE RECHAZO : {}", mensajeRechazo);

			MensajeRechazo savedRejectionMessage = rejectionMessageRepository.save(
					buildMensajeRechazo(mensajeRechazo)
			);

			LOGGER.info("SAVED REJECTION MESSAGE: {}", savedRejectionMessage.getId());
		} catch (Exception exception) {
			LOGGER.error("Se produjo un error inesperado al guardar el mensaje de Rechazo en el indice de ELK. ", exception);
		}
	}



	// MÉTODOS PRIVADOS

	private String generarIdSeguro() {

		Integer randInRange = null;
		try {
			SecureRandom secureRandomGenerator = SecureRandom.getInstance("SHA1PRNG", "SUN");

			// Se obtienen 128 bytes aleatorios
			byte[] randomBytes = new byte[128];
			secureRandomGenerator.nextBytes(randomBytes);

			// Se obtiene un entero aleatorio
			//int r = secureRandomGenerator.nextInt();

			//Se obtiene un entero aleatorio dentro del rango
			randInRange = secureRandomGenerator.nextInt(999999);
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}

		ZonedDateTime datetime = ZonedDateTime.now(ZoneId.of(zoneId));
		DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyMMddHHmmssSSS");
		String referencia = datetime.format(formato);

		if (randInRange == null) {
			// create instance of SecureRandom class
			SecureRandom rand = new SecureRandom();

			// Generate random integers in range 0 to 999
			randInRange = rand.nextInt(1000);
		}

		return referencia.concat(randInRange.toString());
	}

	private MensajeConfirmacion buildMensajeConfirmacion(MensajeConfirmacionResponse mensajeConfirmacionResponse) {
		ZonedDateTime currentZoneDateTime = ZonedDateTime.now(Clock.system(ZoneId.of(zoneId)));

		return new MensajeConfirmacion(
				generarIdSeguro(),
				mensajeConfirmacionResponse.getIdConsumidor(),
				mensajeConfirmacionResponse.getCorresponsal().name(),
				mensajeConfirmacionResponse.getMensajeConfirmacion().getCliente(),
				mensajeConfirmacionResponse.getMensajeConfirmacion().getMensaje(),
				mensajeConfirmacionResponse.getMensajeConfirmacion().getPlataformaOrigen(),
				mensajeConfirmacionResponse.getMensajeConfirmacion().getPlataformaDestino(),
				mensajeConfirmacionResponse.getMensajeConfirmacion().getCorreoElectronico(),
				mensajeConfirmacionResponse.getMensajeConfirmacion().getNumeroTelefono(),
				mensajeConfirmacionResponse.getMensajeConfirmacion().getNotificacion().name(),
				mensajeConfirmacionResponse.getMensajeConfirmacion().getTipoDeNotificacion().name(),
				currentZoneDateTime
		);
	}

	private MensajeRechazo buildMensajeRechazo(MensajeRechazoResponse mensajeRechazoResponse) {
		LocalDateTime currentLocalDateTime = LocalDateTime.now(Clock.system(ZoneId.of(zoneId)));

		return new MensajeRechazo(
				generarIdSeguro(),
				mensajeRechazoResponse.getIdConsumidor(),
				mensajeRechazoResponse.getCorresponsal().name(),
				mensajeRechazoResponse.getMensajeRechazo().getCliente(),
				mensajeRechazoResponse.getMensajeRechazo().getMotivo(),
				mensajeRechazoResponse.getMensajeRechazo().getPlataformaOrigen(),
				mensajeRechazoResponse.getMensajeRechazo().getPlataformaDestino(),
				mensajeRechazoResponse.getMensajeRechazo().getCorreoElectronico(),
				mensajeRechazoResponse.getMensajeRechazo().getNumeroTelefono(),
				mensajeRechazoResponse.getMensajeRechazo().getNotificacion().name(),
				mensajeRechazoResponse.getMensajeRechazo().getTipoDeNotificacion().name(),
				currentLocalDateTime
		);
	}

}
