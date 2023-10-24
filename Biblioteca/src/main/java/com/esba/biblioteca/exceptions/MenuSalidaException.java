package com.esba.biblioteca.exceptions;

import static com.esba.biblioteca.parametros.Mensajes.ERROR_MENU_LIBRO;

public class MenuSalidaException extends MenuLibroException {
	public MenuSalidaException() {
		super(String.format(ERROR_MENU_LIBRO));
	}

	public MenuSalidaException(String message) {
		super(String.format(ERROR_MENU_LIBRO, "") + message);
	}
}
