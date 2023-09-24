package com.kafka.udemy.app.repositories.impl;

import com.google.gson.Gson;
import com.kafka.udemy.app.models.elk.MensajeConfirmacion;
import com.kafka.udemy.app.models.mensajeconfirmacion.MensajeConfirmacionRequest;
import com.kafka.udemy.app.repositories.IElasticsearchOperationsRepository;
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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ElasticsearchOperationsRepository implements IElasticsearchOperationsRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchOperationsRepository.class);

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	/**
	 * Zona horaria
	 */
	@Value("${TZ}")
	private String zoneId;



	// MÉTODOS PÚBLICOS

	@Override
	public void guardarMensajeConfirmacion(String mensaje) {

		MensajeConfirmacionRequest mensajeConfirmacion = new Gson().fromJson(mensaje, MensajeConfirmacionRequest.class);
		LOGGER.info("MENSAJE DE CONFIRMACION : {}", mensajeConfirmacion);


		ZonedDateTime currentZoneDateTime = ZonedDateTime.now(Clock.system(ZoneId.of(zoneId)));
		try {
			MensajeConfirmacion savedEntity =  elasticsearchOperations.save(new MensajeConfirmacion(
					generarIdSeguro(),
					mensajeConfirmacion.getCliente(),
					mensajeConfirmacion.getMensaje(),
					mensajeConfirmacion.getPlataformaOrigen(),
					mensajeConfirmacion.getPlataformaDestino(),
					currentZoneDateTime
			));

			LOGGER.info("MENSAJE GUARDADO: {}", savedEntity.getId());
		} catch (Exception exception) {
			LOGGER.error("Ocurrio un error inesperado en el proceso para guardar en el indice de ELK. ", exception);
		}
	}

	@Override
	public void guardarMensajeRechazo(String mensaje) {

		//return null;
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
}
