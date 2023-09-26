package com.kafka.udemy.app.utils;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utilizado para serializar y deserializar una fecha de tipo {@link ZonedDateTime}.
 */
public class ZonedDateTimeTypeAdapter implements JsonSerializer<ZonedDateTime>, JsonDeserializer<ZonedDateTime> {

	private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu-MM-dd'T'HH:mm:ss.SSSXXX");

	/**
	 * Utilizado para serializar la fecha a un elemento Json.
	 */
	@Override
	public JsonElement serialize(ZonedDateTime zonedDateTime, Type srcType, JsonSerializationContext context) {
		return new JsonPrimitive(formatter.format(zonedDateTime));
	}

	/**
	 * Utilizado para deserializar el elemento Json a la fecha.
	 */
	@Override
	public ZonedDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		return ZonedDateTime.parse(json.getAsString(), formatter);
	}

}
