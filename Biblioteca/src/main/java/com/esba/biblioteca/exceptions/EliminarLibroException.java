package com.esba.biblioteca.exceptions;

import static com.esba.biblioteca.parametros.Mensajes.ERROR_ELIMINAR_LIBROS;

public class EliminarLibroException extends Exception {

	public EliminarLibroException() {
		super(String.format(ERROR_ELIMINAR_LIBROS));
	}

	public EliminarLibroException(String message) {
		super(String.format(ERROR_ELIMINAR_LIBROS) + message);
	}
}
