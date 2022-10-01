package com.piecka.login.model;

public class RegisterAccount extends Account {
	private Password password;

	public RegisterAccount(String firstName, String lastName, String username,
			String email, String passwordHash) {
		super(firstName, lastName, username, email);
		
		password = new Password(passwordHash);
	}

	/**
	 * @return the password
	 */
	public Password getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(Password password) {
		this.password = password;
	}
	

}
