package ejemplos;

public class Ejemplo1 {

	public static void main(String[] args) {
		MiHilo miHilo = new MiHilo();
		miHilo.start();
		for (int i=0; i<10; i++)
			System.out.println("Hilo principal " + i);
	}

}
