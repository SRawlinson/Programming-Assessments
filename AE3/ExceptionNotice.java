import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*
 * This class creates a small GUI for handling any of the exceptions related to the input file. 
 */


public class ExceptionNotice extends JFrame implements ActionListener {
	private JButton closeButton;
	private int typeOfException;
	
	public ExceptionNotice(int i) {
		typeOfException = i;
		this.setSize(300,150);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setComponents();
		this.setVisible(true);
	}
	
	public void setComponents() {
		JPanel background = new JPanel();
		JLabel message = new JLabel();
		
		/*
		 * The message on the pop-up window is dictated by the integer required for the constructor. 
		 * Each possible entry corresponds to a particular class of exception. 
		 */
		
		if(typeOfException == 0) {
		message.setText("File type must be .csv");
		} else if(typeOfException == 1) {
			message.setText("File data must be numbers only");
		} else if (typeOfException == 2) {
			message.setText("File must only contain three columns of data");
		}
		background.add(message);
		
		closeButton = new JButton("Okay");
		closeButton.addActionListener((ActionListener) this);
		background.add(closeButton);
		this.add(background);
		
		
	}
	
		/*
		 * The window closes without affecting the rest of the program. 
		 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton) {
			this.dispose();
		}
	}
	
}
