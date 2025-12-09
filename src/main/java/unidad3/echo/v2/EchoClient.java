package unidad3.echo.v2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", 9001)) {
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			String linea;
			while ((linea = teclado.readLine()) != null) {
				out.writeUTF(linea);
				System.out.println(in.readUTF());
			}
		}
	}

}
