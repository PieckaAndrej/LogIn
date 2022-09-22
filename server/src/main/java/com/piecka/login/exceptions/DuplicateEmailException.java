package com.piecka.login.exceptions;

public class DuplicateEmailException extends Exception {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -5428328047132097276L;

	private String email;
	
	public DuplicateEmailException(String email) {
		this.email = email;
	}
	
	public String getMessage() {
		return email;
	}
}
