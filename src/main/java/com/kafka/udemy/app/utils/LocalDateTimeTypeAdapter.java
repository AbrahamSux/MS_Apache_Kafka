package com.kafka.udemy.app.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilizado para serializar y deserializar una fecha de tipo {@link LocalDateTime}.
 */
public class LocalDateTimeTypeAdapter implements JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSS");

	/**
	 * Utilizado para serializar la fecha a un elemento Json.
	 */
	@Override
	public JsonElement serialize(LocalDateTime localDateTime, Type srcType, JsonSerializationContext context) {
		return new JsonPrimitive(formatter.format(localDateTime));
	}

	/**
	 * Utilizado para deserializar el elemento Json a la fecha.
	 */
	@Override
	public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return LocalDateTime.parse(json.getAsString(), formatter);
	}

}
