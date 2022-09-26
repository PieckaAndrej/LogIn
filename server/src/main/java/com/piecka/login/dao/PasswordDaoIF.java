package com.piecka.login.dao;

import java.sql.Connection;

import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.model.Password;

public interface PasswordDaoIF {
	
	int insertPassword(Password password)
			throws DatabaseException;
	
	int insertPassword(Password password, Connection connection)
			throws DatabaseException;
}
