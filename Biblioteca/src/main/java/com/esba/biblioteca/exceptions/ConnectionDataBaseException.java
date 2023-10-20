package com.esba.biblioteca.exceptions;

import java.sql.SQLException;

public class ConnectionDataBaseException extends SQLException {
	public ConnectionDataBaseException() {
	}

	public ConnectionDataBaseException(Exception e) {
	}

	public ConnectionDataBaseException(String message) {
		super(message);
	}

	public ConnectionDataBaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
