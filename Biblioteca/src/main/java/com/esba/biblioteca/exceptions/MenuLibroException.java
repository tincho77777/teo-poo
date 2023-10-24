package com.esba.biblioteca.exceptions;

import static com.esba.biblioteca.parametros.Mensajes.ERROR_MENU_LIBRO;

public class MenuLibroException extends Exception {
	public MenuLibroException() {
		super(String.format(ERROR_MENU_LIBRO));
	}

	public MenuLibroException(String message) {
		super(String.format(ERROR_MENU_LIBRO, "") + message);
	}
}
