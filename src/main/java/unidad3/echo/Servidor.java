package unidad3.echo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

	public static void main(String[] args) throws IOException {
		ServerSocket serverSocket = new ServerSocket(9001);
		ExecutorService service = Executors.newFixedThreadPool(20);
		while (true) {
			Socket socket = serverSocket.accept();
			service.submit(new Servicio(socket)::run);
		}
	}

}
