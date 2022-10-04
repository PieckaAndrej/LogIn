package com.piecka.login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Repository;

import com.piecka.login.exceptions.DatabaseException;
import com.piecka.login.model.Account;
import com.piecka.login.model.Password;

@Repository("mssqlPassword")
public class PasswordDataAccessService implements PasswordDaoIF {

	@Override
	public int insertPassword(Password password) throws DatabaseException {
		int retVal = 0;
		
		
		try {
			Connection con = DriverManager.getConnection(DBConnection.CONNECTION_URL);
			
			try {
				retVal = insertPassword(password, con);
				
			} catch (DatabaseException de) {
				throw de;
				
			} finally {
				try {
					con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		} catch (SQLException se1) {
			se1.printStackTrace();
		}
		
		return retVal;
	}

	@Override
	public int insertPassword(Password password, Connection con) throws DatabaseException {
		int retVal = 0;
		
		String sql = new StringBuilder().append("INSERT Password (")
				.append(PasswordDataColumnNames.ID).append(", ")
				.append(PasswordDataColumnNames.PASSWORD_HASH)
        		.append(") VALUES (?, ?);").toString();
		
		try {
			PreparedStatement statement = con.prepareStatement(sql);
			
			try {
				statement.setBytes(1, HexUtils.fromHexString(password.getAccount().getId()
						.toString().replace("-", "")));
				statement.setString(2, password.getPasswordHash());
		            
				int rowsAffected = statement.executeUpdate();
				System.out.println(rowsAffected + " row(s) inserted");
		            
				retVal = 1;

			} catch (SQLException e1) {
				System.out.println();
				e1.printStackTrace();

				throw new DatabaseException("Password insert exception");
			} finally {
				try {
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
	public Password getPasswordByID(UUID id) throws DatabaseException {
		Password retVal = null;
		
		String sql = new StringBuilder().append("SELECT * FROM Password WHERE passwordId = ?;").toString();
		
		try {
			Connection con = DriverManager.getConnection(DBConnection.CONNECTION_URL);
			PreparedStatement statement = con.prepareStatement(sql);
			
			try {
				statement.setBytes(1, Utils.convertUUIDToBytes(id));
		            
				ResultSet rs = statement.executeQuery();
				
				if (rs.next()) {
					retVal = buildObject(rs);
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
	
	private Password buildObject(ResultSet rs) {
		Password password = null;
		
		try {
			password = new Password(rs.getString(PasswordDataColumnNames.PASSWORD_HASH),
					Utils.convertBytesToUUID(rs.getBytes(PasswordDataColumnNames.ID)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return password;
	}

}
