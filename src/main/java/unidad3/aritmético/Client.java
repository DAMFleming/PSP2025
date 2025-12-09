package unidad3.aritmético;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client {

	private JFrame frame;
	private JTextField display;
	private double value1;
	private double value2;
	private int state = 0;
	
	private Client() {
		frame = new JFrame("Calculadora Cliente");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frame.getContentPane();
		display = new JTextField();
		display.setEditable(false);
		c.add(display, BorderLayout.NORTH);
		JPanel p = new JPanel(new BorderLayout());
		p.add(new NumbersKeyboard(), BorderLayout.CENTER);
		p.add(new ExtraKeyboard(), BorderLayout.SOUTH);
		c.add(p, BorderLayout.CENTER);
		c.add(new OperatorsKeyboard(), BorderLayout.EAST);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> new Client().show());
    }
    
    private class NumbersKeyboard extends JPanel {
    	private static final long serialVersionUID = 1L;
    	private List<NumberKey> keys = new ArrayList<>(9);
		public NumbersKeyboard() {
    		setLayout(new GridLayout(3, 3));
    		setBorder(BorderFactory.createCompoundBorder(
    				BorderFactory.createEmptyBorder(10, 10, 0, 10),
    				getBorder()));
    		keys.add(new NumberKey("7"));
    		keys.add(new NumberKey("8"));
    		keys.add(new NumberKey("9"));
    		keys.add(new NumberKey("4"));
    		keys.add(new NumberKey("5"));
    		keys.add(new NumberKey("6"));
    		keys.add(new NumberKey("1"));
    		keys.add(new NumberKey("2"));
    		keys.add(new NumberKey("3"));
    		keys.forEach(k -> add(k));
    	}
    }
    
    private class NumberKey extends JButton {
    	private static final long serialVersionUID = 1L;
		private String number;
    	public NumberKey(String number) {
    		super(this.number = number);
    		addActionListener(this::listener);
    	}
    	private void listener(ActionEvent e) {
    		display.setText(display.getText() + number);
    	}
    }
    
    private class ExtraKeyboard extends JPanel {
    	private static final long serialVersionUID = 1L;
    	private List<ExtraKey> keys = new ArrayList<>(3);
		public ExtraKeyboard() {
    		setLayout(new GridLayout(1, 3));
    		setBorder(BorderFactory.createCompoundBorder(
    				BorderFactory.createEmptyBorder(0, 10, 10, 10),
    				getBorder()));
    		keys.add(new ExtraKey("C"));
    		keys.add(new ExtraKey("."));
    		keys.add(new ExtraKey("="));
    		keys.forEach(k -> add(k));
    	}
    }
    
    private class ExtraKey extends JButton {
    	private static final long serialVersionUID = 1L;
		private String function;
    	public ExtraKey(String function) {
    		super(this.function = function);
    		addActionListener(this::listener);
    	}
    	private void listener(ActionEvent e) {

    	}
    }
    
    private class OperatorsKeyboard extends JPanel {
    	private static final long serialVersionUID = 1L;
    	private List<OperatorKey> keys = new ArrayList<>(4);
    	public OperatorsKeyboard() {
    		setLayout(new GridLayout(4, 1));
    		setBorder(BorderFactory.createCompoundBorder(
    				BorderFactory.createEmptyBorder(10, 0, 10, 10),
    				getBorder()));
    		keys.add(new OperatorKey("/"));
    		keys.add(new OperatorKey("x"));
    		keys.add(new OperatorKey("-"));
    		keys.add(new OperatorKey("+"));
    		keys.forEach(k -> add(k));
    	}
    }
    
    private class OperatorKey extends JButton {
    	private static final long serialVersionUID = 1L;
		private String operator;
    	public OperatorKey(String operator) {
    		super(this.operator = operator);
    		addActionListener(this::listener);
    	}
    	private void listener(ActionEvent e) {

    	}
    }

}
