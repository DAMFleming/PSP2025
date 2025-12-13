package unidad3.aritmético;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client {

	private JFrame frame;
	private JTextField display;
	private boolean operator;
	private boolean leftOp;
	private boolean rightOp;
	private boolean minus;
	private boolean decimal;
	private boolean sent;
	
	private Client() {
		frame = new JFrame("Calculadora Cliente");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frame.getContentPane();
		display = new JTextField();
		display.setEditable(false);
		display.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(10, 10, 0, 10),
				display.getBorder()));
		c.add(display, BorderLayout.NORTH);
		JPanel p = new JPanel(new BorderLayout());
		p.add(new MainKeyboard(), BorderLayout.CENTER);
		c.add(p, BorderLayout.CENTER);
		c.add(new OperatorsKeyboard(), BorderLayout.EAST);
		frame.pack();
		frame.setLocationRelativeTo(null);
    }

    public void show() {
        frame.setVisible(true);
    }
    
    public void reset() {
    	leftOp = rightOp = operator = minus = decimal = sent = false;
		display.setText("");
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> new Client().show());
    }
    
    private void sendRequest() {
    	try (Socket socket = new Socket("localhost", 9003)) {
    		socket.setSoTimeout(3000);
    		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    		PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()), true);
    		out.println(display.getText()
    				.replaceAll("−", "-")
    				.replaceAll("×", "*")
    				.replaceAll("÷", "/"));
    		display.setText(in.readLine());
    	} catch (IOException e) {
    		display.setText(e.getLocalizedMessage());
    	}
    	sent = true;
    }
    
    private class MainKeyboard extends JPanel {
    	private static final long serialVersionUID = 1L;
    	public MainKeyboard() {
    		setLayout(new GridLayout(4, 3));
    		setBorder(BorderFactory.createCompoundBorder(
    				BorderFactory.createEmptyBorder(10, 10, 10, 10),
    				getBorder()));
    		add(new NumberKey("7"));
    		add(new NumberKey("8"));
    		add(new NumberKey("9"));
    		add(new NumberKey("4"));
    		add(new NumberKey("5"));
    		add(new NumberKey("6"));
    		add(new NumberKey("1"));
    		add(new NumberKey("2"));
    		add(new NumberKey("3"));
    		add(new ClearKey());
    		add(new DecimalKey());
    		add(new EqualsKey());
    	}
    }
    
    private class OperatorsKeyboard extends JPanel {
    	private static final long serialVersionUID = 1L;
    	public OperatorsKeyboard() {
    		setLayout(new GridLayout(4, 1));
    		setBorder(BorderFactory.createCompoundBorder(
    				BorderFactory.createEmptyBorder(10, 0, 10, 10),
    				getBorder()));
    		add(new OperatorKey("÷"));
    		add(new OperatorKey("×"));
    		add(new MinusKey());
    		add(new OperatorKey("+"));
    	}
    }
    
    private abstract class Key extends JButton {
    	private static final long serialVersionUID = 1L;
    	public Key(String text) {
    		super(text);
    		addActionListener(this::listener);
    	}
    	protected abstract void listener(ActionEvent e);
    }
    
    private class NumberKey extends Key {
    	private static final long serialVersionUID = 1L;
    	public NumberKey(String text) {
    		super(text);
    	}
		protected void listener(ActionEvent e) {
    		if (sent)
    			reset();
    		if (!leftOp)
    			leftOp = true;
    		else if (operator && !rightOp) 
    			rightOp = true;
    		display.setText(display.getText() + getText());
    	}
    }
    
    private class EqualsKey extends Key {
    	private static final long serialVersionUID = 1L;
		public EqualsKey() {
    		super("=");
    	}
    	protected void listener(ActionEvent e) {
    		if (rightOp)
    			sendRequest();
    	}
    }
    
    private class DecimalKey extends Key {
    	private static final long serialVersionUID = 1L;
		public DecimalKey() {
    		super(".");
    	}
    	protected void listener(ActionEvent e) {
    		if (sent)
    			reset();
    		if (!decimal) {
    			decimal = true;
    			display.setText(display.getText() + getText());
    		}
    	}
    }
    
    private class ClearKey extends Key {
    	private static final long serialVersionUID = 1L;
		public ClearKey() {
    		super("C");
    	}
    	protected void listener(ActionEvent e) {
   			reset();
   		}
    }
    
    private class MinusKey extends Key {
    	private static final long serialVersionUID = 1L;
    	public MinusKey() {
    		super("−");
    	}
    	protected void listener(ActionEvent e) {
    		if (sent)
    			reset();
    		if ( (!leftOp && !decimal && !minus) || (operator && !rightOp && !decimal && !minus) ) {
    			minus = true;
    			display.setText(display.getText() + getText());
    		}
    		else if (leftOp && !operator)
    			setOperator(getText());
    	}
    }
    
    private class OperatorKey extends Key {
    	private static final long serialVersionUID = 1L;
		public OperatorKey(String text) {
    		super(text);
    	}
    	protected void listener(ActionEvent e) {
    		if (leftOp && !operator)
    			setOperator(getText());
    	}
    }
    
    private void setOperator(String operator) {
    	this.operator = true;
    	minus = decimal = false;
		display.setText(display.getText() + operator);
    }
    
}
