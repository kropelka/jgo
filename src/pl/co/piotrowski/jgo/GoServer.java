package pl.co.piotrowski.jgo;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

import pl.co.piotrowski.jgo.ServerConfig;
import pl.co.piotrowski.jgo.ServerDB;
import pl.co.piotrowski.jgo.ServerConnection;

public class GoServer {
	public static void main(String[] args) {
		ServerDB db;
		ServerConnection conn = null;
		Socket clientSocket = null;
		
		boolean verbose = true;
		
		System.out.println("Ładowanie pliku konfiguracyjnego...");
 		try {
			ServerConfig.loadFromFile("serverconfig.properties");
		} catch(IOException e) {
			System.err.println("Błąd ładowania pliku konfiguracyjnego: " + e.getLocalizedMessage());
		};
		
		System.out.println("Łączenie z bazą danych " + ServerConfig.getDbUrl() + " pod nazwą użytkownika " + ServerConfig.getDbUser() + "...");
		try {
			db = new ServerDB();
			db.connect(ServerConfig.dbUser, ServerConfig.dbPassword, ServerConfig.dbUrl);
		} catch(SQLException e) {
			System.err.println("Błąd połączenia z bazą danych: " + e.getMessage());
		}
		
		System.out.println("Otwieranie serwera na porcie " + ServerConfig.listeningPort);
		conn = new ServerConnection(ServerConfig.listeningPort);
		 
		// glowna petla oczekiwania na polaczenia
		// przygotowano na podstawie tutoriala "All About Sockets"
		// z dokumentacji Oracle Java SE
		while(true) { 
			clientSocket = conn.listenForSocket();
			if(verbose) {
				System.out.println("Otwarto połączenie z IP: " + clientSocket.getInetAddress() + 
						"[" + clientSocket.getInetAddress().getHostName() + "]");
			};
		}
	}

}
