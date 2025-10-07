package ejemplos.ejemplo9;

public class Contador {
	private Integer n;
	
	public Contador(int n) {
		this.n = n;
	}

	public synchronized void inc() {
			n++;
	}

	public synchronized int get() {
		return n;
	}
}
