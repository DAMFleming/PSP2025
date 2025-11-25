package unidad2.actividad14.locks;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Parque {

	private Banco banco;
	private ExecutorService service;
	private int numPersonas;
	private Main main;
	
	public Parque(int numPersonas, int plazas, Main main) {
		this.numPersonas = numPersonas;
		service = Executors.newFixedThreadPool(50);
		banco = new Banco(plazas);
		this.main = main;
	}
	
	public void iniciarSimulación() {
		for (int i=0; i<numPersonas; i++) {
			Persona p = new Persona(banco, "Persona " + (i + 1), main);
			service.submit(p::tarea);
		}
	}
	
	public void interrumpir() throws InterruptedException {
		service.shutdownNow();
		service.awaitTermination(1000, TimeUnit.MINUTES);
	}
	
}
