package com.piecka.login.dao;

import java.time.LocalDateTime;
import java.util.UUID;

import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.exceptions.DuplicateEmailException;
import com.piecka.login.exceptions.DuplicateUsernameException;
import com.piecka.login.model.Account;
import com.piecka.login.model.Password;

public interface AccountDaoIF {
	
	int registerAccount(Account account, Password password, LocalDateTime creationDateTime)
			throws DatabaseException, DuplicateEmailException, DuplicateUsernameException;
	
	default int registerAccount(Account account, Password password) 
			throws DatabaseException, DuplicateEmailException, DuplicateUsernameException {
		
		UUID id = UUID.randomUUID();
		LocalDateTime creationDateTime = LocalDateTime.now();
		
		account.setId(id);
		password.setAccount(account);
		
		return registerAccount(account, password, creationDateTime);
	}
	
	Account getAccountFromEmail(String email) throws DatabaseException;
	
	Account getAccountFromUsername(String username) throws DatabaseException;

	boolean isEmailPresent(String email) throws DatabaseException;

	boolean isUsernamePresent(String username) throws DatabaseException;
}
