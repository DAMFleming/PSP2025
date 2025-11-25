package unidad2.actividad14.semaforos;

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
		do {
			
			main.mostrarMensaje(getName() + ": paseando " + ms + " milisegundos");
			try {
				Thread.sleep(ms);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + ": interrumpido paseando");
				return;
			}
			
			ms = ms/2;
		} while(!banco.getPlaza() && ms>0);

		if (ms == 0) {
			main.mostrarMensaje(getName() + ": No encontró asiento y se fué del parque.");
		} else {
		
			ms = random.nextInt(6010) + 100;
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
	
}
