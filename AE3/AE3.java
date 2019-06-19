import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JComponent;
import javax.swing.BorderFactory; 

/*
 * This class was mainly to provide a distinction between main and the MVC while I learned the basics of the latter. 
 * 
 * It simply runs the program while allowing me to design the Frame and Graph classes with a little extra clarity. 
 */
public class AE3 {
	
	
	public static void main(String[] args) {
		
		Frame frame = new Frame();
	
	}
	
	//I was initially using these methods to test components of the GUI were being added before I added visible items, but in the end I quite liked the border. 
	public static void setBorderComponent(JComponent component) {
		component.setBorder(BorderFactory.createLineBorder(Color.black));
	}

}
