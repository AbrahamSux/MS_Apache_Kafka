package com.kafka.udemy.app.models.enums;

/**
 * Enumeración con las diferentes notificaciones.
 */
public enum Notificacion {

	CONFIRMACION,
	RECHAZO;


	/**
	 * El tipo de notificación que se enviará.
	 * Por default se establece todos los medios.
	 */
	TipoDeNotificacion tipoDeNotificacion = TipoDeNotificacion.TODOS_LOS_MEDIOS;


	// MÉTODOS

	public static TipoDeNotificacion getTipoDeNotificacion(String tipo) {
		for (TipoDeNotificacion tipoDeNotificacion : TipoDeNotificacion.values()) {
			if (tipoDeNotificacion.name().equals(tipo)) {
				return tipoDeNotificacion;
			}
		}
		return TipoDeNotificacion.TODOS_LOS_MEDIOS;
	}


	// GETTERS Y SETTERS

	public TipoDeNotificacion getTipoDeNotificacion() {
		return tipoDeNotificacion;
	}

	public void setTipoDeNotificacion(TipoDeNotificacion tipoDeNotificacion) {
		this.tipoDeNotificacion = tipoDeNotificacion;
	}


	/**
	 * Enumeración con los diferentes tipos de notificación.
	 */
	public enum TipoDeNotificacion {

		SMS,
		EMAIL,
		TODOS_LOS_MEDIOS

	}

}
