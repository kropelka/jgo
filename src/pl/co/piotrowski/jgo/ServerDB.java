package pl.co.piotrowski.jgo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ServerDB {
	Connection conn = null;
	
	public void connect(String dbUser, String dbPassword, String dbUrl) throws SQLException {
		conn = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
	}
}
