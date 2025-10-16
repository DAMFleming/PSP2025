package unidad2.actividad14;

public class Parque {

	private Banco banco;
	private Persona [] personas;
	
	public Parque(int numPersonas, int plazas, Main main) {
		banco = new Banco(plazas);
		personas = new Persona[numPersonas];
		for (int i=0; i<numPersonas; i++)
			personas[i] = new Persona(banco, "Persona " + (i + 1), main);
	}
	
	public void iniciarSimulación() {
		for (int i=0; i<personas.length; i++)
			personas[i].start();
	}
	
	public void interrumpir() {
		for (int i=0; i<personas.length; i++)
			personas[i].interrupt();
		for (int i=0; i<personas.length; i++)
			try {
				personas[i].join();
			} catch (InterruptedException e) {
			}
	}
	
}
