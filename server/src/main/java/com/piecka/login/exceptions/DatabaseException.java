package com.piecka.login.exceptions;

public class DatabaseException extends Exception {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -7510743189845007450L;
	
	private String message;
	
	public DatabaseException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
