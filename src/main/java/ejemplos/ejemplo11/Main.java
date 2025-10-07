package ejemplos.ejemplo11;

public class Main {
	public static void main(String[] args) {
		Almacen almacen = new Almacen(10);
		Productor [] productores = new Productor[10];
		for (int i=0; i<productores.length; i++)
			productores[i] = new Productor(almacen, 100);
		Consumidor consumidor = new Consumidor(almacen, 300);
		for (int i=0; i<productores.length; i++)
			productores[i].start();
		consumidor.start();
	}
}
