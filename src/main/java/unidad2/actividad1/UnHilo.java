package unidad2.actividad1;

public class UnHilo extends Thread {
	public UnHilo(int id) {
		super("hilo " + id);
	}

	@Override
	public void run() {
		for (int i = 1; i <= 5; i++) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			System.out.println(getName() + ", mensaje " + i);
		}
	}
}
