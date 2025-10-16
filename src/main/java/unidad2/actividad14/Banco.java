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
				e.printStackTrace();
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
