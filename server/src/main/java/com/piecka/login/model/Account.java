package com.piecka.login.model;

import java.util.UUID;

/**
 * @author Piecka Andrej
 *
 */
public class Account {
	
	private UUID id;
	private String firstName;
	private String lastName;
	private String username;
	private String email;
	
	public Account(String firstName, String lastName, String username, String email) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.email = email;
	}
	
	public Account() {
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return the id
	 */
	public UUID getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(UUID id) {
		this.id = id;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	
	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
