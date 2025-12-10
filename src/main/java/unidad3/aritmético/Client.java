package unidad3.aritmético;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Client {

	private static final Pattern p = Pattern.compile("(\\d+|\\d*\\-?\\d*\\.?\\d+)[\\+\\−×÷](\\-?\\d*\\.?\\d+)");
	
	private JFrame frame;
	private JTextField display;
	private int state = 0;
	private String operator;
	
	private Client() {
		frame = new JFrame("Calculadora Cliente");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = frame.getContentPane();
		display = new JTextField();
		display.setEditable(false);
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
    	state = 0;
		operator = null;
		display.setText("");
    }

    public static void main(final String[] args) {
        SwingUtilities.invokeLater(() -> new Client().show());
    }
    
    private void sendRequest() {
    	Matcher m = p.matcher(display.getText());
    	if (m.matches()) {
    		double leftOperand = Double.parseDouble(m.group(1));
    		double rightOperand = Double.parseDouble(m.group(2));
    		System.out.println(leftOperand + operator + rightOperand);
    	}
    	else
    		display.setText("expresión incorrecta");
    }
    
    private class MainKeyboard extends JPanel {
    	private static final long serialVersionUID = 1L;
    	private List<Key> keys = new ArrayList<>(9);
		public MainKeyboard() {
    		setLayout(new GridLayout(4, 3));
    		setBorder(BorderFactory.createCompoundBorder(
    				BorderFactory.createEmptyBorder(10, 10, 10, 10),
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
    		keys.add(new ClearKey());
    		keys.add(new DecimalKey());
    		keys.add(new EqualsKey());
    		keys.forEach(k -> add(k));
    	}
    }
    
    private class OperatorsKeyboard extends JPanel {
    	private static final long serialVersionUID = 1L;
    	private List<Key> keys = new ArrayList<>(4);
    	public OperatorsKeyboard() {
    		setLayout(new GridLayout(4, 1));
    		setBorder(BorderFactory.createCompoundBorder(
    				BorderFactory.createEmptyBorder(10, 0, 10, 10),
    				getBorder()));
    		keys.add(new OperatorKey("÷"));
    		keys.add(new OperatorKey("×"));
    		keys.add(new MinusKey());
    		keys.add(new OperatorKey("+"));
    		keys.forEach(k -> add(k));
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
    		if (state == 16)
    			reset();
    		else if ((state & 1) == 0)
    			state |= 1;
    		else if (operator != null && (state & 2) == 0) 
    			state |= 2;
    		display.setText(display.getText() + getText());
    	}
    }
    
    private class EqualsKey extends Key {
    	private static final long serialVersionUID = 1L;
		public EqualsKey() {
    		super("=");
    	}
    	protected void listener(ActionEvent e) {
    		if ((state & 18) == 2) {
    			state = 16;
    			sendRequest();
    		}
    	}
    }
    
    private class DecimalKey extends Key {
    	private static final long serialVersionUID = 1L;
		public DecimalKey() {
    		super(".");
    	}
    	protected void listener(ActionEvent e) {
    		if ((state & 24) == 0) {
    			state |= 8;
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
   			state = 0;
   			operator = null;
   			display.setText("");
    	}
    }
    
    private class MinusKey extends Key {
    	private static final long serialVersionUID = 1L;
    	public MinusKey() {
    		super("-");
    	}
    	protected void listener(ActionEvent e) {
    		if (state == 16)
    			reset();
    		if ((state & 5) == 0) {
    			state |= 4;
    			display.setText(display.getText() + getText());
    		}
    		else if (operator == null) {
    			if ((state & 1) != 0) {
    				state &= 213;
    				display.setText(display.getText() + (operator = getText()));
    			}
    		} else if ((state & 6) == 0) {
    			state |= 6;
    			display.setText(display.getText() + getText());
    		}	
    	}
    }
    
    private class OperatorKey extends Key {
    	private static final long serialVersionUID = 1L;
		public OperatorKey(String text) {
    		super(text);
    	}
    	protected void listener(ActionEvent e) {
    		if (state != 16 && operator == null && (state & 1) != 0) {
    			state &= 213;
   				display.setText(display.getText() + (operator = getText()));
    		}
    	}
    }
    
    //   16  8   4   2   1
    //	res	dec sig op2 op1

}
