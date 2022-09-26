package com.piecka.login.exceptions;

public class DuplicateUsernameException extends Exception {
	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -1875700257645523677L;
	
	private String username;
	
	public DuplicateUsernameException(String username) {
		this.username = username;
	}
	
	public String getMessage() {
		return username;
	}
}
