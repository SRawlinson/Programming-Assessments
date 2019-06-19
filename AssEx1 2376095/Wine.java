/*
 * The Wine class is here to store each instance of a purchase/return. It calculates the pricing of each transaction
 * with integer values, similar to the CustAccount class. It's toString method is formatted specially for the 
 * final output. 
 */
import java.util.Scanner;

public class Wine {
	private String title;
	private int intPricePerUnit;
	private int numberOfUnits;
	private int intPrice;
	
	Scanner wineInput = new Scanner(System.in);
	
	public Wine(String s, int a, int b) {
		title=s;
		intPricePerUnit = a;
		numberOfUnits = b;
	}
	
	//Getters and setters
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String a) {
		title = a;
	}
	
	public int getIntPricePerUnit() {
		return intPricePerUnit;
	}
	
	public void setIntPricePerUnit(int a) {
		intPricePerUnit = a;
	}
	
	public int getNumberOfUnits() {
		return numberOfUnits;
	}
	
	public void setNumberOfUnits(int a) {
		numberOfUnits = a;
	}
	
	public int getIntPrice() {
		return intPrice;
	}
	
	/*
	 * The setPrice method is slightly different in that it requires calculation, dependent upon if the number of units
	 * is a negative number. I feel like this should have been done in the constructor, but I couldn't get that 
	 * to work so it's here. 
	 */
	public int setPrice() {
		intPrice = intPricePerUnit*numberOfUnits;
		if(intPrice < 0) {
			//If the number of units is in the negative (a return), the total price is 80% of the initial value.
			intPrice = (intPrice/10)*8;		
		}
		 return intPrice;
		}
	
	//Two methods for displaying the final value of the numeric values. 
	public double displayPricePerUnit() {
		return (Double.valueOf(intPricePerUnit)/100);
	}
	
	public double displayPrice() {
		return (Double.valueOf(intPrice)/100);
	}
	
	public String toString() {
		return String.format("Name: " + title + "\n" + "Price per unit: " + "%.2f" + "\n" +
				"Number of units: " + numberOfUnits + "\n" + 
				"Total price: " + "%.2f" + "\n", displayPricePerUnit(), displayPrice());  
	}
}
