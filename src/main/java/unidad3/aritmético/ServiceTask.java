package unidad3.aritmético;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceTask {

	private final Socket socket;
	private static final String numberRegex = "(?:\\-?\\.\\d+)|(?:\\-?\\d+\\.\\d*)";
	private static final String operatorRegex = "[\\+\\-×÷]";
	private static final Pattern p = Pattern.compile(String.format("(%s)(%s)(%s)", numberRegex, operatorRegex, numberRegex));

	public ServiceTask(Socket socket) throws SocketException {
		this.socket = socket;
		socket.setSoTimeout(10000);
	}
	
	public void run() {
		try (socket) {
			System.out.println(socket.getRemoteSocketAddress() + ": conectado");
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
			Matcher m = p.matcher(in.readLine());
			if (m.matches()) {
				double leftOp = Double.parseDouble(m.group(1));
				double rightOp = Double.parseDouble(m.group(3));
				String resultado = null;
				switch (m.group(2)) {
				case "+":
					resultado = String.valueOf(leftOp + rightOp);
					break;
				case "-":
					out.println(String.valueOf(leftOp - rightOp));
					break;
				case "×":
					out.println(String.valueOf(leftOp * rightOp));
					break;
				case "÷":
					out.println(String.valueOf(leftOp / rightOp));
					break;
				}
				System.out.println(socket.getRemoteSocketAddress() + ":" + resultado);
				out.println(resultado);
				
			}
			else
				out.println("expresión incorrecta");
		} catch (IOException e) {
			System.out.println(socket.getRemoteSocketAddress() + ":" + e.getLocalizedMessage());
		}
		System.out.println(socket.getRemoteSocketAddress() + ":" + ": conexión finalizada");
	}
	
}
