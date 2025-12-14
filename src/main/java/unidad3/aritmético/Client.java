package unidad3.aritmético;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
	private static Font displayFont;
	private static Font buttonFont;
	private GridBagLayout keyboardLayout = new GridBagLayout();
	private GridBagConstraints constraints = new GridBagConstraints();
	
	private Client() {
		try {
			displayFont = Font.createFont(Font.PLAIN, Client.class.getResourceAsStream("/fonts/LEDCalculator.ttf")).deriveFont(30f);
			buttonFont = Font.createFont(Font.PLAIN, Client.class.getResourceAsStream("/fonts/FredokaOne.ttf")).deriveFont(35f);
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
			c.add(new MainKeyboard(), BorderLayout.CENTER);
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
    		super(keyboardLayout);
    		setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    		add(new NumberKey("7", 0, 0));
    		add(new NumberKey("8", 0, 1));
    		add(new NumberKey("9", 0, 2));
    		add(new NumberKey("4", 1, 0));
    		add(new NumberKey("5", 1, 1));
    		add(new NumberKey("6", 1, 2));
    		add(new NumberKey("1", 2, 0));
    		add(new NumberKey("2", 2, 1));
    		add(new NumberKey("3", 2, 2));
    		add(new NumberKey("0", 3, 0));
    		add(new DecimalKey());
    		add(new ClearKey());
    		add(new OperatorKey("÷", 0, 3));
    		add(new OperatorKey("×", 1, 3));
    		add(new MinusKey());
    		add(new OperatorKey("+", 3, 3));
    		add(new EqualsKey());
    	}
    }
       
    private abstract class Key extends JButton {
    	private static final long serialVersionUID = 1L;
    	public Key(String text, int row, int column, int width, int height) {
    		super(text);
    		setFocusable(false);
    		setFont(buttonFont);
    		setForeground(Color.DARK_GRAY);
    		addActionListener(this::listener);
    		constraints.gridx = column;
    		constraints.gridy = row;
    		constraints.gridwidth = width;
    		constraints.gridheight = height;
    		constraints.fill = GridBagConstraints.HORIZONTAL;
    		constraints.insets = new Insets(3, 3, 3, 3);
    		keyboardLayout.setConstraints(this, constraints);
    	}
    	protected abstract void listener(ActionEvent e);
    }
    
    private class NumberKey extends Key {
    	private static final long serialVersionUID = 1L;
    	public NumberKey(String text, int row, int column) {
    		super(text, row, column, 1, 1);
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
    		super("=", 4, 2, 2, 1);
    	}
    	protected void listener(ActionEvent e) {
    		if (rightOp)
    			sendRequest();
    	}
    }
    
    private class DecimalKey extends Key {
    	private static final long serialVersionUID = 1L;
		public DecimalKey() {
    		super(".", 3, 1, 2, 1);
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
    		super("C", 4, 0, 2, 1);
    	}
    	protected void listener(ActionEvent e) {
   			reset();
   		}
    }
    
    private class MinusKey extends Key {
    	private static final long serialVersionUID = 1L;
    	public MinusKey() {
    		super("-", 2, 3, 1, 1);
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
		public OperatorKey(String text, int row, int column) {
    		super(text, row, column, 1, 1);
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
