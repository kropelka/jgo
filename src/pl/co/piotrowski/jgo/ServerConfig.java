package pl.co.piotrowski.jgo;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * 
 * @author Karol Piotrowski
 * Prosta obsługa pliku konfiguracji serwera
 */
public class ServerConfig {
	static String dbUser = "";
	static String dbPassword = "";
	static int listeningPort = 8042;
	static String dbUrl = "";
	
	public static String getDbUrl() {
		return dbUrl;
	}

	public static void setDbUrl(String dbUrl) {
		ServerConfig.dbUrl = dbUrl;
	}

	public static String getDbUser() {
		return dbUser;
	}

	public static void setDbUser(String dbUser) {
		ServerConfig.dbUser = dbUser;
	}

	public static String getDbPassword() {
		return dbPassword;
	}

	public static void setDbPassword(String dbPassword) {
		ServerConfig.dbPassword = dbPassword;
	}

	public static int getListeningPort() {
		return listeningPort;
	}

	public static void setListeningPort(int listeningPort) {
		ServerConfig.listeningPort = listeningPort;
	}	
	
	static Properties props;
	
	public static void loadFromFile(String filename) throws IOException {
		props = new Properties();
		InputStream input = new FileInputStream(filename);
		props.load(input);
		
		setDbUser(props.getProperty("db_user", ""));
		setDbPassword(props.getProperty("db_password", ""));
		setListeningPort(Integer.parseInt(props.getProperty("listening_port", "8042")));
		setDbUrl(props.getProperty("db_url", "127.0.0.1"));
	}
}

