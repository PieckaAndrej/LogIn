package com.piecka.login.exceptions;

public class ShortPasswordException extends Exception {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 4758162700115092921L;
	
	public String getMessage() {
		return "The password must be at least 8 characters";
	}
}