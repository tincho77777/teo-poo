package com.esba.biblioteca.exceptions;

import static com.esba.biblioteca.parametros.Mensajes.ERROR_LISTAR_LIBROS;

public class ListarLibrosException extends Exception {

	public ListarLibrosException() {
		super(String.format(ERROR_LISTAR_LIBROS));
	}

	public ListarLibrosException(String message) {
		super(String.format(ERROR_LISTAR_LIBROS) + message);
	}
}
