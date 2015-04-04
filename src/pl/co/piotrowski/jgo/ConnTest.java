package pl.co.piotrowski.jgo;

import java.net.Socket;

public class ConnTest {
	public static void main(String[] args) {
		try {
			Socket socket = new Socket("127.0.0.1", 8042);
			socket.close();
		} catch(Exception e) {
			System.err.println("Message! " + e.getMessage());
		};
	}
}
