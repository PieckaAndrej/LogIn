package com.piecka.login.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	
	@PostMapping(path = "/add")
	public ResponseEntity<Account> addAccount(@RequestBody Account account) {
		// TODO check if exists
		accountService.addAccount(account);
		
		return new ResponseEntity<>(account, HttpStatus.CREATED);
	}
}
