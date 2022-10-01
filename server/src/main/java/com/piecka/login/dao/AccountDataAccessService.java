package com.piecka.login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Repository;

import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.exceptions.DuplicateEmailException;
import com.piecka.login.exceptions.DuplicateUsernameException;
import com.piecka.login.model.Account;
import com.piecka.login.model.Password;

@Repository("mssqlAccount")
public class AccountDataAccessService implements AccountDaoIF {

	@Override
	public int registerAccount(Account account, Password password, LocalDateTime creationDateTime) 
			throws DatabaseException, DuplicateEmailException, DuplicateUsernameException {
		int retVal = 0;
		
		String sql = new StringBuilder().append("INSERT Account (")
				.append(AccountDataColumnNames.EMAIL).append(", ")
				.append(AccountDataColumnNames.USERNAME).append(", ")
				.append(AccountDataColumnNames.ID).append(", ")
				.append(AccountDataColumnNames.CREATION_DATE).append(", ")
				.append(AccountDataColumnNames.FIRST_NAME).append(", ")
				.append(AccountDataColumnNames.LAST_NAME)
        		.append(") VALUES (?, ?, ?, ?, ?, ?);").toString();
		
		PasswordDataAccessService passwordDao = new PasswordDataAccessService();
		
		try {
			Connection con = DriverManager.getConnection(DBConnection.CONNECTION_URL);
			PreparedStatement statement = con.prepareStatement(sql);
			
			con.setAutoCommit(false);
			
			try {
				System.out.println(sql);
				statement.setString(1, account.getEmail());
				statement.setString(2, account.getUsername());
				statement.setBytes(3, HexUtils.fromHexString(account.getId()
						.toString().replace("-", "")));
				statement.setTimestamp(4, Timestamp.valueOf(creationDateTime));
				statement.setString(5, account.getFirstName());
				statement.setString(6, account.getLastName());
		            
				int rowsAffected = statement.executeUpdate();
				System.out.println(rowsAffected + " row(s) inserted");
				
				passwordDao.insertPassword(password, con);
		            
				retVal = 1;

			} catch (DatabaseException de) {
				con.rollback();
				
				throw de;
			} catch (SQLException e1) {
				System.out.println();
				e1.printStackTrace();
				
				con.rollback();
				
				if (e1.getErrorCode() == 2627) {
					throw new DuplicateEmailException(account.getEmail());
				} else if (e1.getErrorCode() == 2601) {
					throw new DuplicateUsernameException(account.getUsername());
				} else {
					throw new DatabaseException("Account insert exception");
				}
			
			} finally {
				try {
					con.setAutoCommit(true);
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

	@Override
	public boolean isEmailPresent(String email) throws DatabaseException {
		return isValuePresentInColumn(email, AccountDataColumnNames.EMAIL);
	}

	@Override
	public boolean isUsernamePresent(String username) throws DatabaseException {
		return isValuePresentInColumn(username, AccountDataColumnNames.USERNAME);
	}
	
	private boolean isValuePresentInColumn(String value, String column) throws DatabaseException {
		boolean retVal = false;
		
		String sql = new StringBuilder().append("SELECT * FROM Account WHERE " + column + " = ?;").toString();
		
		try {
			Connection con = DriverManager.getConnection(DBConnection.CONNECTION_URL);
			PreparedStatement statement = con.prepareStatement(sql);
			
			try {
				statement.setString(1, value);
		            
				ResultSet resultSet = statement.executeQuery();
				
		        if (resultSet.next()) {
		        	retVal = true;
		        }

			} catch (SQLException e1) {
				System.out.println();
				e1.printStackTrace();
				
				throw new DatabaseException("Something went wrong!");
				
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
