package com.piecka.login.exceptions;

public class ShortUsernameException extends Exception {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -4011422193952014653L;
	
	private String message;
	
	public ShortUsernameException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
}
