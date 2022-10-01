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
import com.piecka.login.exceptions.ShortPasswordException;
import com.piecka.login.exceptions.ShortUsernameException;
import com.piecka.login.model.Account;
import com.piecka.login.model.RegisterAccount;
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
	public ResponseEntity<Integer> addAccount(@RequestBody RegisterAccount registerAccount) {
		int response = 0;
		
		try {
			if (!(accountService.isEmailPresent(registerAccount.getEmail()) &&
					accountService.isUsernamePresent(registerAccount.getUsername()))) {
				
				try {
					response = accountService.registerAccount(registerAccount, registerAccount.getPassword());
					
				} catch (DuplicateEmailException dee) {
					dee.printStackTrace();
					throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
					
				} catch (DuplicateUsernameException due) {
					due.printStackTrace();
					throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
					
				} catch (ShortUsernameException sue) {
					sue.printStackTrace();
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username must be at least 3 characters");
					
				} catch (ShortPasswordException spe) {
					spe.printStackTrace();
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password must be at least 8 characters");
				}
			}
			
		} catch (DatabaseException de) {
			de.printStackTrace();
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
					"Database error");
		}
		
		return new ResponseEntity<>(Integer.valueOf(response), HttpStatus.CREATED);
	}
	
	@PostMapping(path= "/login/{id}")
	public ResponseEntity<Account> logIn(@PathVariable("id") String id, @RequestBody String password) {
		Account account = null;
		
		return new ResponseEntity<>(account, HttpStatus.CONTINUE);
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
