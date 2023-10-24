package com.esba.biblioteca.exceptions;

import static com.esba.biblioteca.parametros.Mensajes.ERROR_ALTA_LIBROS;

public class AltaLibroException extends Exception {

	public AltaLibroException() {
		super(String.format(ERROR_ALTA_LIBROS));
	}

	public AltaLibroException(String message) {
		super(String.format(ERROR_ALTA_LIBROS) + message);
	}
}
