package com.kafka.udemy.app.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

import static com.kafka.udemy.app.constants.ConstantsKafka.*;

@Configuration
@EnableScheduling
public class ApacheKafkaConfig {

	/**
	 * Configuración de la fábrica de productores.
	 */
	@Bean
	public KafkaTemplate<String, String> createTemplate() {
		Map<String, Object> senderProps = producerProps();
		ProducerFactory<String, String> producerFactory = new DefaultKafkaProducerFactory<>(senderProps);
		producerFactory.addListener(new MicrometerProducerListener<String, String>(meterRegistry()));

		return new KafkaTemplate<>(producerFactory);
	}

	/**
	 * Configuración de la fábrica de consumo.
	 */
	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		return new DefaultKafkaConsumerFactory<>(consumerProps());
	}

	/**
	 * Una vez definido el consumer factory se configura la fabrica de contenedores de escucha concurrente de Kafka.
	 */
	@Bean(name = "kafkaListenerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		factory.setBatchListener(true); //Leer más de un registro al mismo tiempo.
		factory.setConcurrency(3); //Se tendrán 3 hilos consumiendo mensajes de forma concurrente.

		return factory;
	}

	@Bean
	public MeterRegistry meterRegistry() {
		PrometheusMeterRegistry prometheusMeterRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);
		return prometheusMeterRegistry;
	}


	// MÉTODOS PRIVADOS

	/**
	 * Mapa con la configuración del Consumer.
	 */
	private Map<String, Object> consumerProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ConsumerConfig.GROUP_ID_CONFIG, "group");
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);
		props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		return props;
	}

	/**
	 * Mapa con la configuración del Producer.
	 */
	private Map<String, Object> producerProps() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		props.put(ProducerConfig.RETRIES_CONFIG, 0);
		props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
		props.put(ProducerConfig.LINGER_MS_CONFIG, 1);
		props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}

}
