package com.piecka.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;

import com.piecka.login.dao.PasswordDaoIF;
import com.piecka.login.model.Password;

@Service
public class PasswordService {

	private final PasswordDaoIF passwordDao;
	
	@Autowired
	public PasswordService(@Qualifier("mssqlPassword") PasswordDaoIF passwordDao) {
		this.passwordDao = passwordDao;
	}
	
	public Password hashPassword(Password password) {
		Argon2PasswordEncoder hash = new Argon2PasswordEncoder();
		System.out.println(password.getPasswordHash());
		
		password.setPasswordHash(hash.encode(password.getPasswordHash()));
		
		System.out.println(password.getPasswordHash());
		
		return password;
	}
}
