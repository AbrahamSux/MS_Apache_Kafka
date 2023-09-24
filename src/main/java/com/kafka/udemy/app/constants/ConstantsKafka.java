package com.kafka.udemy.app.constants;

public final class ConstantsKafka {

	/**
	 * Server Kafka.
	 */
	public static final String HOSTNAME = "localhost";
	public static final String PORT = "9092";

	/**
	 * Topics Kafka.
	 */
	public static final String TOPIC_CONFIRMACION = "confirmacion-topic";
	public static final String TOPIC_RECHAZO = "rechazo-topic";

	/**
	 * Keys Topics Kafka.
	 */
	public static final String MSJ_CONFIRMACION_KEY = "mensajes-confirmacion-key";
	public static final String MSJ_RECHAZO_KEY = "mensajes-rechazo-key";

}
