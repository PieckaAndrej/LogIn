package com.piecka.login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HexFormat;
import java.util.UUID;

import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.stereotype.Repository;

import com.piecka.login.model.Account;

@Repository("mssql")
public class AccountDataAccessService implements AccountDAO {

	@Override
	public int insertAccount(UUID id, Account account) {
		int retVal = 0;
		
		String sql = new StringBuilder().append("INSERT Account (email, username, id) ")
        		.append("VALUES (?, ?, ?);").toString();
		
		try (Connection con = DriverManager.getConnection(DBConnection.CONNECTION_URL);
				PreparedStatement statement = con.prepareStatement(sql)) {
			
	            statement.setString(1, account.getEmail());
	            statement.setString(2, account.getUsername());
	            statement.setBytes(3, HexUtils.fromHexString(id.toString().replace("-", "")));
	            
	            System.out.println("update");
	            int rowsAffected = statement.executeUpdate();
	            System.out.println(rowsAffected + " row(s) inserted");
	            
	            retVal = 1;
	            
	    		con.close();
	    		statement.close();

		} catch (SQLException e1) {
			System.out.println();
			e1.printStackTrace();
		}
        
        return retVal;
	}

}
