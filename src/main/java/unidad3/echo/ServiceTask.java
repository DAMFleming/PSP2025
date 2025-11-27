package unidad3.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class ServiceTask {

	private final Socket socket;

	public ServiceTask(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try (socket) {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			String linea;
			while ((linea = in.readLine()) != null) {
				System.out.println(socket.getRemoteSocketAddress() + ": " + linea);
				out.println(linea);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
