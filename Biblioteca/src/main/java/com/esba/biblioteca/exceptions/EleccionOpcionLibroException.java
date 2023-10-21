package com.esba.biblioteca.exceptions;

import static com.esba.biblioteca.Parametros.Mensajes.ERROR_OPCION_INCORRECTA;

public class EleccionOpcionLibroException extends Exception{

	public EleccionOpcionLibroException() {
		super(String.format(ERROR_OPCION_INCORRECTA, ""));
	}

	public EleccionOpcionLibroException(String message) {
		super(String.format(ERROR_OPCION_INCORRECTA, "") + message);
	}

}
