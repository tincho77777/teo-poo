package com.esba.biblioteca.exceptions;

import static com.esba.biblioteca.parametros.Mensajes.ERROR_ACTUALIZAR_LIBRO;

public class ActualizarLibroException extends Exception {

	public ActualizarLibroException() {
		super(String.format(ERROR_ACTUALIZAR_LIBRO));
	}

	public ActualizarLibroException(String message) {
		super(String.format(ERROR_ACTUALIZAR_LIBRO) + message);
	}
}
