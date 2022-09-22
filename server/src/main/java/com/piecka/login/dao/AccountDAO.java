package com.piecka.login.dao;

import java.time.LocalDateTime;
import java.util.UUID;

import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.exceptions.DuplicateEmailException;
import com.piecka.login.model.Account;

public interface AccountDAO {
	
	int insertAccount(UUID id, Account account, LocalDateTime creationDateTime)
			throws DatabaseException, DuplicateEmailException;
	
	default int insertAccount(Account account) 
			throws DatabaseException, DuplicateEmailException {
		
		UUID id = UUID.randomUUID();
		LocalDateTime creationDateTime = LocalDateTime.now();
		
		return insertAccount(id, account, creationDateTime);
	}
}
