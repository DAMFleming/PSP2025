package unidad3.contactos;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServiceTask {

	private final Socket socket;

	public ServiceTask(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try (socket) {
			socket.setSoTimeout(10000);
			DataInputStream in = new DataInputStream(socket.getInputStream());
			attendRequest(in.readUTF());
		} catch (SocketTimeoutException e) {
			System.out.println("tiempo de espera agotado");
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(socket.getRemoteSocketAddress() + ": conexión finalizada");
	}
	
	private void attendRequest(String request) {
		String [] data = request.split(":");
		if (data.length == 0 || data.length > 2)
			System.out.println("petición incorrecta");
		else {
			switch (data[0].toLowerCase()) {
			case "buscar":
				if (data.length == 2) {
					System.out.println("buscando");
				}
				else
					System.out.println("petición incorrecta");
				break;
			case "eliminar":
				System.out.println("eliminando");
				break;
			case "contactos":
				System.out.println("contactos");
				break;
			default:
				System.out.println("almacenando");
			}
			
		}
	}
	
}
