package unidad2.actividad14;

import java.util.Random;

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
			System.out.println(Thread.currentThread().getName() + ": interrumpido paseando");
			return;
		}
		
		banco.getPlaza();
		
		ms = random.nextInt(601) + 100;
		main.mostrarMensaje(getName() + ": sentado en el banco durante " + ms + " milisegundos");
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName() + ": interrumpido sentado");
			return;
		}
		banco.liberarPlaza();
		main.mostrarMensaje(getName() + ": se levanta del banco");
	}
	
}
