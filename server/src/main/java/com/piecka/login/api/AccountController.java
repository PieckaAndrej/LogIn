package com.piecka.login.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.exceptions.DuplicateEmailException;
import com.piecka.login.exceptions.DuplicateUsernameException;
import com.piecka.login.model.Account;
import com.piecka.login.model.Password;
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
	@PostMapping(path = "/register")
	public ResponseEntity<Account> addAccount(@RequestBody Account account, @RequestBody Password password) {
		try {
			if (!(accountService.isEmailPresent(account.getEmail()) &&
					accountService.isUsernamePresent(account.getUsername()))) {
				
				try {
					accountService.registerAccount(account, password);
					
				} catch (DuplicateEmailException dee) {
					dee.printStackTrace();
					throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
					
				} catch (DuplicateUsernameException due) {
					due.printStackTrace();
					throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
				}
			}
			
		} catch (DatabaseException de) {
			de.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Database error");
		}
		
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}
	
	/**
	 * Check if email is available
	 * @param email Email that is being checked
	 * @return A boolean response
	 */
	@GetMapping(path = "/email/{email}")
	public ResponseEntity<Boolean> isEmailAvailable(@PathVariable("email") String email) {
		boolean isAvailable = false;
		
		try {
			isAvailable = !accountService.isEmailPresent(email);
		} catch (DatabaseException e) {
			e.printStackTrace();
			return new ResponseEntity<>(Boolean.valueOf(isAvailable),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(Boolean.valueOf(isAvailable), HttpStatus.OK);
	}
	
	/**
	 * Check if username is available
	 * @param username Username that is being checked
	 * @return A boolean response
	 */
	@GetMapping(path = "/username/{username}")
	public ResponseEntity<Boolean> isUsernameAvailable(@PathVariable("username") String username) {
		boolean isAvailable = false;
		
		try {
			isAvailable = !accountService.isUsernamePresent(username);
		} catch (DatabaseException e) {
			e.printStackTrace();
			
			return new ResponseEntity<>(Boolean.valueOf(isAvailable), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(Boolean.valueOf(isAvailable), HttpStatus.OK);
	}
	
}
