package unidad2.actividad7;

public class EjemploInterrumpirConFlag extends Thread {
	private volatile boolean finalizar = false;

	@Override
	public void run() {
		while (!finalizar) {
			System.out.println("en el Hilo");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("hilo finalizado");
	}

	public void finalizar() {
		finalizar = true;
	}

	public static void main(String[] args) throws InterruptedException {
		EjemploInterrumpirConFlag t = new EjemploInterrumpirConFlag();
		t.start();
		Thread.sleep(2000);
		t.finalizar();
	}
}
