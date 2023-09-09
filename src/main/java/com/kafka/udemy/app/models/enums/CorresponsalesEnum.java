package com.kafka.udemy.app.models.enums;

public enum CorresponsalesEnum {

	OXXO,
	SPEI,
	OPENPAY;


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
