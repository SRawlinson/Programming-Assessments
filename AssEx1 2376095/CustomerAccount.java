import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

/*
 *This is the class where all the attributes linked to the customer/user is logged. The account balance is logged as 
 *an int, with a displayBalance() method used during the output/export stage. This kept things easier ot follow than 
 *having two versions of the same attribute as integer and double values. 
 */

public class CustomerAccount {
	private String name;
	private int intBalance;
	
	//The two strings are for displaying outputs at two different stages. 'Line' is for the record of transactions, 
	//'custOutput' is for after the initial values have been logged. 
	public String line;
	public String custOutput;
			
	Scanner customerInput = new Scanner(System.in);
	
	public CustomerAccount(String s, int b) {
		name = s;
		intBalance = b;
		line = String.format("Customer: " + name + '\t'+ '\t' + "Initial Account Balance: £" + 
				"%.2f" +'\n' + '\n', displayBalance()) ;
		custOutput = String.format("Hello, " + getName() + 
				". Your balance is £" + displayBalance() + ". ");
	}
			
	//Getters, setters, and toString:
	public String getName() {
		return name;
	}
	
	public int getBalance() {
		return intBalance;
	}
	
	public void setName(String a) {
		name = a;
	}
	
	
	public void setBalance(int a) {
		intBalance = a;		
	}
	
	//updateBalance adds the value of a transaction to the customer's account. 
	public void updateBalance(int a) {
		intBalance += a;
	}
	
	//this method is how I convert the integer value to a double for the display. 
	public double displayBalance() {
		return (Double.valueOf(intBalance)/100);
	}
	public String toString() {
		return "Name: " + name + "\n" + "Balance: " + intBalance;
	}
	
	public void exportFile(String a) {
		String outF = "statement.txt";
		FileWriter fw = null;
		try {
			fw = new FileWriter(outF);	
			fw.write(a);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// Attempt to close the file
			if(fw!=null) {
				try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	
			}
		}
	}
}
