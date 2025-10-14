package unidad2.actividad14;

import java.util.Random;

import javax.swing.SwingUtilities;

public class Persona extends Thread {

	private static Random random = new Random();
	
	private Banco banco;
	private Main main;
	
	public Persona(Banco banco, String nombre, Main main) {
		this.banco = banco;
		setName(nombre);
		this.main = main;
	}
	
	@Override
	public void run() {
		long ms = random.nextInt(2001) + 1000;
		main.mostrarMensaje(getName() + ": paseando " + ms + " milisegundos");
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
		
		banco.getPlaza();
		
		ms = random.nextInt(601) + 100;
		main.mostrarMensaje(getName() + ": sentado en el banco durante " + ms + " milisegundos");
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
		main.mostrarMensaje(getName() + ": se levanta del banco");
	}
	
}
