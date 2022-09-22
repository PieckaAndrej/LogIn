package com.piecka.login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Repository;

import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.exceptions.DuplicateEmailException;
import com.piecka.login.model.Account;

@Repository("mssql")
public class AccountDataAccessService implements AccountDAO {

	@Override
	public int insertAccount(UUID id, Account account, LocalDateTime creationDateTime) 
			throws DatabaseException, DuplicateEmailException {
		int retVal = 0;
		
		String sql = new StringBuilder().append("INSERT Account (email, username, id, creationDate) ")
        		.append("VALUES (?, ?, ?, ?);").toString();
		
		try {
			Connection con = DriverManager.getConnection(DBConnection.CONNECTION_URL);
			PreparedStatement statement = con.prepareStatement(sql);
			
			try {
				statement.setString(1, account.getEmail());
				statement.setString(2, account.getUsername());
				statement.setBytes(3, HexUtils.fromHexString(id.toString().replace("-", "")));
				statement.setTimestamp(4, Timestamp.valueOf(creationDateTime));
		            
				int rowsAffected = statement.executeUpdate();
				System.out.println(rowsAffected + " row(s) inserted");
		            
				retVal = 1;

			} catch (SQLException e1) {
				System.out.println();
				e1.printStackTrace();
				
				if (e1.getErrorCode() == 2627) {
					throw new DuplicateEmailException(account.getEmail());
				} else {
					throw new DatabaseException("Account insert exception");
				}
			} finally {
				try {
					con.close();
					statement.close();
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		} catch (SQLException e3) {
			e3.printStackTrace();
		}
		
        return retVal;
	}

}
