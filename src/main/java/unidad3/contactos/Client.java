package unidad3.contactos;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {

	public static void main(String[] args) {
		String request = IO.readln("Petición: ");
		try (Socket socket = new Socket("localhost", 9001)) {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(request);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
