import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;

/*
 * This class functions as the VC part of the MVC, constructing the interactive GUI for the user to input the file
 * and then control the graph via the combo boxes. 
 */
public class Frame extends JFrame implements ActionListener {
	private JButton closeButton;
	private JButton openButton;
	private JTextField titleField;
	static JTextField detailsField;
	private Font graphicsFont = new Font("Calibri", 1, 10);

	
	private JComboBox comboX;
	private JComboBox comboY;
	private ArrayList<Bond> dataList;
	private Graph myGraph;
	private JPanel background;
	private JFileChooser chooser;
	private FileReader reader;
	
	private String xAxisVariable = "";
	private String yAxisVariable = "";
	private String thirdVariable = "";
	
	//Constructor for GUI
	public Frame() {

		this.setSize(800, 500);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setComponents();
		this.setVisible(true);
	}
	
		/*
		 * Breaking the 'setComponents' method into smaller sections required a few more elements being in global scope than I had 
		 * originally planned for, but I felt the smaller methods allowed for clearer testing and results.  
		 */
	public void setComponents() {
		
		background = new JPanel(new BorderLayout());
		this.add(background);
		
		setTopThird();		
		setBottomThird();		
	}
	
	public void setTopThird() {
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		
		JPanel topThird = new JPanel(new BorderLayout());
		background.add(topThird, BorderLayout.NORTH);
		
		openButton = new JButton("Open");
		openButton.addActionListener(this);
		topThird.add(openButton, BorderLayout.WEST);
		
		closeButton = new JButton("Close");
		closeButton.addActionListener(this);
		topThird.add(closeButton, BorderLayout.EAST);
		titleField = new JTextField(10);
		titleField.setFont(graphicsFont);

		titleField.setText("TITLE");
		
		topThird.add(titleField, BorderLayout.CENTER);
	}
	
	public void setBottomThird() {
		JPanel bottomThird = new JPanel(new FlowLayout());
		background.add(bottomThird, BorderLayout.SOUTH);
		
		comboX = new JComboBox();
		comboX.addItem("...");
		comboX.addActionListener(this);
		
		comboY = new JComboBox();
		comboY.addItem("...");
		comboY.addActionListener(this);
		comboX.setFont(graphicsFont);
		comboY.setFont(graphicsFont);
		
		bottomThird.add(comboX);
		bottomThird.add(comboY);
		
		detailsField = new JTextField(40);
		detailsField.setText("DETAILS");
		detailsField.setFont(graphicsFont);
		bottomThird.add(detailsField);
	}
	

	/*
	 * The actionPerformed method - as well as setting which methods are performed when based on user interaction - also
	 * calls a method which ensures the program only attempts to load a .csv file, and that the csv file only contains 
	 * numerical data (after the column headers), and that it contains only three columns of data - limiting the number of potential IO problems. 
	 */ 	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == closeButton) {
			System.exit(0);
	} else if (e.getSource() == openButton) {
		int returnVal = chooser.showOpenDialog(new JFrame());
		
		if (returnVal == chooser.APPROVE_OPTION) {
			try {
				openFileAndGetData();
			
			//These exceptions are handled with a pop-up GUI - details are found in the ExceptionNotice class. 
				} catch (InputException exception){
					new ExceptionNotice(0);
				} catch (DataTypeException dataException) {
					new ExceptionNotice(1);
				} catch (ColumnException columnException) {
					new ExceptionNotice(2);
				}
		}

	} else if (e.getSource() == comboX) {
			if (comboX.getSelectedIndex() == 1) {
				comboXSelected();
			}
			
	} else if (e.getSource() == comboY) {
			if (comboY.getSelectedIndex() == 1) {
				comboYSelected();
			}
		}
	
	}
	
	public void openFileAndGetData() throws InputException, DataTypeException, ColumnException {
		try {
			reader = new FileReader(chooser.getSelectedFile());
			String fileName = chooser.getName(chooser.getSelectedFile());
			
			
			//This is the method which checks the file type
			if (!(checkFileType(fileName))) {
				throw new InputException();	
			}
			
			Scanner scanner = new Scanner(reader);
			
			String[] variableLine = scanner.nextLine().split(",");
			xAxisVariable = variableLine[0];
			yAxisVariable = variableLine[1];
			thirdVariable = variableLine[2];
			
			//This is the method which checks the number of input columns
			if(hasMoreVariables(variableLine)) {
				throw new ColumnException();
			}
			
			
//			scanner.nextLine();
			dataList = new ArrayList();
			while(scanner.hasNextLine()) {
				
				String[] splitLine = scanner.nextLine().split(",");
					//adding in the Strings for each variable name seems wasteful, but it was the only way I could think of to have them communicate the variables 
					//to the 'detailsField' effectively. 
				dataList.add(new Bond(splitLine[0], splitLine[1], splitLine[2], xAxisVariable, yAxisVariable, thirdVariable));
					
				//This method checks each Bond object for the correct data type, throwing an exception if the wrong one is found.
				if(!(checkForDataType(dataList.get(dataList.size()-1)))){
					throw new DataTypeException();
					}
				}
			
			titleField.setText(fileName);
			//Clears the combo boxes, ready for the variable inputs. 
			comboX.removeAllItems();
			comboY.removeAllItems();
			populateComboBoxes();
			//Creates the graph based on the list of Bond objects. 
			myGraph = new Graph(dataList);
			AE3.setBorderComponent(myGraph);
			background.add(myGraph, BorderLayout.CENTER);
			myGraph.repaint();
			
		} catch(FileNotFoundException exception) {
			exception.printStackTrace();
			}
		}
	
	
	/*
	 * The following three methods are for handling Input exceptions
	 */
	public boolean checkFileType(String fileName){
		boolean fileTypeCSV = false;
		char[] fileNameArray = fileName.toCharArray();
		String fileNameType = "";
		for(int i = fileNameArray.length-4; i< fileNameArray.length; i++) {
			fileNameType+=fileNameArray[i];
			}
		if(fileNameType.equals(".csv")) {
			fileTypeCSV = true;
			} 
		return fileTypeCSV;
	}
	
	
	public boolean checkForDataType(Bond bond) {
		boolean typesCorrect = false;
		try {
			bond.getX();
			bond.getY();
			bond.getZ();
		}catch (NumberFormatException exception) {
			return typesCorrect;
		}
		typesCorrect = true;
		return typesCorrect;
	}
	
	public boolean hasMoreVariables(String[] variableList) {
		boolean hasMoreVariables = false;
		try {
		String testVariable = variableList[3];
	} catch (ArrayIndexOutOfBoundsException exception) {
		return hasMoreVariables;
	}
		hasMoreVariables = true;
		return hasMoreVariables;
	}
	
	
	public void populateComboBoxes() {
		comboX.addItem(xAxisVariable);
		comboY.addItem(yAxisVariable);
		
		comboX.addItem(thirdVariable);
		comboY.addItem(thirdVariable);
		
	}
	
	/*Combo boxes swap variables and their respective names around rather than hard code data in - this was in 
	 * an effort to make the program more flexible/reusable
	 * 
	 * However, even with this in mind, the program must take in a file with three columns, or the code for the 
	 * combo boxes wouldn't work properly. 
	 */
	
	public void comboXSelected() {
		//The method starts by swapping the appropriate variables for each Bond object. 
		for(Bond b : dataList) {
			b.swapXForZ();
		}
		//myGraph resets the scales for the each axis relative to x and y data before redrawing
		myGraph.setMaxAndMinValues();
		myGraph.repaint();
		//The combo boxes then have to have their variable ordering changed around to match the Bonds. 
		this.changeComboBoxVariableXForThird();
		comboX.removeAllItems();
		comboY.removeAllItems();
		this.populateComboBoxes();	
	}
	
	public void comboYSelected() {
		for(Bond b : dataList) {
			b.swapYForZ();
		}
		myGraph.setMaxAndMinValues();
		myGraph.repaint();
		this.changeComboBoxVairableYForThird();
		comboX.removeAllItems();
		comboY.removeAllItems();
		this.populateComboBoxes();
	}
	
	/*
	 * The following two methods simply swap the variables around based on what is required for each box being selected. 
	 */
	public void changeComboBoxVariableXForThird() {
		String tempVariable = xAxisVariable;
		xAxisVariable = thirdVariable;
		thirdVariable = tempVariable;		
	}
	
	public void changeComboBoxVairableYForThird() {
		String tempVariable = yAxisVariable;
		yAxisVariable = thirdVariable;
		thirdVariable = tempVariable;
	}
	
	public static Bond getSelectedBond(Bond bond) {
		return bond;
	}
	
	/*
	 * This final method is called from the Graph class, and updates the details field when a particular Bond object is clicked on.
	 * 
	 * It is a static method so that it might interact with the Graph class, which meant I was unable to use the variables 
	 * in the combo boxes, so each bond object was updated so that its toString method could include all the information 
	 * required to give the full details. 
	 */
	public static void updateDetailsField(Bond bond) {
			detailsField.setText(bond.toString());	
	}
	

	
}
