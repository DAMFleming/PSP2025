package unidad3.echo.v1;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BadClient {

	public static void main(String[] args) throws UnknownHostException, IOException {
		ExecutorService service = Executors.newFixedThreadPool(100);
		for (int i = 0; i<100; i++) {
			Socket socket = new Socket("localhost", 9001);
			service.submit(new Peticion(socket)::run);
		}
		
	}
	
	static class Peticion {
		private final Socket socket;
		public Peticion(Socket socket) {
			this.socket = socket;
		}
		public void run() {
			try (socket) {
				while (true);
			} catch (IOException e) {
			}
		}
	}

}
