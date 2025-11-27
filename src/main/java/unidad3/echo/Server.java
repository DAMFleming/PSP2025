package unidad3.echo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(9001)) {
			System.out.println("Servidor ECHO escuchando en el puerto 9001");
			ExecutorService service = Executors.newFixedThreadPool(20);
			while (true) {
				Socket socket = serverSocket.accept();
				service.submit(new ServiceTask(socket)::run);
			}
		}
	}

}
