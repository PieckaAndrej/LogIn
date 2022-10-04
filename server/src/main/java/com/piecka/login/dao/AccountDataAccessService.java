package com.piecka.login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Function;

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
				statement.setBytes(3, Utils.convertUUIDToBytes(account.getId()));
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
		boolean retVal = false;
		
		retVal = performQuery(email, AccountDataColumnNames.EMAIL, rs -> {
			boolean next = false;
			
			try {
				next = rs.next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return next;
		});
		
		return retVal;
	}

	@Override
	public boolean isUsernamePresent(String username) throws DatabaseException {
		boolean retVal = false;

		retVal = performQuery(username, AccountDataColumnNames.USERNAME, rs -> {
			boolean next = false;
			
			try {
				next = rs.next();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return next;
		});
		
		return retVal;
	}
	
	private <T> T performQuery(String value, String column, Function<ResultSet, T> func) 
			throws DatabaseException {
		
		T retVal = null;
		
		String sql = new StringBuilder().append("SELECT * FROM Account WHERE ").append(column)
				.append(" = ?;").toString();
		
		try {
			Connection con = DriverManager.getConnection(DBConnection.CONNECTION_URL);
			PreparedStatement statement = con.prepareStatement(sql);
			
			try {
				statement.setString(1, value);
		            
				ResultSet rs = statement.executeQuery();
				
				retVal = func.apply(rs);

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

	@Override
	public Account getAccountFromEmail(String email) throws DatabaseException {
		Account retVal = null;
		
		retVal = performQuery(email, AccountDataColumnNames.EMAIL, rs -> {
			Account account = null;
			
			try {
				if (rs.next()) {
					account = buildObject(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return account;
		});
		
		return retVal;
	}

	@Override
	public Account getAccountFromUsername(String username) throws DatabaseException {
		Account retVal = null;
		

		retVal = performQuery(username, AccountDataColumnNames.USERNAME, rs -> {
			Account account = null;
			
			try {
				if (rs.next()) {
					account = buildObject(rs);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			return account;
		});
		
		return retVal;
	}
	
	private Account buildObject(ResultSet rs) {
		Account account = null;
		try {
			account = new Account(rs.getString(AccountDataColumnNames.FIRST_NAME),
					rs.getString(AccountDataColumnNames.LAST_NAME),
					rs.getString(AccountDataColumnNames.USERNAME),
					rs.getString(AccountDataColumnNames.EMAIL));
			account.setId(Utils.convertBytesToUUID(rs.getBytes(AccountDataColumnNames.ID)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return account;
	}

}
