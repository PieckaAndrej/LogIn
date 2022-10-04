package com.piecka.login.model;

import java.util.UUID;

public class Password {
	
	private UUID id;
	private String passwordHash;
	private Account account;
	
	public Password(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	public Password(String passwordHash, UUID id) {
		this(passwordHash);
		
		this.id = id;
	}
	
	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	/**
	 * @return the account
	 */
	public Account getAccount() {
		return account;
	}

	/**
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
	
}
