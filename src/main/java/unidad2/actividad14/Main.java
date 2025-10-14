package unidad2.actividad14;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class Main implements WindowListener {

	private JTextArea text = new JTextArea();

	public void mostrarMensaje(String mensaje) {
		SwingUtilities.invokeLater(() -> text.append(mensaje));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Main()::crearMostrarGUI);
	}

	private void crearMostrarGUI() {
		JFrame frame = new JFrame("El banco del parque");
		frame.addWindowListener(this);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(900, 700));
		
		JScrollPane sp = new JScrollPane(text, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.getContentPane().add(sp, BorderLayout.CENTER);

		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}
}
