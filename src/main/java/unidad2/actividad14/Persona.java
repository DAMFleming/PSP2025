package unidad2.actividad14;

public class Persona extends Thread {

	private Banco banco;
	
	public Persona(Banco banco, String nombre) {
		this.banco = banco;
		setName(nombre);
	}
	
	@Override
	public void run() {
		
	}
	
}
