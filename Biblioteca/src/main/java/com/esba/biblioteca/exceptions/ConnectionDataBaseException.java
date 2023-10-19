package com.esba.biblioteca.exceptions;

public class ConnectionDataBaseException extends Exception{
	public ConnectionDataBaseException() {
	}

	public ConnectionDataBaseException(String message) {
		super(message);
	}

	public ConnectionDataBaseException(Exception e) {
	}
}
