package com.piecka.login.model;

public class Password {
	private String passwordHash;
	private Account account;
	
	public Password(String passwordHash) {
		this.passwordHash = passwordHash;
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
	
}
