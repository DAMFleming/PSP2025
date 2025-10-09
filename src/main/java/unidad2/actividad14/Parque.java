package unidad2.actividad14;

public class Parque {

	private Banco banco = new Banco();
	private Persona [] personas;
	
	public Parque(int n) {
		personas = new Persona[n];
		for (int i=0; i<n; i++)
			personas[i] = new Persona(banco, "Persona " + (i +1));
	}
	
	public void iniciarSimulación() {
		
	}
	
}
