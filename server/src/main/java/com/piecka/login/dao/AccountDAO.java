package com.piecka.login.dao;

import java.util.UUID;

import com.piecka.login.model.Account;

public interface AccountDAO {
	
	int insertAccount(UUID id, Account account);
	
	default int insertAccount(Account account) {
		UUID id = UUID.randomUUID();
		
		return insertAccount(id, account);
	}
}
