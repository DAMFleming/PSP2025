package ejemplos;

public class Ejemplo2 {

	public static void main(String[] args) {
		Thread.currentThread().setName("Hilo principal");
		Thread miHilo = new Thread(Ejemplo2::unaTarea, "Mi Hilo");
		miHilo.start();
		for (;;)
			System.out.println("Hilo principal ");
	}
	
	public static void unaTarea() {
		for (;;)
			System.out.println("Mi hilo ");
	}

}
