package com.piecka.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.piecka.login.dao.AccountDAO;
import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.exceptions.DuplicateEmailException;
import com.piecka.login.model.Account;

@Service
public class AccountService {
	
	private final AccountDAO accountDao;
	
	@Autowired
	public AccountService(@Qualifier("mssql") AccountDAO accountDao) {
		this.accountDao = accountDao;
	}

	public int addAccount(Account account)
			throws DatabaseException, DuplicateEmailException {
		return accountDao.insertAccount(account);
	}
	
}
