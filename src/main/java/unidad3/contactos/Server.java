package unidad3.contactos;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

	static Map<String, Set<String>> contactos = new TreeMap<>();
	
	public static void main(String[] args) throws IOException {
		try (ServerSocket serverSocket = new ServerSocket(9001)) {
			System.out.println("Servidor de contactos escuchando en el puerto 9001");
			ExecutorService service = Executors.newFixedThreadPool(20);
			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println(socket.getRemoteSocketAddress() + ":" + socket.getPort() + ": conectado");
				service.submit(new ServiceTask(socket)::run);
			}
		}
	}

}
