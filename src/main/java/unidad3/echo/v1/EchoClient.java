package unidad3.echo.v1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class EchoClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		try (Socket socket = new Socket("localhost", 9001)) {
			BufferedReader teclado = new BufferedReader(new InputStreamReader(System.in));
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			String linea;
			while ((linea = teclado.readLine()) != null) {
				out.println(linea);
				System.out.println(in.readLine());
			}
		}
	}

}
