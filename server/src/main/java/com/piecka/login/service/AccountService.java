package com.piecka.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.piecka.login.dao.AccountDaoIF;
import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.exceptions.DuplicateEmailException;
import com.piecka.login.exceptions.DuplicateUsernameException;
import com.piecka.login.exceptions.ShortPasswordException;
import com.piecka.login.exceptions.ShortUsernameException;
import com.piecka.login.model.Account;
import com.piecka.login.model.Password;

@Service
public class AccountService {
	
	private final AccountDaoIF accountDao;
	private final PasswordService passwordService;
	
	@Autowired
	public AccountService(@Qualifier("mssqlAccount") AccountDaoIF accountDao, PasswordService passwordService) {
		this.accountDao = accountDao;
		this.passwordService = passwordService;
	}

	public int registerAccount(Account account, Password password)
			throws DatabaseException, DuplicateEmailException,
			DuplicateUsernameException, ShortUsernameException, ShortPasswordException {
		
		if (account.getUsername().length() < 3) {
			throw new ShortUsernameException(account.getUsername());
		}
		
		passwordService.hashPassword(password);
		
		return accountDao.registerAccount(account, password);
	}
	
	public boolean checkLogin(String id, String password) {
		boolean retVal = false;
		
		
		
		return retVal;
	}
	
	public boolean isEmailPresent(String email) throws DatabaseException {
		return accountDao.isEmailPresent(email);
	}
	
	public boolean isUsernamePresent(String username) throws DatabaseException {
		return accountDao.isUsernamePresent(username);
	}
	
}
