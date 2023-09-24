package com.kafka.udemy.app.models.enums;

/**
 * Enumeración con las diferentes corresponsalías.
 */
public enum CorresponsalesEnum {

	OXXO,
	SPEI,
	OPENPAY,
	SEVENELEVEN;


	// MÉTODOS

	public static CorresponsalesEnum getCorresponsal(String nameCorresponsal) {

		for (CorresponsalesEnum corresponsal : CorresponsalesEnum.values()) {
			if (corresponsal.name().equals(nameCorresponsal)) {
				return corresponsal;
			}
		}
		return null;
	}

}
