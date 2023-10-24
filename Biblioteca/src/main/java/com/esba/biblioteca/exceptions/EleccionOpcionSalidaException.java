package com.esba.biblioteca.exceptions;

import static com.esba.biblioteca.parametros.Mensajes.ERROR_OPCION_INCORRECTA;

public class EleccionOpcionSalidaException extends Exception{

	public EleccionOpcionSalidaException() {
		super(String.format(ERROR_OPCION_INCORRECTA, ""));
	}

	public EleccionOpcionSalidaException(String message) {
		super(String.format(ERROR_OPCION_INCORRECTA, "") + message);
	}

}
