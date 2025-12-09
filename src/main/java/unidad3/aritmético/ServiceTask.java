package unidad3.aritmético;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;

public class ServiceTask {

	private final Socket socket;

	public ServiceTask(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try (socket) {
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			while (true) {
				try {
					String s = in.readUTF(); 
					System.out.println(socket.getRemoteSocketAddress() + ": " + s);
					out.writeUTF(s);
				} catch (EOFException e) {
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(socket.getRemoteSocketAddress() + ": conexión finalizada");
	}
	
}
