package com.piecka.login.model;

public class Password {
	private String passwordHash;
	private String serverSalt;
	private String clientSalt;
	private Account account;
	
	public Password(String passwordHash, String clientSalt) {
		this.passwordHash = passwordHash;
		this.clientSalt = clientSalt;
	}
	
	/**
	 * @return the passwordHash
	 */
	public String getPasswordHash() {
		return passwordHash;
	}
	
	/**
	 * @return the serverSalt
	 */
	public String getServerSalt() {
		return serverSalt;
	}
	
	/**
	 * @return the clientSalt
	 */
	public String getClientSalt() {
		return clientSalt;
	}
	
	/**
	 * @param passwordHash the passwordHash to set
	 */
	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}
	
	/**
	 * @param serverSalt the serverSalt to set
	 */
	public void setServerSalt(String serverSalt) {
		this.serverSalt = serverSalt;
	}
	
	/**
	 * @param clientSalt the clientSalt to set
	 */
	public void setClientSalt(String clientSalt) {
		this.clientSalt = clientSalt;
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
