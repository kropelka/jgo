package pl.co.piotrowski.jgo;

import java.net.Socket;

public class GameManager implements Runnable {
	Socket socket;
	GameManager(Socket socket) {
		this.socket = socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	};
	
	public void run() {
		System.out.println("Uruchamiam GameManager!");
	}
}
