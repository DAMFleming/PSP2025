package unidad3.aritmético;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(9003)) {
			System.out.println("Servidor arimético escuchando en el puerto 9003");
			ExecutorService service = Executors.newFixedThreadPool(20);
			while (true) {
				Socket socket = serverSocket.accept();
				socket.setSoTimeout(10000);
				System.out.println(socket.getRemoteSocketAddress() + ":" + socket.getPort() + ": conectado");
				service.submit(new ServiceTask(socket)::run);
			}
		}
	}

}
