package unidad3.aritmético;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
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

// 012345 ÷×−,. áéíóúäñ

public class Client {

	private JFrame frame;
	private JTextField display;
	private boolean operator;
	private boolean leftOp;
	private boolean rightOp;
	private boolean minus;
	private boolean decimal;
	private boolean sent;
	private static Font displayFont;
	private static Font buttonFont;
	
	private Client() {
		try {
			displayFont = Font.createFont(Font.PLAIN, Client.class.getResourceAsStream("/fonts/PocketCalculator.ttf")).deriveFont(40f);
			buttonFont = Font.createFont(Font.PLAIN, Client.class.getResourceAsStream("/fonts/FredokaOne.ttf")).deriveFont(30f);
			frame = new JFrame("Calculadora Cliente");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			Container c = frame.getContentPane();
			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createEmptyBorder(10,10, 0, 10));
			panel.add(display = new JTextField(25));
			display.setFocusable(false);
			display.setFont(displayFont);
			display.setEditable(false);
			display.setHorizontalAlignment(JTextField.RIGHT);
			display.setBackground(Color.WHITE);
			display.setBorder(BorderFactory.createCompoundBorder(
					display.getBorder(),
					BorderFactory.createEmptyBorder(10, 10, 10, 10)));
			c.add(panel, BorderLayout.NORTH);
			JPanel p = new JPanel(new BorderLayout());
			p.add(new MainKeyboard(), BorderLayout.CENTER);
			c.add(p, BorderLayout.CENTER);
			c.add(new OperatorsKeyboard(), BorderLayout.EAST);
			frame.pack();
			frame.setLocationRelativeTo(null);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
			System.exit(-1);
		};
		
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
    		add(new NumberKey("0"));
    		add(new DecimalKey());
    		add(new ClearKey());
    	}
    }
    
    private class OperatorsKeyboard extends JPanel {
    	private static final long serialVersionUID = 1L;
    	public OperatorsKeyboard() {
    		setLayout(new GridLayout(5, 1));
    		setBorder(BorderFactory.createCompoundBorder(
    				BorderFactory.createEmptyBorder(10, 0, 10, 10),
    				getBorder()));
    		add(new OperatorKey("÷"));
    		add(new OperatorKey("×"));
    		add(new MinusKey());
    		add(new OperatorKey("+"));
    		add(new EqualsKey());
    	}
    }
    
    private abstract class Key extends JButton {
    	private static final long serialVersionUID = 1L;
    	public Key(String text) {
    		super(text);
    		setFocusable(false);
    		setFont(buttonFont);
    		setForeground(Color.DARK_GRAY);
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
    		super("-");
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
