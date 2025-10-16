package unidad2.actividad14;

public class Banco {

	int libres;
	
	public Banco(int plazas) {
		libres = plazas;
	}
	
	public synchronized void getPlaza() {
		while (libres == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + ": interrumpido en wait");
				Thread.currentThread().interrupt();
				return;
			}
		}
		libres--;
		notifyAll();
	}
	
	public synchronized void liberarPlaza() {
		libres++;
		notifyAll();
	}
	
}
