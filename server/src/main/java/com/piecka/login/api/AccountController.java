package com.piecka.login.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.exceptions.DuplicateEmailException;
import com.piecka.login.model.Account;
import com.piecka.login.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	private final AccountService accountService;
	
	@Autowired
	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}
	
	/**
	 * Register a new account
	 * @param account An account to be registered
	 * @return A response with an account
	 */
	@PostMapping(path = "/add")
	public ResponseEntity<Account> addAccount(@RequestBody Account account) {
		// TODO check if exists
		try {
			accountService.addAccount(account);
			
		} catch (DatabaseException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Database error");
			
		} catch (DuplicateEmailException e) {
			throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
		}
		
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}
}
