package com.piecka.login.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	public static final String CONNECTION_URL = "jdbc:sqlserver://localhost:1433;"
			+ "databaseName=LogInDB;user=sa;password=secret2022*;"
			+ "encrypt=true;trustServerCertificate=true";
	
	public static Connection getConnection() {
		try {
            // Load SQL Server JDBC driver and establish connection.
            System.out.print("Connecting to SQL Server ... ");
            try (Connection connection = DriverManager.getConnection(CONNECTION_URL)) {
                System.out.println("Done.");

                System.out.println(connection.isClosed());
                return connection;
            }
        } catch (Exception e) {
            System.out.println();
            e.printStackTrace();
        }

		return null;
	}
}
