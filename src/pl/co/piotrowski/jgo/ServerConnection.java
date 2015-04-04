package pl.co.piotrowski.jgo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnection {
	ServerSocket serverSocket;
	ServerConnection(int port) {
		try {
			serverSocket = new ServerSocket(ServerConfig.listeningPort);
		} catch (IOException e) {
			System.err.println("Błąd inicjalizacji serwera: " + e.getMessage());
		};
	}
	
	Socket listenForSocket() {
		try {
			return serverSocket.accept();
		} catch(IOException e) {
			System.err.println("Błąd oczekiwania na połączenie: " + e.getMessage());
			return null;
		}
	}
}
