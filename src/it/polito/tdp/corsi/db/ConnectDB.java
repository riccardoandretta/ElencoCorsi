package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectDB {
	
	private static final String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root";
	private static Connection conn;
	
	public static Connection getConnection() {
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(jdbcURL);
			}
		} catch (SQLException e) {
			System.err.println("Eroore di connesione al DB");
			throw new RuntimeException(e);
		}
		return conn;
	}

}
