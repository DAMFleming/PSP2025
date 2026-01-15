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
				if (data.length == 2)
					search(data[1]);
				else
					error("");
				break;
			case "eliminar":
				if (data.length == 2)
					delete(data[1]);
				else
					error("");
				break;
			case "contactos":
				if (data.length == 1)
					list();
				else
					error("");
				break;
			default:
				if (data.length == 2)
					add(data[0], data[1]);
				else
					error("");
				break;
			}
			
		}
	}
	
	private void search(String nombre) {
		
	}
	
	private void delete(String nombre) {
		
	}
	
	private void add(String nombre, String telefono) {
		
	}
	
	private void list() {
		
	}
	
	private void error(String msg) {
		
	}
	
}
